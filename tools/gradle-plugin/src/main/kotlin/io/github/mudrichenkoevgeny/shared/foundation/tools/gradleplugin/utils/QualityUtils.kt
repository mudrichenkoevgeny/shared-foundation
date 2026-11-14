package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.utils

import dev.detekt.gradle.Detekt
import dev.detekt.gradle.extensions.DetektExtension
import io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.quality.EditorConfigSyncTask
import org.gradle.api.Project
import java.io.File

/**
 * Utility object for configuring Detekt rule dependencies, config extraction, and plugin version resolution across Gradle plugins.
 */
public object QualityUtils {

    /**
     * Applies a custom Detekt rule set dependency to the target project.
     * Uses local subproject dependency if the subproject exists in the owner build, or published Maven coordinates otherwise.
     *
     * @param target Project to configure.
     * @param expectedRootProjectName Root project name of the owner repository (e.g. "shared-foundation").
     * @param rulesProjectPath Gradle project path to local detekt rules subproject (e.g. ":tools:detekt-rules").
     * @param mavenCoordinates Published Maven group and artifact ID (e.g. "io.github.mudrichenkoevgeny:shared-foundation-detekt-rules").
     * @param pluginVersion Version string of published Maven artifact.
     */
    public fun applyDetektRulesDependency(
        target: Project,
        expectedRootProjectName: String,
        rulesProjectPath: String,
        mavenCoordinates: String,
        pluginVersion: String
    ) {
        val isOwnerRepo = target.rootProject.name == expectedRootProjectName

        if (!isOwnerRepo) {
            target.dependencies.add(CONFIGURATION_DETEKT_PLUGINS, "$mavenCoordinates:$pluginVersion")
            return
        }

        if (target.path != rulesProjectPath) {
            val rulesProject = target.rootProject.findProject(rulesProjectPath)
            if (rulesProject != null) {
                target.dependencies.add(
                    CONFIGURATION_DETEKT_PLUGINS,
                    target.dependencies.project(mapOf(KEY_PATH to rulesProjectPath))
                )
            }
        } else {
            target.tasks.withType(Detekt::class.java).configureEach { detektTask ->
                detektTask.dependsOn(TASK_JAR)
                detektTask.pluginClasspath.from(target.tasks.named(TASK_JAR))
            }
        }
    }

    /**
     * Registers a task to extract a Detekt YAML config resource and appends it to the project's DetektExtension configuration.
     * Re-appends local config/detekt/detekt.yml at the end of detekt.config to guarantee highest override priority.
     *
     * @param target Project to configure.
     * @param taskName Name for the extraction task (e.g. "extractDetektBaseConfig").
     * @param classLoader ClassLoader to inspect for resource.
     * @param resourcePath Path to the YAML resource inside ClassLoader resources (e.g. "config/detekt/detekt-base.yml").
     * @param outputFileName Destination file name inside build directory (e.g. "detekt-base.yml").
     */
    public fun registerDetektConfigExtraction(
        target: Project,
        taskName: String,
        classLoader: ClassLoader,
        resourcePath: String,
        outputFileName: String
    ) {
        val extractTask = target.tasks.register(taskName) { task ->
            val outputFile = target.layout.buildDirectory.file("$DEFAULT_DETEKT_OUTPUT_DIR$outputFileName")
            task.outputs.file(outputFile)
            task.doLast {
                val file = outputFile.get().asFile
                file.parentFile.mkdirs()
                val cleanPath = resourcePath.removePrefix("/")
                val stream = classLoader.getResourceAsStream(cleanPath)
                    ?: QualityUtils::class.java.getResourceAsStream("/$cleanPath")
                checkNotNull(stream) { "Detekt config resource not found: $resourcePath" }
                stream.use { input ->
                    file.outputStream().use { output -> input.copyTo(output) }
                }
            }
        }

        val extractedFileProvider = extractTask.map { it.outputs.files.singleFile }
        val topLevelDir = resolveTopLevelRepositoryDir(target)
        val localConfigFile = File(topLevelDir, DEFAULT_LOCAL_DETEKT_CONFIG_PATH)

        target.extensions.configure(DetektExtension::class.java) { detekt ->
            val existingConfigs = detekt.config.from.filterNot { it == localConfigFile }
            if (localConfigFile.exists()) {
                detekt.config.setFrom(existingConfigs, extractedFileProvider, localConfigFile)
            } else {
                detekt.config.setFrom(existingConfigs, extractedFileProvider)
            }
        }

        target.tasks.withType(Detekt::class.java).configureEach { detektTask ->
            detektTask.dependsOn(extractTask)
        }
    }

    /**
     * Registers an editorconfig resource layer and ensures single root syncEditorConfig task exists.
     * Skips registration if the project belongs to an included or nested sub-build inside a parent repository.
     *
     * @param target Target project.
     * @param classLoader ClassLoader containing editorconfig resource.
     * @param resourcePath Path to editorconfig layer resource file.
     */
    public fun registerEditorConfigLayer(
        target: Project,
        classLoader: ClassLoader,
        resourcePath: String
    ) {
        if (isNestedBuild(target)) {
            return
        }

        val root = target.rootProject

        val syncTaskProvider = if (root.tasks.names.contains(TASK_SYNC_EDITORCONFIG)) {
            root.tasks.named(TASK_SYNC_EDITORCONFIG, EditorConfigSyncTask::class.java)
        } else {
            val newTask = root.tasks.register(TASK_SYNC_EDITORCONFIG, EditorConfigSyncTask::class.java) { task ->
                task.group = TASK_GROUP_QUALITY
                task.description = "Synchronizes shared .editorconfig rules into project root directory"
                task.outputFile.set(root.layout.projectDirectory.file(FILE_EDITORCONFIG))
            }

            root.tasks.matching {
                (it.name == TASK_PREPARE_BUILD_SCRIPT_MODEL) || (it.name == TASK_PREPARE_IDEA_IMPORT)
            }.configureEach { task ->
                task.dependsOn(newTask)
            }

            newTask
        }

        syncTaskProvider.configure { task ->
            task.addLayer(classLoader, resourcePath)
        }

        target.tasks.matching {
            it.name.contains(KTLINT_TASK_KEYWORD, ignoreCase = true)
        }.configureEach { task ->
            task.dependsOn(syncTaskProvider)
        }
    }

    /**
     * Resolves plugin version from a specified version resource or Manifest implementation version without hardcoding.
     *
     * @param classLoader ClassLoader to inspect for version resource.
     * @param versionResourceName Name of the resource file (e.g. "shared-foundation-plugin-version.txt").
     * @param manifestPackage Package metadata to fallback for implementationVersion.
     * @return Resolved version string.
     * @throws IllegalStateException if version cannot be resolved from resource or Manifest.
     */
    public fun resolvePluginVersion(
        classLoader: ClassLoader,
        versionResourceName: String,
        manifestPackage: Package?
    ): String {
        val cleanName = versionResourceName.removePrefix("/")
        val resourceStream = classLoader.getResourceAsStream(cleanName)
            ?: QualityUtils::class.java.getResourceAsStream("/$cleanName")
        if (resourceStream != null) {
            val versionFromResource = resourceStream.bufferedReader().use { it.readText().trim() }
            if (versionFromResource.isNotBlank()) {
                return versionFromResource
            }
        }
        val implementationVersion = manifestPackage?.implementationVersion
        check(!implementationVersion.isNullOrBlank()) {
            "Unable to resolve plugin version from '$versionResourceName' resource or Manifest"
        }
        return implementationVersion
    }

    private fun isNestedBuild(target: Project): Boolean {
        if (target.gradle.parent != null) {
            return true
        }
        val rootDir = target.rootProject.projectDir
        return resolveTopLevelRepositoryDir(target) != rootDir
    }

    private fun resolveTopLevelRepositoryDir(target: Project): File {
        var currentDir = target.rootProject.projectDir
        var topMostGradleDir = currentDir
        var parentDir = currentDir.parentFile

        while (parentDir != null) {
            val hasSettingsKts = File(parentDir, FILE_SETTINGS_GRADLE_KTS).exists()
            val hasSettingsGroovy = File(parentDir, FILE_SETTINGS_GRADLE).exists()
            if (hasSettingsKts || hasSettingsGroovy) {
                topMostGradleDir = parentDir
            }
            parentDir = parentDir.parentFile
        }

        return topMostGradleDir
    }

    private const val CONFIGURATION_DETEKT_PLUGINS = "detektPlugins"
    private const val TASK_JAR = "jar"
    private const val KEY_PATH = "path"

    private const val DEFAULT_DETEKT_OUTPUT_DIR = "generated/detekt/"
    private const val DEFAULT_LOCAL_DETEKT_CONFIG_PATH = "config/detekt/detekt.yml"

    private const val TASK_SYNC_EDITORCONFIG = "syncEditorConfig"
    private const val TASK_GROUP_QUALITY = "quality"
    private const val FILE_EDITORCONFIG = ".editorconfig"
    private const val FILE_SETTINGS_GRADLE_KTS = "settings.gradle.kts"
    private const val FILE_SETTINGS_GRADLE = "settings.gradle"

    private const val TASK_PREPARE_BUILD_SCRIPT_MODEL = "prepareKotlinBuildScriptModel"
    private const val TASK_PREPARE_IDEA_IMPORT = "prepareKotlinIdeaImport"
    private const val KTLINT_TASK_KEYWORD = "ktlint"
}
