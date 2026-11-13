package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.quality

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

/**
 * Gradle task for synchronizing multi-layer .editorconfig rules into project root directory.
 */
@DisableCachingByDefault(because = "Merges editorconfig layers and local overrides, execution is fast")
public abstract class EditorConfigSyncTask : DefaultTask() {

    private val layers: MutableList<Layer> = mutableListOf()

    /**
     * Destination .editorconfig file property.
     */
    @get:OutputFile
    public abstract val outputFile: RegularFileProperty

    init {
        outputs.upToDateWhen { false }
    }

    /**
     * Registers a new editorconfig resource layer provider.
     *
     * @param classLoader ClassLoader to inspect for resource.
     * @param resourcePath Path to editorconfig layer resource.
     */
    public fun addLayer(
        classLoader: ClassLoader,
        resourcePath: String
    ) {
        if (layers.none { it.resourcePath == resourcePath }) {
            layers.add(Layer(classLoader, resourcePath))
        }
    }

    /**
     * Synchronizes registered editorconfig layers with local overrides into output file.
     */
    @TaskAction
    public fun sync() {
        val marker = "# --- LOCAL OVERRIDES ---"
        val defaultOverrideText = "# Add project-specific editorconfig rules below"

        val joinedLayers = layers.mapNotNull { layer ->
            val cleanPath = layer.resourcePath.removePrefix("/")
            val stream = layer.classLoader.getResourceAsStream(cleanPath)
                ?: EditorConfigSyncTask::class.java.getResourceAsStream("/$cleanPath")
            checkNotNull(stream) { "EditorConfig layer resource not found: ${layer.resourcePath}" }
            val rawText = stream.bufferedReader().use { it.readText() }
            val layerContent = rawText.substringBefore(marker).trim()
            layerContent.ifBlank { null }
        }.joinToString("\n\n")

        val file = outputFile.get().asFile

        val preservedLocalOverrides = if (file.exists()) {
            val currentText = file.readText()
            if (currentText.contains(marker)) {
                val tail = currentText.substringAfter(marker).trim()
                tail.ifBlank { defaultOverrideText }
            } else if (currentText.isNotBlank()) {
                currentText.trim()
            } else {
                defaultOverrideText
            }
        } else {
            defaultOverrideText
        }

        val fullText = buildString {
            if (joinedLayers.isNotBlank()) {
                append(joinedLayers.trim())
                append("\n\n")
            }
            append(marker)
            append("\n")
            append(preservedLocalOverrides.trim())
            append("\n")
        }

        val normalizedContent = fullText.replace("\r\n", "\n")
        val currentNormalized = if (file.exists()) file.readText().replace("\r\n", "\n") else null

        if (currentNormalized != normalizedContent) {
            file.parentFile?.mkdirs()
            file.writeText(normalizedContent)
        }
    }

    private data class Layer(
        val classLoader: ClassLoader,
        val resourcePath: String
    )
}
