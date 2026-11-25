package io.github.mudrichenkoevgeny.shared.foundation.tools.designsystem

import java.io.File
import java.net.URI
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Utility for extracting embedded icon resources (Vector Drawable XML for KMP and SVG for Web).
 */
public object DesignSystemIconExtractor {

    private const val SCHEME_JAR: String = "jar"
    private const val SCHEME_FILE: String = "file"

    /**
     * List of supported module names containing icon assets.
     */
    public val SUPPORTED_ICON_MODULES: List<String> = listOf("core-common", "core-storage", "feature-user")

    /**
     * Extracts Vector Drawable XML icons for [iconModules] into [targetDir].
     *
     * @param iconModules List of module names (e.g. `listOf("core-common", "core-storage")`).
     * @param targetDir Destination directory for Compose Resources (e.g. `src/commonMain/composeResources/drawable`).
     */
    public fun extractDrawableIcons(
        iconModules: List<String>,
        targetDir: File
    ) {
        targetDir.mkdirs()
        iconModules.forEach { moduleName ->
            val resourceDirPath = "design-system/icons/$moduleName/drawable"
            copyResourcesFromClasspath(resourceDirPath, targetDir)
        }
    }

    /**
     * Extracts SVG icons for Web NPM export for all supported modules into [targetDir].
     *
     * @param targetDir Destination directory (e.g. `dist/design-system/icons`).
     */
    public fun extractSvgIconsForWeb(targetDir: File) {
        SUPPORTED_ICON_MODULES.forEach { moduleName ->
            val moduleSvgDir = File(targetDir, moduleName)
            moduleSvgDir.mkdirs()
            val resourceDirPath = "design-system/icons/$moduleName/svg"
            copyResourcesFromClasspath(resourceDirPath, moduleSvgDir)
        }
    }

    private fun copyResourcesFromClasspath(
        resourceDirPath: String,
        targetDir: File
    ) {
        val classLoader = DesignSystemIconExtractor::class.java.classLoader
        val cleanPath = resourceDirPath.trimStart('/')
        val resourceUrl = classLoader.getResource(cleanPath)
            ?: error("Icon resource directory not found at '$cleanPath'")

        val fileEntries = discoverResourceEntries(cleanPath, resourceUrl.toURI())
        check(fileEntries.isNotEmpty()) {
            "No icon files found in resource directory '$cleanPath'"
        }

        fileEntries.forEach { entryName ->
            val fullResourcePath = "$cleanPath/$entryName"
            val stream = classLoader.getResourceAsStream(fullResourcePath)
                ?: error("Failed to open resource stream for icon '$fullResourcePath'")
            val targetFile = File(targetDir, entryName)
            targetFile.parentFile.mkdirs()
            stream.use { input ->
                targetFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    private fun discoverResourceEntries(
        cleanPath: String,
        uri: URI
    ): List<String> = when (uri.scheme) {
        SCHEME_JAR -> {
            val fs = try {
                FileSystems.getFileSystem(uri)
            } catch (_: Exception) {
                try {
                    FileSystems.newFileSystem(uri, emptyMap<String, Any>())
                } catch (_: Exception) {
                    FileSystems.getFileSystem(uri)
                }
            }
            val pathInFs = fs.getPath(if (cleanPath.startsWith("/")) cleanPath else "/$cleanPath")
            Files.walk(pathInFs, 1).use { stream ->
                stream.filter { Files.isRegularFile(it) }
                    .map { it.fileName.toString() }
                    .filter { !it.startsWith(".") }
                    .toList()
            }
        }

        SCHEME_FILE -> {
            val dirPath = Paths.get(uri)
            check(Files.exists(dirPath) && Files.isDirectory(dirPath)) {
                "Icon directory on disk does not exist or is not a directory: $dirPath"
            }
            Files.list(dirPath).use { stream ->
                stream.filter { Files.isRegularFile(it) }
                    .map { it.fileName.toString() }
                    .filter { !it.startsWith(".") }
                    .toList()
            }
        }

        else -> {
            val dirPath = Paths.get(uri)
            Files.list(dirPath).use { stream ->
                stream.filter { Files.isRegularFile(it) }
                    .map { it.fileName.toString() }
                    .filter { !it.startsWith(".") }
                    .toList()
            }
        }
    }
}
