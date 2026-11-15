package io.github.mudrichenkoevgeny.shared.foundation.tools.gradleplugin.utils

import org.gradle.api.logging.Logger
import java.io.File
import java.net.JarURLConnection
import java.net.URL

/**
 * Utility object for copying resource folders from plugin JARs or file system into target directories.
 */
public object ResourceUtils {
    /**
     * Resolves a resource directory URL from the given [classLoader].
     *
     * @param classLoader The ClassLoader to inspect.
     * @param resourcePath Relative resource path to resolve.
     * @return Resolved [URL] or `null` if not found.
     */
    public fun resolveResourceUrl(
        classLoader: ClassLoader,
        resourcePath: String
    ): URL? {
        val normalizedPath = resourcePath.removePrefix("/").trimEnd('/')
        return classLoader.getResource(normalizedPath)
            ?: classLoader.getResource("$normalizedPath/")
    }

    /**
     * Copies a resource directory from the class loader into a target file directory.
     *
     * @param classLoader The ClassLoader to load resources from.
     * @param resourcePath Relative resource path to copy.
     * @param targetFolder Destination folder on the file system.
     * @param logger Gradle Logger instance for logging warnings.
     */
    public fun copyResourceFolder(
        classLoader: ClassLoader,
        resourcePath: String,
        targetFolder: File,
        logger: Logger
    ) {
        val resourceUrl = resolveResourceUrl(classLoader, resourcePath)
        copyResourceFolder(resourceUrl, resourcePath, targetFolder, logger)
    }

    /**
     * Copies a pre-resolved resource directory [URL] into a target file directory.
     *
     * @param resourceUrl Resolved URL of the resource folder.
     * @param resourcePath Original relative resource path (used for JAR entry prefix and logging).
     * @param targetFolder Destination folder on the file system.
     * @param logger Gradle Logger instance for logging warnings.
     */
    public fun copyResourceFolder(
        resourceUrl: URL?,
        resourcePath: String,
        targetFolder: File,
        logger: Logger
    ) {
        if (resourceUrl == null) {
            logger.warn("Resource folder not found: $resourcePath")
            return
        }

        val normalizedPath = resourcePath.removePrefix("/").trimEnd('/')

        when (resourceUrl.protocol) {
            PROTOCOL_FILE -> {
                val sourceDir = File(resourceUrl.toURI())
                if (sourceDir.canonicalFile == targetFolder.canonicalFile) {
                    return
                }

                targetFolder.deleteRecursively()
                targetFolder.mkdirs()

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

            PROTOCOL_JAR -> {
                val connection = resourceUrl.openConnection()
                connection.useCaches = false
                val jarConnection = connection as? JarURLConnection ?: return

                targetFolder.deleteRecursively()
                targetFolder.mkdirs()

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

            else -> logger.warn("Unsupported resource protocol '${resourceUrl.protocol}' for path:$resourcePath")
        }
    }

    private const val PROTOCOL_FILE = "file"
    private const val PROTOCOL_JAR = "jar"
}
