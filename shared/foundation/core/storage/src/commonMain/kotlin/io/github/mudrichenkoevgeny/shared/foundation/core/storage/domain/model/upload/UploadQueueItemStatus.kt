package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val STATUS_IDLE = "idle"
private const val STATUS_UPLOADING = "uploading"
private const val STATUS_COMPLETED = "completed"
private const val STATUS_FAILED = "failed"

/**
 * Execution lifecycle states for client file upload queue items.
 *
 * @property value String representation of the [UploadQueueItemStatus].
 */
@Serializable
enum class UploadQueueItemStatus(val value: String) {
    /**
     * Item is waiting in the queue to be uploaded.
     */
    @SerialName(STATUS_IDLE)
    IDLE(STATUS_IDLE),

    /**
     * Item is actively transferring bytes to S3 or confirming metadata.
     */
    @SerialName(STATUS_UPLOADING)
    UPLOADING(STATUS_UPLOADING),

    /**
     * Upload and confirmation succeeded; file record is created and ready.
     */
    @SerialName(STATUS_COMPLETED)
    COMPLETED(STATUS_COMPLETED),

    /**
     * Upload failed due to validation, network, or server error.
     */
    @SerialName(STATUS_FAILED)
    FAILED(STATUS_FAILED);

    companion object {
        /**
         * Returns [UploadQueueItemStatus] for the given string value, or null if unmapped.
         */
        fun fromValueOrNull(value: String): UploadQueueItemStatus? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [UploadQueueItemStatus] for the given string value, or throws [IllegalArgumentException].
         */
        fun fromValueOrThrow(value: String): UploadQueueItemStatus =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown UploadQueueItemStatus: '$value'")
    }
}
