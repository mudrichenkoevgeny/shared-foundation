package io.github.mudrichenkoevgeny.shared.foundation.tools.projectgenerator

import java.io.File
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

internal fun main() {
    val config = ProjectGeneratorConfig.fromSystemProperties()
    ProjectGenerator(config).generate()
}

internal class ProjectGenerator(private val config: ProjectGeneratorConfig) {

    fun generate() {
        val targetDir = config.targetDir.canonicalFile
        if (targetDir.exists()) {
            val files = targetDir.list()?.toList() ?: emptyList()
            if (files.any { CONFLICTING_ENTRIES.contains(it) }) {
                error("Target directory '${targetDir.absolutePath}' already contains project files. Aborting.")
            }
        }

        println("Scaffolding contract repository for '${config.projectName}' in ${targetDir.absolutePath}...")
        targetDir.mkdirs()

        copyFoundationFiles(targetDir)
        extractAndProcessTemplates(targetDir)
        createBaseSourceDirectories(targetDir)

        if (!config.skipGit && !File(targetDir, GIT_DIR).exists()) {
            try {
                ProcessBuilder("git", "init").directory(targetDir).start().waitFor()
            } catch (e: IOException) {
                println("Failed to initialize git repository: ${e.message}")
            } catch (e: InterruptedException) {
                println("Failed to initialize git repository: ${e.message}")
            }
        }

        println("\nSuccessfully scaffolded '${config.projectRepoName}'!")
        println("Next steps:")
        println("  cd ${targetDir.absolutePath}")
        println("  ./gradlew check")
        println("  ./gradlew generateNpmPackage\n")
    }

    private fun copyFoundationFiles(targetDir: File) {
        for (relPath in FOUNDATION_FILES_TO_COPY) {
            val srcFile = File(config.foundationRootDir, relPath)
            if (srcFile.exists()) {
                val destFile = File(targetDir, relPath)
                destFile.parentFile.mkdirs()
                srcFile.copyTo(destFile, overwrite = true)
                if (relPath == GRADLEW_FILE) {
                    destFile.setExecutable(true)
                }
            }
        }

        val agentDir = File(config.foundationRootDir, AGENT_GLOBAL_DIR)
        if (agentDir.exists()) {
            val destAgentDir = File(targetDir, AGENT_GLOBAL_DIR)
            agentDir.copyRecursively(destAgentDir, overwrite = true)
        }
    }

    private fun createBaseSourceDirectories(targetDir: File) {
        val baseCoreCommon = File(targetDir, "core/common/src/commonMain/kotlin/${config.packageDirPath}/core/common")
        val baseTesting = File(targetDir, "testing/src/commonMain/kotlin/${config.packageDirPath}/testing")
        baseCoreCommon.mkdirs()
        baseTesting.mkdirs()
    }

    private fun extractAndProcessTemplates(targetDir: File) {
        val replacements = TemplateReplacer.buildReplacementMap(config)
        val resourceUrl = javaClass.classLoader.getResource(TEMPLATES_RESOURCE_DIR)
            ?: error("Templates not found in resources.")

        if (resourceUrl.protocol == JAR_PROTOCOL) {
            val uri = resourceUrl.toURI()
            val fs = try {
                FileSystems.getFileSystem(uri)
            } catch (_: IOException) {
                FileSystems.newFileSystem(uri, emptyMap<String, Any>())
            }
            val myPath = fs.getPath("/$TEMPLATES_RESOURCE_DIR")
            processPaths(myPath, targetDir, replacements)
        } else {
            val myPath = Paths.get(resourceUrl.toURI())
            processPaths(myPath, targetDir, replacements)
        }
    }

    private fun processPaths(
        sourceRoot: Path,
        targetDir: File,
        replacements: Map<String, String>
    ) {
        Files.walk(sourceRoot).use { stream ->
            stream.filter { Files.isRegularFile(it) }.forEach { path ->
                val relPath = sourceRoot.relativize(path).toString().replace("\\", "/")
                val content = Files.readString(path)
                val processedContent = TemplateReplacer.replaceAll(content, replacements)

                val rawDestPath = relPath.removeSuffix(TEMPLATE_SUFFIX)
                val processedDestPath = TemplateReplacer.replaceAll(rawDestPath, replacements)

                if (processedDestPath.endsWith("/")) {
                    File(targetDir, processedDestPath).mkdirs()
                } else {
                    val destFile = File(targetDir, processedDestPath)
                    destFile.parentFile.mkdirs()
                    destFile.writeText(processedContent)
                }
            }
        }
    }

    private companion object {
        private const val TEMPLATES_RESOURCE_DIR = "templates"
        private const val JAR_PROTOCOL = "jar"
        private const val TEMPLATE_SUFFIX = ".template"
        private const val GRADLEW_FILE = "gradlew"
        private const val AGENT_GLOBAL_DIR = ".agent/global"
        private const val GIT_DIR = ".git"

        private val CONFLICTING_ENTRIES =
            listOf("settings.gradle.kts", "build.gradle.kts", "core", "feature", "testing", "gradlew")
        private val FOUNDATION_FILES_TO_COPY = listOf(
            "gradlew",
            "gradlew.bat",
            "gradle/wrapper/gradle-wrapper.jar",
            "gradle/wrapper/gradle-wrapper.properties",
            "gradle/gradle-daemon-jvm.properties",
            ".editorconfig"
        )
    }
}
