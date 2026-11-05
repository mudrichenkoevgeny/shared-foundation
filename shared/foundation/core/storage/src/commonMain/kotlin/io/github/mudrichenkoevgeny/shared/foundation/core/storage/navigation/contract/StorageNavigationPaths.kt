package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.contract

/**
 * Storage-specific naming conventions for navigation route path and query parameters.
 */
public object StorageNavigationPaths {
    /**
     * Query parameter key for the maximum number of files permitted to be selected or uploaded in a single batch.
     */
    public const val MAX_FILES: String = "max_files"

    /**
     * Query parameter key for the list of accepted file extensions or MIME types.
     */
    public const val ACCEPTED_FILE_TYPES: String = "accepted_file_types"

    /**
     * Query parameter key for whether to automatically trigger upload submission upon file selection.
     */
    public const val AUTO_SUBMIT: String = "auto_submit"

    /**
     * Query parameter key for the maximum file size limit in bytes for a specific upload context.
     */
    public const val MAX_FILE_SIZE_BYTES: String = "max_file_size_bytes"
}
