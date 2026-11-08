package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.validation

private const val WILDCARD_SUFFIX = "/" + "*"

/**
 * Pure Kotlin Multiplatform validation utilities for client file upload rules and filters.
 */
public object StorageUploadValidator {
    /**
     * Checks if a file matches the specified accepted file types filter list.
     *
     * Supports extension filters (e.g., `".png"`, `"jpg"`), exact MIME types (e.g., `"image/png"`),
     * and MIME type wildcard patterns (e.g., `"image/png"` or `"image" wildcard`). Matching is case-insensitive.
     *
     * @param acceptedFileTypes List of accepted file extension or MIME type patterns. If empty, all files are accepted.
     * @param fileName Original file name including extension (e.g., `"avatar.png"`).
     * @param mimeType Optional MIME type of the file (e.g., `"image/png"`).
     * @return `true` if the file matches at least one accepted type filter or if filters are empty.
     */
    public fun isFileTypeAccepted(
        acceptedFileTypes: List<String>,
        fileName: String,
        mimeType: String? = null
    ): Boolean {
        if (acceptedFileTypes.isEmpty()) return true

        val lowerFileName = fileName.lowercase()
        val extension =
            lowerFileName.substringAfterLast('.', "").let {
                if (it.isNotEmpty()) ".$it" else ""
            }
        val normalizedMimeType = mimeType?.lowercase()?.trim()

        return acceptedFileTypes.any { rawFilter ->
            val filter = rawFilter.trim().lowercase()
            if (filter.isBlank()) return@any false

            when {
                filter.startsWith(".") -> {
                    extension == filter || lowerFileName.endsWith(filter)
                }

                filter.endsWith(WILDCARD_SUFFIX) -> {
                    val mimePrefix = filter.removeSuffix(WILDCARD_SUFFIX)
                    normalizedMimeType != null && normalizedMimeType.startsWith("$mimePrefix/")
                }

                filter.contains('/') -> {
                    normalizedMimeType == filter
                }

                else -> {
                    extension == ".$filter"
                }
            }
        }
    }

    /**
     * Checks if the given file size in bytes does not exceed the maximum permitted limit.
     *
     * @param maxFileSizeBytes Maximum permitted file size in bytes. If `null` or `<= 0`, size is unrestricted.
     * @param fileSizeBytes Actual size of the target file in bytes.
     * @return `true` if file size is within limits.
     */
    public fun isFileSizeValid(
        maxFileSizeBytes: Long?,
        fileSizeBytes: Long
    ): Boolean {
        if (maxFileSizeBytes == null || maxFileSizeBytes <= 0) return true
        return fileSizeBytes in 0..maxFileSizeBytes
    }

    /**
     * Checks if a total file count does not exceed the maximum permitted batch upload limit.
     *
     * @param maxFiles Maximum permitted file count. If `null` or `<= 0`, count is unrestricted.
     * @param currentCount Target file count to check.
     * @return `true` if file count is within limits.
     */
    public fun isBatchSizeValid(
        maxFiles: Int?,
        currentCount: Int
    ): Boolean {
        if (maxFiles == null || maxFiles <= 0) return currentCount >= 0
        return currentCount in 0..maxFiles
    }
}
