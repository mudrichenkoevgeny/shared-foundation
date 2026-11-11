package io.github.mudrichenkoevgeny.shared.foundation.plugin.utils

import org.gradle.api.logging.Logger
import java.io.File
import java.net.JarURLConnection

/**
 * Utility object for copying resource folders from plugin JARs or file system into target directories.
 */
public object ResourceUtils {
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
        val normalizedPath = resourcePath.removePrefix("/").trimEnd('/')
        val resourceUrl = classLoader.getResource(normalizedPath)
            ?: classLoader.getResource("$normalizedPath/")

        if (resourceUrl == null) {
            logger.warn("Resource folder not found: $resourcePath")
            return
        }

        when (resourceUrl.protocol) {
            "file" -> {
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

            "jar" -> {
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

            else -> logger.warn("Unsupported resource protocol '${resourceUrl.protocol}' for path: $resourcePath")
        }
    }
}
