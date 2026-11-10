package io.github.mudrichenkoevgeny.shared.foundation.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File
import java.net.JarURLConnection

/**
 * Extension for configuring agent rules synchronization in consumer projects.
 */
public abstract class AgentSyncExtension {
    /**
     * Whether to include shared contract/foundation rules (`.agent/global/shared/`).
     * Defaults to `true` for shared foundation/contract projects, `false` for application SDKs.
     */
    public abstract val includeShared: Property<Boolean>
}

/**
 * Gradle plugin for synchronizing shared AI agent rules into consumer projects.
 */
public class AgentSyncPlugin : Plugin<Project> {
    /**
     * Applies the agent sync task configuration to the target Gradle project.
     *
     * @param target The target Gradle project to configure.
     */
    override fun apply(target: Project) {
        if (target != target.rootProject) {
            target.logger.warn("AgentSyncPlugin should only be applied to the root project")
            return
        }

        val extension = target.extensions.create("agentSync", AgentSyncExtension::class.java)
        extension.includeShared.convention(target.name == "shared-foundation")

        val syncTask = target.tasks.register("syncAgentRules", AgentSyncTask::class.java) { task ->
            task.group = "documentation"
            task.description = "Synchronizes shared AI agent rules into .agent/global/ directory"
            task.rootDirectory.convention(target.layout.projectDirectory)
            task.outputDirectory.convention(target.layout.projectDirectory.dir(".agent/global"))
            task.includeShared.convention(extension.includeShared)
        }

        target.tasks.matching {
            it.name == "prepareKotlinBuildScriptModel" || it.name == "prepareKotlinIdeaImport"
        }.configureEach { task ->
            task.dependsOn(syncTask)
        }
    }
}

/**
 * Task extracting global agent rules from plugin resources or local repository into .agent/global/.
 */
@DisableCachingByDefault(because = "Copies files from resources or local directory, execution is fast")
public abstract class AgentSyncTask : DefaultTask() {

    /**
     * Root directory property of the target project.
     */
    @get:Internal
    public abstract val rootDirectory: DirectoryProperty

    /**
     * Target directory property where agent rules will be synchronized.
     */
    @get:Internal
    public abstract val outputDirectory: DirectoryProperty

    /**
     * Whether to sync shared library/contract rules into `.agent/global/shared/`.
     */
    @get:Input
    public abstract val includeShared: Property<Boolean>

    init {
        outputs.upToDateWhen { false }
    }

    /**
     * Synchronizes global AI agent rules into the configured output directory.
     */
    @TaskAction
    public fun sync() {
        val targetDir = outputDirectory.get().asFile
        val rootDir = rootDirectory.get().asFile
        val commonDir = File(targetDir, "common")
        val kotlinDir = File(targetDir, "kotlin")
        val sharedDir = File(targetDir, "shared")

        commonDir.deleteRecursively()
        kotlinDir.deleteRecursively()
        sharedDir.deleteRecursively()

        commonDir.mkdirs()
        kotlinDir.mkdirs()

        val classLoader = javaClass.classLoader
        copyResourceFolder(classLoader, "agent-rules/common", commonDir)
        copyResourceFolder(classLoader, "agent-rules/kotlin", kotlinDir)

        if (includeShared.get()) {
            sharedDir.mkdirs()
            copyResourceFolder(classLoader, "agent-rules/shared", sharedDir)
        }

        syncEditorConfig(rootDir, classLoader)
    }

    private fun syncEditorConfig(
        rootDir: File,
        classLoader: ClassLoader
    ) {
        val localEditorConfig = File(rootDir, ".editorconfig")
        val baseConfigStream = classLoader.getResourceAsStream("config/editorconfig/.editorconfig") ?: return
        val rawConfigText = baseConfigStream.bufferedReader().use { it.readText() }

        val marker = "# --- LOCAL OVERRIDES ---"
        val baseConfigText = rawConfigText.substringBefore(marker).trim()

        val contentToSave = if (!localEditorConfig.exists()) {
            "$baseConfigText\n\n$marker\n# Add project-specific editorconfig rules below\n"
        } else {
            val currentText = localEditorConfig.readText()
            if (currentText.contains(marker)) {
                val localOverrides = currentText.substringAfter(marker)
                "$baseConfigText\n\n$marker$localOverrides"
            } else {
                "$baseConfigText\n\n$marker\n\n$currentText"
            }
        }

        val normalizedContent = contentToSave.replace("\r\n", "\n")
        val currentNormalized =
            if (localEditorConfig.exists()) localEditorConfig.readText().replace("\r\n", "\n") else null

        if (currentNormalized != normalizedContent) {
            localEditorConfig.writeText(normalizedContent)
        }
    }

    private fun copyResourceFolder(
        classLoader: ClassLoader,
        resourcePath: String,
        targetFolder: File
    ) {
        val normalizedPath = resourcePath.trimEnd('/')
        val resourceUrl = classLoader.getResource(normalizedPath)
            ?: classLoader.getResource("$normalizedPath/")

        if (resourceUrl == null) {
            logger.warn("Agent rules resource folder not found: $resourcePath")
            return
        }

        when (resourceUrl.protocol) {
            "file" -> {
                val sourceDir = File(resourceUrl.toURI())
                if (sourceDir.isDirectory) {
                    sourceDir.walkTopDown()
                        .filter { it.isFile }
                        .forEach { file ->
                            val relativePath = file.relativeTo(sourceDir).path
                            val destFile = File(targetFolder, relativePath)
                            destFile.parentFile.mkdirs()
                            file.copyTo(destFile, overwrite = true)
                        }
                }
            }

            "jar" -> {
                val connection = resourceUrl.openConnection()
                connection.useCaches = false
                val jarConnection = connection as? JarURLConnection ?: return

                jarConnection.jarFile.use { jar ->
                    val entries = jar.entries()
                    val prefix = "$normalizedPath/"
                    while (entries.hasMoreElements()) {
                        val entry = entries.nextElement()
                        if (!entry.isDirectory && entry.name.startsWith(prefix)) {
                            val relativeName = entry.name.removePrefix(prefix)
                            if (relativeName.isNotEmpty()) {
                                val destFile = File(targetFolder, relativeName)
                                destFile.parentFile.mkdirs()
                                jar.getInputStream(entry).use { input ->
                                    destFile.outputStream().use { output ->
                                        input.copyTo(output)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
