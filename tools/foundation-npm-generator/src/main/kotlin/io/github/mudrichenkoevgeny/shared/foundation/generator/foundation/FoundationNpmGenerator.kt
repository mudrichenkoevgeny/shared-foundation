package io.github.mudrichenkoevgeny.shared.foundation.generator.foundation

import io.github.mudrichenkoevgeny.npmgenerator.engine.NpmGeneratorEngine
import java.io.File
import java.io.IOException

internal fun main() {
    val currentDir = File(System.getProperty("user.dir"))
    val rootDir = if (currentDir.name == "foundation-npm-generator" || currentDir.name == "npm-generator") {
        currentDir.parentFile.parentFile
    } else {
        currentDir
    }
    val outputDir = File(rootDir, "build/npm-package")
    outputDir.mkdirs()

    val packageVersion = resolvePackageVersion(rootDir)
    val zodVersion = resolveZodVersion(rootDir)
    val tsVersion = resolveTypescriptVersion(rootDir)
    val config = buildFoundationNpmConfig(rootDir, outputDir, packageVersion, zodVersion, tsVersion)

    try {
        NpmGeneratorEngine(config).generate()
        syncToLocalWebSdk(rootDir, outputDir)
        println("Successfully generated NPM package in ${outputDir.absolutePath}")
    } catch (e: IllegalStateException) {
        logAndRethrow(e)
    } catch (e: IllegalArgumentException) {
        logAndRethrow(e)
    } catch (e: IOException) {
        logAndRethrow(e)
    }
}

private fun logAndRethrow(e: Throwable): Nothing {
    File("build/generator-error.log").apply {
        parentFile.mkdirs()
        writeText(e.stackTraceToString())
    }
    throw e
}

private fun syncToLocalWebSdk(rootDir: File, outputDir: File) {
    val localWebSdkDir = File(rootDir.parentFile, "web-platform-sdk/packages/core-common/src/generated")
    if (localWebSdkDir.exists()) {
        outputDir.copyRecursively(localWebSdkDir, overwrite = true)
        println("Synced generated files to local web-platform-sdk: ${localWebSdkDir.absolutePath}")
    }
}

private fun resolvePackageVersion(rootDir: File): String =
    System.getProperty("PACKAGE_VERSION")?.takeIf { it.isNotBlank() && it != "unspecified" }
        ?: System.getenv("PACKAGE_VERSION")?.takeIf { it.isNotBlank() }
        ?: parseRootBuildGradleVersion(rootDir)
        ?: error("PACKAGE_VERSION is not set and could not be parsed from root build.gradle.kts")

private fun resolveZodVersion(rootDir: File): String =
    System.getProperty("ZOD_VERSION")?.takeIf { it.isNotBlank() }
        ?: parseVersionFromToml(rootDir, "npmZod")
        ?: error("ZOD_VERSION system property is not set")

private fun resolveTypescriptVersion(rootDir: File): String =
    System.getProperty("TYPESCRIPT_VERSION")?.takeIf { it.isNotBlank() }
        ?: parseVersionFromToml(rootDir, "npmTypescript")
        ?: error("TYPESCRIPT_VERSION system property is not set")

private fun parseVersionFromToml(rootDir: File, key: String): String? {
    val tomlFile = File(rootDir, "gradle/libs.versions.toml")
    if (!tomlFile.exists()) return null
    val regex = Regex("""^\s*$key\s*=\s*"([^"]+)"""")
    return tomlFile.useLines { lines ->
        lines.firstNotNullOfOrNull { regex.find(it)?.groupValues?.get(1) }
    }
}

private fun parseRootBuildGradleVersion(rootDir: File): String? {
    val file = File(rootDir, "build.gradle.kts")
    if (!file.exists()) return null
    val regex = Regex("""version\s*=\s*"([^"]+)"""")
    return file.useLines { lines ->
        lines.mapNotNull { line -> regex.find(line)?.groupValues?.get(1) }.firstOrNull()
    }
}
