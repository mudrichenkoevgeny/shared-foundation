package io.github.mudrichenkoevgeny.shared.foundation.core.storage.error.client.naming

/**
 * Machine-readable client-side storage error code string constants for direct presigned S3 upload failures and cancellations.
 *
 * Raised by client applications or SDK storage upload managers.
 * **NOT returned by backend API responses.**
 */
object ClientStorageErrorCodes {

    /**
     * Direct S3 / Object Storage upload failed due to network error, CORS issue, or S3 error response.
     */
    const val S3_UPLOAD_FAILED = "S3_UPLOAD_FAILED"

    /**
     * Direct S3 / Object Storage upload was cancelled by the user or client application.
     */
    const val DIRECT_UPLOAD_CANCELLED = "DIRECT_UPLOAD_CANCELLED"
}
