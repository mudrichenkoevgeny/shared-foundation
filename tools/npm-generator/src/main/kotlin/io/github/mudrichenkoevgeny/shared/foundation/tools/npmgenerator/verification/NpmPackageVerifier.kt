package io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.verification

import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.engine.NpmGeneratorConfig
import io.github.mudrichenkoevgeny.shared.foundation.tools.npmgenerator.util.decapitalizeFirstLetter
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
        val isFoundation = config.basePackagePrefix.isEmpty() ||
            (config.foundationImportPackage.isNotEmpty() && config.packageName == config.foundationImportPackage)

        val requiredFiles = mutableListOf("package.json", "index.js", "index.d.ts", "README.md", ".npmignore")
        if (isFoundation) {
            requiredFiles.add("scripts/agent-rules-syncer.mjs")
            requiredFiles.add("scripts/postinstall-sync-rules.mjs")
        }

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
        val indexDts = File(outputDir, "index.d.ts")
        val tscJs = File(envNodeModules, "typescript/bin/tsc")

        try {
            val dtsTestCode = buildString {
                append("import { z } from 'zod';\n")
                append("import * as Pkg from './index';\n\n")
                config.dtoClasses.forEach { kClass ->
                    kClass.simpleName?.let { name ->
                        if (name != "PagedResult" && name != "WebSocketContract") {
                            val schemaName = name.decapitalizeFirstLetter() + "Schema"
                            append("type Infered_$name = z.infer<typeof Pkg.$schemaName>;\n")
                            append("const _dto_to_schema_$name: Infered_$name = null as unknown as Pkg.$name;\n")
                            append("const _schema_to_dto_$name: Pkg.$name = null as unknown as Infered_$name;\n")
                        }
                        append("const _fixture_$name: Pkg.$name = Pkg.createTest$name();\n\n")
                    }
                }
                config.domainToDtoMappers.forEach { (domainClass, payloadClass) ->
                    val domainName = domainClass.simpleName
                    val payloadName = payloadClass?.simpleName
                    if (domainName != null && payloadName != null) {
                        append("const _mapper_domain_$domainName: Pkg.$domainName = Pkg.to$domainName(Pkg.createTest$payloadName());\n\n")
                    }
                }
                if (!config.customDtsSmokeTestCode.isNullOrBlank()) {
                    append("\n").append(config.customDtsSmokeTestCode).append("\n")
                }
            }
            testFile.writeText(dtsTestCode)

            val command = if (tscJs.exists()) {
                listOf("node", tscJs.absolutePath, "--noEmit", "--strict", "--target", "es2020", "--lib", "es2020,dom", "--moduleResolution", "node", "--skipDefaultLibCheck", indexDts.name, testFile.name)
            } else if (isWindows()) {
                listOf("npx.cmd", "-p", "typescript", "tsc", "--noEmit", "--strict", "--target", "es2020", "--lib", "es2020,dom", "--moduleResolution", "node", "--skipDefaultLibCheck", indexDts.name, testFile.name)
            } else {
                listOf("npx", "-p", "typescript", "tsc", "--noEmit", "--strict", "--target", "es2020", "--lib", "es2020,dom", "--moduleResolution", "node", "--skipDefaultLibCheck", indexDts.name, testFile.name)
            }

            val result = runCommand(command, outputDir)
            if (result.exitCode != 0) {
                File("build/npm-verifier-error.log").writeText("TSC COMPILATION ERROR:\n${result.output}")
            }
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
        val d = '$'
        try {
            val runtimeTestCode = buildString {
                append("import * as Pkg from './index.js';\n")
                append("import process from 'process';\n\n")
                append("let errors = 0;\n\n")

                append(
                    """
const keys = Object.keys(Pkg);
const fixtureKeys = keys.filter(k => k.startsWith('createTest') && !k.endsWith('List') && typeof Pkg[k] === 'function');

for (const key of fixtureKeys) {
  try {
    const mock = Pkg[key]();
    if (mock === undefined) {
      console.error(`Fixture ${d}{key}() returned undefined`);
      errors++;
    }

    const listKey = key + 'List';
    if (typeof Pkg[listKey] === 'function') {
      const mockList = Pkg[listKey](2);
      if (!Array.isArray(mockList) || mockList.length !== 2) {
        console.error(`Fixture list ${d}{listKey}(2) returned invalid array`);
        errors++;
      }
    }

    const entityName = key.replace(/^createTest/, '');
    const schemaName = entityName.charAt(0).toLowerCase() + entityName.slice(1) + 'Schema';
    const schema = Pkg[schemaName];

    if (schema && typeof schema.parse === 'function') {
      try {
        schema.parse(mock);
      } catch (zodErr) {
        console.error(`Fixture ${d}{key}() failed Zod validation against ${d}{schemaName}:`, zodErr.message);
        errors++;
      }
    }
  } catch (err) {
    console.error(`Fixture ${d}{key}() execution threw an error:`, err);
    errors++;
  }
}

for (const key of keys) {
  if (key.endsWith('ParamsParser')) {
    const parser = Pkg[key];
    if (parser && typeof parser.parse === 'function' && typeof parser.toQueryMap === 'function' && typeof parser.toSearchString === 'function') {
      const baseName = key.replace(/Parser$/, '');
      const fixtureName = 'createTest' + baseName;
      if (typeof Pkg[fixtureName] === 'function') {
        try {
          const paramsMock = Pkg[fixtureName]();
          const parsed = parser.parse(paramsMock);
          const qMap = parser.toQueryMap(parsed);
          const qs = parser.toSearchString(parsed);
          if (!qMap || typeof qMap !== 'object' || typeof qs !== 'string') {
            console.error(`Parser ${d}{key} returned invalid query map or search string`);
            errors++;
          }
        } catch (parserErr) {
          console.error(`Parser ${d}{key} verification failed:`, parserErr);
          errors++;
        }
      }
    }
  }
}
                    """.trimIndent()
                ).append("\n\n")

                config.domainToDtoMappers.forEach { (domainClass, payloadClass) ->
                    val domainName = domainClass.simpleName
                    val payloadName = payloadClass?.simpleName
                    if (domainName != null && payloadName != null) {
                        val payloadSchemaName = payloadName.decapitalizeFirstLetter() + "Schema"
                        append(
                            """
try {
  if (typeof Pkg.createTest$payloadName === 'function' && typeof Pkg.to$domainName === 'function') {
    const payloadMock = Pkg.createTest$payloadName();
    const domainObj = Pkg.to$domainName(payloadMock);
    if (domainObj === undefined || domainObj === null) {
      console.error("to$domainName returned null/undefined for test payload");
      errors++;
    }
    if (typeof Pkg.to$payloadName === 'function' && Pkg.$payloadSchemaName && typeof Pkg.$payloadSchemaName.parse === 'function') {
      const remappedPayload = Pkg.to$payloadName(domainObj);
      Pkg.$payloadSchemaName.parse(remappedPayload);
    }
  }
} catch (mapperErr) {
  console.error("Domain mapper verification failed for $domainName / $payloadName:", mapperErr);
  errors++;
}
                            """.trimIndent()
                        ).append("\n\n")
                    }
                }

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
            if (result.exitCode != 0) {
                File("build/npm-verifier-error.log").writeText("RUNTIME BEHAVIOR ERROR:\n${result.output}")
            }
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
                        symbol == "toQuerySearchString" -> "export const toQuerySearchString = (x) => '';"
                        symbol == "toQueryMap" || symbol.startsWith("toQueryMap") || symbol.startsWith("serialize") -> "export const $symbol = (x) => ({});"
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
