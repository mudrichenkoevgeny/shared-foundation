package io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.naming

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.naming.CommonErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.common.formatting.ByteSizeFormatter

/**
 * Storage-specific error codes for API responses and backend logging, used to categorize failures.
 */
object StorageErrorCodes {
    // --- Presigned Upload Validation ---

    /**
     * The requested file size exceeds the maximum permitted limit for its category or system policy.
     *
     * **Localization keys:**
     * - `file_size_exceeds_limit_without_args`: Static fallback message.
     * - `file_size_exceeds_limit_with_args`: Parameterized message template (`{formattedMaxFileSize}`).
     *
     * **Formatters & Resolvers:**
     * - Use [ByteSizeFormatter.format] to format [StorageErrorArgs.MAX_FILE_SIZE_BYTES] into `{formattedMaxFileSize}`.
     *
     * **Display arguments (Localization placeholders):**
     * - `formattedMaxFileSize`: Formatted maximum file size string (e.g., "50 MB").
     *
     * **Public arguments (Programmatic API payload):**
     * - [StorageErrorArgs.MAX_FILE_SIZE_BYTES]: Maximum file size permitted in bytes.
     * - [StorageErrorArgs.FILE_SIZE_BYTES] (optional): Actual size of the file provided by the caller in bytes.
     * - [StorageErrorArgs.CATEGORY] (optional): Storage category identifier.
     * - [StorageErrorArgs.FILE_NAME] (optional): Name of the file being uploaded.
     */
    const val FILE_SIZE_EXCEEDS_LIMIT = "FILE_SIZE_EXCEEDS_LIMIT"

    /**
     * The requested file size is below the minimum permitted threshold (e.g. empty 0-byte file).
     *
     * **Localization keys:**
     * - `file_size_below_minimum_without_args`: Static fallback message.
     * - `file_size_below_minimum_with_args`: Parameterized message template (`{formattedMinFileSize}`).
     *
     * **Formatters & Resolvers:**
     * - Use [ByteSizeFormatter.format] to format [StorageErrorArgs.MIN_FILE_SIZE_BYTES] into `{formattedMinFileSize}`.
     *
     * **Display arguments (Localization placeholders):**
     * - `formattedMinFileSize`: Formatted minimum file size string (e.g., "1 KB").
     *
     * **Public arguments (Programmatic API payload):**
     * - [StorageErrorArgs.MIN_FILE_SIZE_BYTES]: Minimum file size permitted in bytes.
     * - [StorageErrorArgs.FILE_SIZE_BYTES] (optional): Actual size of the file provided by the caller in bytes.
     * - [StorageErrorArgs.CATEGORY] (optional): Storage category identifier.
     * - [StorageErrorArgs.FILE_NAME] (optional): Name of the file being uploaded.
     */
    const val FILE_SIZE_BELOW_MINIMUM = "FILE_SIZE_BELOW_MINIMUM"

    /**
     * The provided MIME type is not supported or forbidden for the target storage category.
     *
     * **Localization keys:**
     * - `unsupported_mime_type`: Static message.
     *
     * **Public arguments (Programmatic API payload):**
     * - [StorageErrorArgs.MIME_TYPE]: The rejected MIME type.
     * - [StorageErrorArgs.CATEGORY] (optional): Storage category identifier.
     * - [StorageErrorArgs.FILE_NAME] (optional): Name of the file being uploaded.
     */
    const val UNSUPPORTED_MIME_TYPE = "UNSUPPORTED_MIME_TYPE"

    /**
     * The specified storage category is disabled, unknown, or not supported by the platform configuration.
     *
     * **Localization keys:**
     * - `unsupported_storage_category`
     *
     * **Public arguments (Programmatic API payload):**
     * - [StorageErrorArgs.CATEGORY]: Storage category identifier that was rejected.
     */
    const val UNSUPPORTED_STORAGE_CATEGORY = "UNSUPPORTED_STORAGE_CATEGORY"

    /**
     * The upload or storage operation failed because the user or tenant has exceeded their total allocated storage quota.
     *
     * **Localization keys:**
     * - `storage_quota_exceeded_without_args`: Static fallback message.
     * - `storage_quota_exceeded_with_args`: Parameterized message template (`{formattedUsed}`, `{formattedQuota}`).
     *
     * **Formatters & Resolvers:**
     * - Use [ByteSizeFormatter.format] to format [StorageErrorArgs.QUOTA_BYTES] and [StorageErrorArgs.USED_BYTES].
     *
     * **Display arguments (Localization placeholders):**
     * - `formattedQuota`: Formatted allocated storage quota string (e.g., "1 GB").
     * - `formattedUsed`: Formatted current storage space used string (e.g., "950 MB").
     *
     * **Public arguments (Programmatic API payload):**
     * - [StorageErrorArgs.QUOTA_BYTES]: Allocated storage quota in bytes.
     * - [StorageErrorArgs.USED_BYTES]: Current storage space used in bytes.
     */
    const val STORAGE_QUOTA_EXCEEDED = "STORAGE_QUOTA_EXCEEDED"

    /**
     * The caller has created too many unfinalized presigned upload sessions without completing or canceling them.
     *
     * **Localization keys:**
     * - `too_many_pending_uploads_without_args`: Static fallback message.
     * - `too_many_pending_uploads_with_args`: Parameterized message template (`{maxPendingUploads}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [StorageErrorArgs.MAX_PENDING_UPLOADS]: Maximum permitted active upload sessions (`{maxPendingUploads}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [StorageErrorArgs.MAX_PENDING_UPLOADS]: Maximum permitted active upload sessions.
     */
    const val TOO_MANY_PENDING_UPLOADS = "TOO_MANY_PENDING_UPLOADS"

    /**
     * The user attempted to select or upload more files in a single batch than permitted by system policy or component configuration.
     *
     * **Localization keys:**
     * - `max_batch_upload_count_exceeded_without_args`: Static fallback message.
     * - `max_batch_upload_count_exceeded_with_args`: Parameterized message template (`{maxBatchUploadCount}`).
     *
     * **Display arguments (Localization placeholders):**
     * - [StorageErrorArgs.MAX_BATCH_UPLOAD_COUNT]: Maximum permitted files in a single batch (`{maxBatchUploadCount}`).
     *
     * **Public arguments (Programmatic API payload):**
     * - [StorageErrorArgs.MAX_BATCH_UPLOAD_COUNT]: Maximum permitted files in a single batch.
     */
    const val MAX_BATCH_UPLOAD_COUNT_EXCEEDED = "MAX_BATCH_UPLOAD_COUNT_EXCEEDED"

    // --- Upload Session & Finalization ---

    /**
     * The presigned upload URL or session token has expired and can no longer be used.
     *
     * **Localization keys:**
     * - `presigned_url_expired`
     */
    const val PRESIGNED_URL_EXPIRED = "PRESIGNED_URL_EXPIRED"

    /**
     * The upload session token or signature is invalid, malformed, or tampered with.
     *
     * **Localization keys:**
     * - `bad_request`
     */
    const val INVALID_UPLOAD_TOKEN = "INVALID_UPLOAD_TOKEN"

    /**
     * The upload session or presigned URL has already been completed or finalized.
     *
     * **Localization keys:**
     * - `upload_already_completed`
     *
     * **Secret Arguments:**
     * - [StorageErrorArgs.FILE_ID]: The identifier of the file whose upload was already completed.
     */
    const val UPLOAD_ALREADY_COMPLETED = "UPLOAD_ALREADY_COMPLETED"

    /**
     * Verification or processing of an uploaded file failed after transfer (e.g. actual uploaded size mismatch, corrupted upload, or magic bytes check failure).
     *
     * **Localization keys:**
     * - `storage_upload_failed`
     *
     * **Secret Arguments:**
     * - [StorageErrorArgs.FILE_ID] (optional): The target file identifier.
     * - [CommonErrorArgs.MESSAGE] (optional): Internal verification error details.
     */
    const val STORAGE_UPLOAD_FAILED = "STORAGE_UPLOAD_FAILED"

    /**
     * Generic unexpected storage infrastructure or metadata error.
     *
     * **Localization keys:**
     * - `storage_error`
     *
     * **Secret Arguments:**
     * - [CommonErrorArgs.MESSAGE] (optional): Internal error details.
     */
    const val STORAGE_ERROR = "STORAGE_ERROR"

    // --- File State & Operations ---

    /**
     * The requested file record was not found in storage/metadata repository, OR the caller lacks permission to access it.
     *
     * *Note: For security reasons (prevention of resource enumeration attacks), servers MUST return [FILE_NOT_FOUND]
     * rather than an access denied error when a user attempts to access a file they do not own.*
     *
     * **Localization keys:**
     * - `file_not_found`
     *
     * **Secret Arguments:**
     * - [StorageErrorArgs.FILE_ID]: The file identifier that was requested.
     */
    const val FILE_NOT_FOUND = "FILE_NOT_FOUND"

    /**
     * The requested file is not yet available because it is still being uploaded, scanned, or processed asynchronously.
     *
     * **Localization keys:**
     * - `file_not_ready`
     *
     * **Secret Arguments:**
     * - [StorageErrorArgs.FILE_ID]: The target file identifier.
     */
    const val FILE_NOT_READY = "FILE_NOT_READY"

    /**
     * A file with the specified identifier or content hash already exists and cannot be overwritten.
     *
     * **Localization keys:**
     * - `file_already_exists`
     *
     * **Secret Arguments:**
     * - [StorageErrorArgs.FILE_ID]: The file identifier that already exists.
     */
    const val FILE_ALREADY_EXISTS = "FILE_ALREADY_EXISTS"

    /**
     * Failed to remove the file binary from object storage or database metadata.
     *
     * **Localization keys:**
     * - `file_delete_failed`
     */
    const val FILE_DELETE_FAILED = "FILE_DELETE_FAILED"
}
