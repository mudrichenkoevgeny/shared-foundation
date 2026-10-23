package io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs

/**
 * Storage-specific error codes for API responses and backend logging, used to categorize failures.
 */
object StorageErrorCodes {
    // --- Presigned Upload Validation ---

    /**
     * The requested file size exceeds the maximum permitted limit for its category or system policy.
     *
     * **Public arguments:**
     * - [StorageErrorArgs.FILE_SIZE_BYTES]: The size of the file provided by the caller in bytes.
     * - [StorageErrorArgs.MAX_FILE_SIZE_BYTES]: Maximum file size permitted in bytes.
     * - [StorageErrorArgs.CATEGORY] (optional): Storage category identifier.
     * - [StorageErrorArgs.FILE_NAME] (optional): Name of the file being uploaded.
     */
    const val FILE_SIZE_EXCEEDS_LIMIT = "FILE_SIZE_EXCEEDS_LIMIT"

    /**
     * The requested file size is below the minimum permitted threshold (e.g. empty 0-byte file).
     *
     * **Public arguments:**
     * - [StorageErrorArgs.FILE_SIZE_BYTES]: The size of the file provided by the caller in bytes.
     * - [StorageErrorArgs.MIN_FILE_SIZE_BYTES]: Minimum file size permitted in bytes.
     * - [StorageErrorArgs.CATEGORY] (optional): Storage category identifier.
     * - [StorageErrorArgs.FILE_NAME] (optional): Name of the file being uploaded.
     */
    const val FILE_SIZE_BELOW_MINIMUM = "FILE_SIZE_BELOW_MINIMUM"

    /**
     * The provided MIME type is not supported or forbidden for the target storage category.
     *
     * **Public arguments:**
     * - [StorageErrorArgs.MIME_TYPE]: The rejected MIME type.
     * - [StorageErrorArgs.CATEGORY] (optional): Storage category identifier.
     * - [StorageErrorArgs.FILE_NAME] (optional): Name of the file being uploaded.
     */
    const val UNSUPPORTED_MIME_TYPE = "UNSUPPORTED_MIME_TYPE"

    /**
     * The specified storage category is disabled, unknown, or not supported by the platform configuration.
     *
     * **Public arguments:**
     * - [StorageErrorArgs.CATEGORY]: Storage category identifier that was rejected.
     */
    const val UNSUPPORTED_STORAGE_CATEGORY = "UNSUPPORTED_STORAGE_CATEGORY"

    /**
     * The upload or storage operation failed because the user or tenant has exceeded their total allocated storage quota.
     *
     * **Public arguments:**
     * - [StorageErrorArgs.QUOTA_BYTES]: Allocated storage quota in bytes.
     * - [StorageErrorArgs.USED_BYTES]: Current storage space used in bytes.
     */
    const val STORAGE_QUOTA_EXCEEDED = "STORAGE_QUOTA_EXCEEDED"

    /**
     * The caller has created too many unfinalized presigned upload sessions without completing or canceling them.
     *
     * **Public arguments:**
     * - [StorageErrorArgs.MAX_PENDING_UPLOADS]: Maximum permitted active upload sessions.
     */
    const val TOO_MANY_PENDING_UPLOADS = "TOO_MANY_PENDING_UPLOADS"

    // --- Upload Session & Finalization ---

    /**
     * The presigned upload URL or session token has expired and can no longer be used.
     */
    const val PRESIGNED_URL_EXPIRED = "PRESIGNED_URL_EXPIRED"

    /**
     * The upload session token or signature is invalid, malformed, or tampered with.
     */
    const val INVALID_UPLOAD_TOKEN = "INVALID_UPLOAD_TOKEN"

    /**
     * The upload session or presigned URL has already been completed or finalized.
     *
     * **Secret arguments:**
     * - [StorageErrorArgs.FILE_ID]: The identifier of the file whose upload was already completed.
     */
    const val UPLOAD_ALREADY_COMPLETED = "UPLOAD_ALREADY_COMPLETED"

    /**
     * Verification or processing of an uploaded file failed after transfer (e.g. actual uploaded size mismatch, corrupted upload, or magic bytes check failure).
     *
     * **Secret arguments:**
     * - [StorageErrorArgs.FILE_ID] (optional): The target file identifier.
     * - [CommonErrorArgs.MESSAGE] (optional): Internal verification error details.
     */
    const val STORAGE_UPLOAD_FAILED = "STORAGE_UPLOAD_FAILED"

    // --- File State & Operations ---

    /**
     * The requested file record was not found in storage/metadata repository, OR the caller lacks permission to access it.
     *
     * *Note: For security reasons (prevention of resource enumeration attacks), servers MUST return [FILE_NOT_FOUND]
     * rather than an access denied error when a user attempts to access a file they do not own.*
     *
     * **Secret arguments:**
     * - [StorageErrorArgs.FILE_ID]: The file identifier that was requested.
     */
    const val FILE_NOT_FOUND = "FILE_NOT_FOUND"

    /**
     * The requested file is not yet available because it is still being uploaded, scanned, or processed asynchronously.
     *
     * **Secret arguments:**
     * - [StorageErrorArgs.FILE_ID]: The target file identifier.
     */
    const val FILE_NOT_READY = "FILE_NOT_READY"

    /**
     * A file with the specified identifier or content hash already exists and cannot be overwritten.
     *
     * **Secret arguments:**
     * - [StorageErrorArgs.FILE_ID]: The file identifier that already exists.
     */
    const val FILE_ALREADY_EXISTS = "FILE_ALREADY_EXISTS"

    /**
     * Failed to remove the file binary from object storage or database metadata.
     *
     * **Secret arguments:**
     * - [StorageErrorArgs.FILE_ID]: The identifier of the file that could not be deleted.
     */
    const val FILE_DELETE_FAILED = "FILE_DELETE_FAILED"
}
