package io.github.mudrichenkoevgeny.shared.foundation.plugin.agent

import io.github.mudrichenkoevgeny.shared.foundation.plugin.utils.ResourceUtils
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.File

/**
 * Task extracting global agent rules from plugin resources or local repository into .agent/global/.
 */
@DisableCachingByDefault(because = "Copies files from resources or local directory, execution is fast")
public abstract class AgentSyncTask : DefaultTask() {

    @get:Internal
    private val extraLayers: MutableList<Layer> = mutableListOf()

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
     * Registers an additional agent rules layer to be synchronized into a specific subdirectory.
     *
     * @param classLoader ClassLoader to inspect for resource.
     * @param resourcePath Path to agent rules resource directory.
     * @param targetSubdir Target subdirectory inside agent rules directory.
     */
    public fun addLayer(classLoader: ClassLoader, resourcePath: String, targetSubdir: String) {
        if (extraLayers.none { it.targetSubdir == targetSubdir }) {
            extraLayers.add(Layer(classLoader, resourcePath, targetSubdir))
        }
    }

    /**
     * Synchronizes global AI agent rules into the configured output directory.
     */
    @TaskAction
    public fun sync() {
        val targetDir = outputDirectory.get().asFile
        val commonDir = File(targetDir, "common")
        val kotlinDir = File(targetDir, "kotlin")
        val sharedDir = File(targetDir, "shared")

        val classLoader = javaClass.classLoader
        ResourceUtils.copyResourceFolder(classLoader, "agent-rules/common", commonDir, logger)
        ResourceUtils.copyResourceFolder(classLoader, "agent-rules/kotlin", kotlinDir, logger)

        if (includeShared.get()) {
            ResourceUtils.copyResourceFolder(classLoader, "agent-rules/shared", sharedDir, logger)
        } else {
            sharedDir.deleteRecursively()
        }

        extraLayers.forEach { layer ->
            val layerDir = File(targetDir, layer.targetSubdir)
            ResourceUtils.copyResourceFolder(layer.classLoader, layer.resourcePath, layerDir, logger)
        }
    }

    private data class Layer(
        val classLoader: ClassLoader,
        val resourcePath: String,
        val targetSubdir: String
    )
}
