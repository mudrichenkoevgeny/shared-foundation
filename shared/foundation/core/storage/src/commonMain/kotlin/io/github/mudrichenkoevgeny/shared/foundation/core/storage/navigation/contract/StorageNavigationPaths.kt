package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.contract

/**
 * Storage-specific naming conventions for navigation route path and query parameters.
 */
object StorageNavigationPaths {

    /**
     * Query parameter key for the maximum number of files permitted to be selected or uploaded in a single batch.
     */
    const val MAX_FILES = "max_files"

    /**
     * Query parameter key for the list of accepted file extensions or MIME types.
     */
    const val ACCEPTED_FILE_TYPES = "accepted_file_types"

    /**
     * Query parameter key for whether to automatically trigger upload submission upon file selection.
     */
    const val AUTO_SUBMIT = "auto_submit"

    /**
     * Query parameter key for the maximum file size limit in bytes for a specific upload context.
     */
    const val MAX_FILE_SIZE_BYTES = "max_file_size_bytes"
}
