package io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.naming

/**
 * Storage-specific keys for error arguments used in API responses and backend logging.
 */
object StorageErrorArgs {
    /**
     * Key for the unique identifier of the target storage file.
     * Used as a **secret argument** for backend logging and auditing, or **public argument** when referencing missing files.
     *
     * Associated with [StorageErrorCodes.FILE_NOT_FOUND], [StorageErrorCodes.FILE_NOT_READY],
     * [StorageErrorCodes.FILE_DELETE_FAILED], [StorageErrorCodes.FILE_ALREADY_EXISTS],
     * [StorageErrorCodes.UPLOAD_ALREADY_COMPLETED].
     */
    const val FILE_ID = "fileId"

    /**
     * Key for the original or requested file name string.
     * Used as a **public argument** or **secret argument** in upload validation context.
     *
     * Associated with [StorageErrorCodes.FILE_SIZE_EXCEEDS_LIMIT], [StorageErrorCodes.FILE_SIZE_BELOW_MINIMUM],
     * [StorageErrorCodes.UNSUPPORTED_MIME_TYPE].
     */
    const val FILE_NAME = "fileName"

    /**
     * Key for the actual file size in bytes.
     * Used as a **public argument** for client-side validation error messages.
     *
     * Associated with [StorageErrorCodes.FILE_SIZE_EXCEEDS_LIMIT], [StorageErrorCodes.FILE_SIZE_BELOW_MINIMUM].
     */
    const val FILE_SIZE_BYTES = "fileSizeBytes"

    /**
     * Key for the minimum permitted file size in bytes for the requested operation or category.
     * Used as a **public argument** for client-side validation error messages.
     *
     * Associated with [StorageErrorCodes.FILE_SIZE_BELOW_MINIMUM].
     */
    const val MIN_FILE_SIZE_BYTES = "minFileSizeBytes"

    /**
     * Key for the maximum permitted file size in bytes for the requested operation or category.
     * Used as a **public argument** for client-side validation error messages.
     *
     * Associated with [StorageErrorCodes.FILE_SIZE_EXCEEDS_LIMIT].
     */
    const val MAX_FILE_SIZE_BYTES = "maxFileSizeBytes"

    /**
     * Key for the requested or actual MIME type string (e.g. `image/png`, `application/pdf`).
     * Used as a **public argument** for client-side validation error messages.
     *
     * Associated with [StorageErrorCodes.UNSUPPORTED_MIME_TYPE].
     */
    const val MIME_TYPE = "mimeType"

    /**
     * Key for the storage category code.
     * Used as a **public argument** for client-side localization and error handling.
     *
     * Associated with [StorageErrorCodes.FILE_SIZE_EXCEEDS_LIMIT], [StorageErrorCodes.FILE_SIZE_BELOW_MINIMUM],
     * [StorageErrorCodes.UNSUPPORTED_MIME_TYPE], [StorageErrorCodes.UNSUPPORTED_STORAGE_CATEGORY].
     */
    const val CATEGORY = "category"

    /**
     * Key for the total storage quota limit in bytes allocated to the user or organization.
     * Used as a **public argument** for client-side localization and UI notifications.
     *
     * Associated with [StorageErrorCodes.STORAGE_QUOTA_EXCEEDED].
     */
    const val QUOTA_BYTES = "quotaBytes"

    /**
     * Key for the currently used storage space in bytes.
     * Used as a **public argument** for client-side localization and UI notifications.
     *
     * Associated with [StorageErrorCodes.STORAGE_QUOTA_EXCEEDED].
     */
    const val USED_BYTES = "usedBytes"

    /**
     * Key for the maximum allowed uncompleted presigned upload sessions per user.
     * Used as a **public argument** for client-side throttling error messages.
     *
     * Associated with [StorageErrorCodes.TOO_MANY_PENDING_UPLOADS].
     */
    const val MAX_PENDING_UPLOADS = "maxPendingUploads"

    /**
     * Key for the maximum permitted file count in a single batch upload selection.
     * Used as a **public argument** for client-side upload validation error messages.
     *
     * Associated with [StorageErrorCodes.MAX_BATCH_UPLOAD_COUNT_EXCEEDED].
     */
    const val MAX_BATCH_UPLOAD_COUNT = "maxBatchUploadCount"
}
