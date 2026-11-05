package io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.client.naming

/**
 * Machine-readable client-side storage error code string constants for direct presigned S3 upload failures and cancellations.
 *
 * Raised by client applications or SDK storage upload managers.
 * **NOT returned by backend API responses.**
 */
public object ClientStorageErrorCodes {
    /**
     * Direct S3 / Object Storage upload failed due to network error, CORS issue, or S3 error response.
     *
     * **Localization keys:**
     * - `storage_upload_failed`
     */
    public const val S3_UPLOAD_FAILED: String = "S3_UPLOAD_FAILED"

    /**
     * Direct S3 / Object Storage upload was cancelled by the user or client application.
     *
     * **Localization keys:**
     * - `upload_cancelled`
     */
    public const val DIRECT_UPLOAD_CANCELLED: String = "DIRECT_UPLOAD_CANCELLED"
}
