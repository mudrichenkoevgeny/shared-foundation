package io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.naming

/**
 * Storage-specific keys for error arguments used in API responses and backend logging.
 */
public object StorageErrorArgs {
    /**
     * Key for the unique identifier of the target storage file.
     * Used as a **secret argument** for backend logging and auditing, or **public argument** when referencing missing files.
     *
     * Associated with [StorageErrorCodes.FILE_NOT_FOUND], [StorageErrorCodes.FILE_NOT_READY],
     * [StorageErrorCodes.FILE_DELETE_FAILED], [StorageErrorCodes.FILE_ALREADY_EXISTS],
     * [StorageErrorCodes.UPLOAD_ALREADY_COMPLETED].
     */
    public const val FILE_ID: String = "fileId"

    /**
     * Key for the original or requested file name string.
     * Used as a **public argument** or **secret argument** in upload validation context.
     *
     * Associated with [StorageErrorCodes.FILE_SIZE_EXCEEDS_LIMIT], [StorageErrorCodes.FILE_SIZE_BELOW_MINIMUM],
     * [StorageErrorCodes.UNSUPPORTED_MIME_TYPE].
     */
    public const val FILE_NAME: String = "fileName"

    /**
     * Key for the actual file size in bytes.
     * Used as a **public argument** for client-side validation error messages.
     *
     * Associated with [StorageErrorCodes.FILE_SIZE_EXCEEDS_LIMIT], [StorageErrorCodes.FILE_SIZE_BELOW_MINIMUM].
     */
    public const val FILE_SIZE_BYTES: String = "fileSizeBytes"

    /**
     * Key for the minimum permitted file size in bytes for the requested operation or category.
     * Used as a **public argument** for client-side validation error messages.
     *
     * Associated with [StorageErrorCodes.FILE_SIZE_BELOW_MINIMUM].
     */
    public const val MIN_FILE_SIZE_BYTES: String = "minFileSizeBytes"

    /**
     * Key for the maximum permitted file size in bytes for the requested operation or category.
     * Used as a **public argument** for client-side validation error messages.
     *
     * Associated with [StorageErrorCodes.FILE_SIZE_EXCEEDS_LIMIT].
     */
    public const val MAX_FILE_SIZE_BYTES: String = "maxFileSizeBytes"

    /**
     * Key for the requested or actual MIME type string (e.g. `image/png`, `application/pdf`).
     * Used as a **public argument** for client-side validation error messages.
     *
     * Associated with [StorageErrorCodes.UNSUPPORTED_MIME_TYPE].
     */
    public const val MIME_TYPE: String = "mimeType"

    /**
     * Key for the storage category code.
     * Used as a **public argument** for client-side localization and error handling.
     *
     * Associated with [StorageErrorCodes.FILE_SIZE_EXCEEDS_LIMIT], [StorageErrorCodes.FILE_SIZE_BELOW_MINIMUM],
     * [StorageErrorCodes.UNSUPPORTED_MIME_TYPE], [StorageErrorCodes.UNSUPPORTED_STORAGE_CATEGORY].
     */
    public const val CATEGORY: String = "category"

    /**
     * Key for the total storage quota limit in bytes allocated to the user or organization.
     * Used as a **public argument** for client-side localization and UI notifications.
     *
     * Associated with [StorageErrorCodes.STORAGE_QUOTA_EXCEEDED].
     */
    public const val QUOTA_BYTES: String = "quotaBytes"

    /**
     * Key for the currently used storage space in bytes.
     * Used as a **public argument** for client-side localization and UI notifications.
     *
     * Associated with [StorageErrorCodes.STORAGE_QUOTA_EXCEEDED].
     */
    public const val USED_BYTES: String = "usedBytes"

    /**
     * Key for the maximum allowed uncompleted presigned upload sessions per user.
     * Used as a **public argument** for client-side throttling error messages.
     *
     * Associated with [StorageErrorCodes.TOO_MANY_PENDING_UPLOADS].
     */
    public const val MAX_PENDING_UPLOADS: String = "maxPendingUploads"

    /**
     * Key for the maximum permitted file count in a single batch upload selection.
     * Used as a **public argument** for client-side upload validation error messages.
     *
     * Associated with [StorageErrorCodes.MAX_BATCH_UPLOAD_COUNT_EXCEEDED].
     */
    public const val MAX_BATCH_UPLOAD_COUNT: String = "maxBatchUploadCount"
}
