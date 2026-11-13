package io.github.mudrichenkoevgeny.npmgenerator.verification

import io.github.mudrichenkoevgeny.npmgenerator.engine.NpmGeneratorConfig
import io.github.mudrichenkoevgeny.npmgenerator.util.decapitalizeFirstLetter
import java.io.File
import java.io.IOException
import java.nio.file.Files

/**
 * Reusable verifier for NPM package output artifacts, syntax, TypeScript compilation, and runtime behavior.
 */
public class NpmPackageVerifier(
    private val config: NpmGeneratorConfig,
    private val importedFoundationJs: Set<String> = emptySet(),
    private val importedFoundationDts: Set<String> = emptySet()
) {

    /**
     * Runs all package verification steps.
     */
    public fun verifyAll() {
        verifyArtifactsPresence()
        verifyJsSyntax()
        verifyNpmPackDryRun()

        val (createdSymlink, createdFoundationPkgDir, envNodeModules) = ensureNodeModules(config.outputDirectory)

        try {
            verifyDtsCompilation(envNodeModules)
            verifyRuntimeBehavior()
        } finally {
            if (createdFoundationPkgDir != null && createdFoundationPkgDir.exists()) {
                try {
                    createdFoundationPkgDir.deleteRecursively()
                } catch (_: SecurityException) {}
            }

            val nodeModulesInOutputDir = File(config.outputDirectory, "node_modules")
            if (createdSymlink && nodeModulesInOutputDir.exists()) {
                try {
                    Files.deleteIfExists(nodeModulesInOutputDir.toPath())
                } catch (_: IOException) {
                } catch (_: SecurityException) {}
            }
        }
    }

    /**
     * Verifies presence of all required package artifacts.
     */
    public fun verifyArtifactsPresence() {
        val outputDir = config.outputDirectory
        val requiredFiles = listOf("package.json", "index.js", "index.d.ts", "README.md", ".npmignore")
        val missingFiles = requiredFiles.filter { !File(outputDir, it).exists() }
        check(missingFiles.isEmpty()) {
            "NPM package verification failed: Missing required artifacts: $missingFiles in ${outputDir.absolutePath}"
        }
    }

    /**
     * Verifies JavaScript syntax using node --check.
     */
    public fun verifyJsSyntax() {
        val outputDir = config.outputDirectory
        val indexJs = File(outputDir, "index.js")

        val jsResult = runCommand(listOf("node", "--check", indexJs.name), outputDir)
        check(jsResult.exitCode == 0) {
            "JS syntax check failed for index.js:\n${jsResult.output}"
        }
    }

    /**
     * Verifies package archive structure using npm pack --dry-run.
     */
    public fun verifyNpmPackDryRun() {
        val outputDir = config.outputDirectory
        val result = runCommand(listOf(getNpmExecutable(), "pack", "--dry-run"), outputDir)
        check(result.exitCode == 0) {
            "npm pack --dry-run failed in ${outputDir.absolutePath}:\n${result.output}"
        }
    }

    /**
     * Verifies TypeScript declaration compilation using tsc --noEmit --strict.
     */
    public fun verifyDtsCompilation(envNodeModules: File) {
        val outputDir = config.outputDirectory
        val testFile = File(outputDir, "test-types.ts")
        val tscJs = File(envNodeModules, "typescript/bin/tsc")

        try {
            val dtsTestCode = buildString {
                append("import { z } from 'zod';\n")
                append("import * as Pkg from './index';\n\n")
                config.dtoClasses.forEach { kClass ->
                    kClass.simpleName?.let { name ->
                        val schemaName = name.decapitalizeFirstLetter() + "Schema"
                        append("type Infered$name = z.infer<typeof Pkg.$schemaName>;\n")
                        append("const _type_check_$name: Pkg.$name = null as unknown as Infered$name;\n")
                    }
                }
                if (!config.customDtsSmokeTestCode.isNullOrBlank()) {
                    append("\n").append(config.customDtsSmokeTestCode).append("\n")
                }
            }
            testFile.writeText(dtsTestCode)

            val command = if (tscJs.exists()) {
                listOf("node", tscJs.absolutePath, "--noEmit", "--strict", "--skipLibCheck", testFile.name)
            } else if (isWindows()) {
                listOf("npx.cmd", "-p", "typescript", "tsc", "--noEmit", "--strict", "--skipLibCheck", testFile.name)
            } else {
                listOf("npx", "-p", "typescript", "tsc", "--noEmit", "--strict", "--skipLibCheck", testFile.name)
            }

            val result = runCommand(command, outputDir)
            check(result.exitCode == 0) {
                "TypeScript declaration compilation (tsc --noEmit --strict) failed:\n${result.output}"
            }
        } finally {
            if (testFile.exists()) {
                testFile.delete()
            }
        }
    }

    /**
     * Verifies runtime behavioral execution in Node.js ESM environment.
     */
    public fun verifyRuntimeBehavior() {
        val outputDir = config.outputDirectory
        val testFile = File(outputDir, "test-runtime.mjs")
        try {
            val runtimeTestCode = buildString {
                append("import * as Pkg from './index.js';\n")
                append("import process from 'process';\n\n")
                append("let errors = 0;\n\n")

                config.brandedTypes.forEach { kClass ->
                    kClass.simpleName?.let { name ->
                        append(
                            """
if (typeof Pkg.to${name}OrThrow === 'function') {
  try {
    Pkg.to${name}OrThrow("");
    console.error("to${name}OrThrow did not throw on empty string");
    errors++;
  } catch (e) {}

  try {
    const val = Pkg.to${name}OrThrow("valid_test_val");
    if (val !== "valid_test_val") {
      console.error("to${name}OrThrow altered valid input");
      errors++;
    }
  } catch (e) {
    console.error("to${name}OrThrow threw on valid input", e);
    errors++;
  }
}
                            """.trimIndent()
                        ).append("\n\n")
                    }
                }

                if (!config.customRuntimeSmokeTestCode.isNullOrBlank()) {
                    append(config.customRuntimeSmokeTestCode).append("\n\n")
                }

                append("if (errors > 0) {\n  process.exit(1);\n}\n")
            }
            testFile.writeText(runtimeTestCode)

            val result = runCommand(listOf("node", testFile.name), outputDir)
            check(result.exitCode == 0) {
                "Runtime behavior verification failed:\n${result.output}"
            }
        } finally {
            if (testFile.exists()) {
                testFile.delete()
            }
        }
    }

    private fun ensureNodeModules(outputDir: File): Triple<Boolean, File?, File> {
        val envNodeModules = ensureVerifierCacheNodeModules()
        val nodeModulesInOutputDir = File(outputDir, "node_modules")
        var createdSymlink = false

        if (!nodeModulesInOutputDir.exists() && envNodeModules.exists()) {
            createdSymlink = createDirectoryJunctionOrSymlink(nodeModulesInOutputDir, envNodeModules)
        }

        var createdFoundationPkgDir: File? = null
        if (config.foundationImportPackage.isNotEmpty() && config.packageName != config.foundationImportPackage) {
            val foundationPkgDir = File(nodeModulesInOutputDir, config.foundationImportPackage)
            if (!foundationPkgDir.exists()) {
                foundationPkgDir.mkdirs()
                createdFoundationPkgDir = foundationPkgDir
                File(foundationPkgDir, "package.json").writeText("""{"name":"${config.foundationImportPackage}","version":"1.0.0","type":"module","main":"index.js","types":"index.d.ts"}""")

                val jsExports = importedFoundationJs.joinToString("\n") { symbol ->
                    when {
                        symbol.endsWith("Schema") -> "export const $symbol = z.unknown();"
                        symbol.startsWith("to") -> "export const $symbol = (x) => x;"
                        symbol.startsWith("createTest") -> "export const $symbol = (count, factory) => (typeof count === 'number' ? Array.from({ length: count }, (_, i) => (typeof factory === 'function' ? factory(i) : factory)) : {});"
                        else -> "export const $symbol = {};"
                    }
                }
                File(foundationPkgDir, "index.js").writeText("import { z } from 'zod';\n\n" + if (jsExports.isNotBlank()) jsExports else "export const dummy = {};\n")

                val dtsExports = (config.autoImportSymbols + importedFoundationDts + importedFoundationJs).joinToString("\n") {
                    "export declare const $it: any;\nexport type $it = any;"
                }
                File(foundationPkgDir, "index.d.ts").writeText(if (dtsExports.isNotBlank()) dtsExports else "export declare const dummy: any;\n")
            }
        }

        return Triple(createdSymlink, createdFoundationPkgDir, envNodeModules)
    }

    private fun ensureVerifierCacheNodeModules(): File {
        val baseCacheDir = config.outputDirectory.parentFile ?: File("build")
        val envDir = File(baseCacheDir, "npm-verifier-env")
        val envNodeModules = File(envDir, "node_modules")
        val envZodPkg = File(envNodeModules, "zod/package.json")
        val envTsPkg = File(envNodeModules, "typescript/package.json")

        val cleanZodVersion = config.zodVersion.removePrefix("^").removePrefix("~")
        val cleanTsVersion = config.typescriptVersion.removePrefix("^").removePrefix("~")
        val needsInstall = !envZodPkg.exists() || !envTsPkg.exists() ||
            !envZodPkg.readText().contains(cleanZodVersion) || !envTsPkg.readText().contains(cleanTsVersion)

        if (needsInstall) {
            envDir.mkdirs()
            File(envDir, "package.json").writeText(
                """
{
  "name": "npm-verifier-env",
  "private": true,
  "dependencies": {
    "zod": "${config.zodVersion}",
    "typescript": "${config.typescriptVersion}"
  }
}
                """.trimIndent()
            )
            val installResult = runCommand(listOf(getNpmExecutable(), "install"), envDir)
            check(installResult.exitCode == 0) {
                "npm install failed in ${envDir.absolutePath}:\n${installResult.output}"
            }
        }

        return envNodeModules
    }

    private fun createDirectoryJunctionOrSymlink(linkDir: File, targetDir: File): Boolean {
        if (isWindows()) {
            try {
                val process = ProcessBuilder("cmd.exe", "/c", "mklink", "/J", linkDir.absolutePath, targetDir.absolutePath)
                    .redirectErrorStream(true)
                    .start()
                val exitCode = process.waitFor()
                if (exitCode == 0 && linkDir.exists()) return true
            } catch (_: IOException) {
            } catch (_: InterruptedException) {
            } catch (_: SecurityException) {}
        }
        return try {
            Files.createSymbolicLink(linkDir.toPath(), targetDir.toPath())
            linkDir.exists()
        } catch (_: IOException) {
            false
        } catch (_: UnsupportedOperationException) {
            false
        } catch (_: SecurityException) {
            false
        }
    }

    private data class CommandResult(val exitCode: Int, val output: String)

    private fun runCommand(command: List<String>, workDir: File): CommandResult {
        return try {
            val process = ProcessBuilder(command)
                .directory(workDir)
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()
            CommandResult(exitCode, output)
        } catch (e: IOException) {
            CommandResult(1, e.message ?: "Process execution I/O error")
        } catch (e: InterruptedException) {
            CommandResult(1, e.message ?: "Process execution interrupted")
        } catch (e: SecurityException) {
            CommandResult(1, e.message ?: "Process execution security error")
        }
    }

    private fun isWindows(): Boolean =
        System.getProperty("os.name").lowercase().contains("win")

    private fun getNpmExecutable(): String =
        if (isWindows()) {
            "npm.cmd"
        } else {
            "npm"
        }
}
