package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val STATUS_PENDING_UPLOAD = "pending_upload"
private const val STATUS_READY = "ready"
private const val STATUS_PROCESSING = "processing"
private const val STATUS_DELETED = "deleted"

/**
 * Lifecycle status of a storage file record in the system.
 *
 * @property value String representation of the [StorageFileStatus].
 */
@Serializable
enum class StorageFileStatus(val value: String) {
    /**
     * Presigned upload URL requested, pending binary transfer and confirmation.
     */
    @SerialName(STATUS_PENDING_UPLOAD)
    PENDING_UPLOAD(STATUS_PENDING_UPLOAD),

    /**
     * File binary transferred to S3 and confirmed; ready for consumption.
     */
    @SerialName(STATUS_READY)
    READY(STATUS_READY),

    /**
     * File is undergoing async post-processing (e.g. virus scanning, thumbnail generation, encoding).
     */
    @SerialName(STATUS_PROCESSING)
    PROCESSING(STATUS_PROCESSING),

    /**
     * File record and binary marked for removal or deleted.
     */
    @SerialName(STATUS_DELETED)
    DELETED(STATUS_DELETED);

    companion object {
        /**
         * Returns [StorageFileStatus] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): StorageFileStatus? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [StorageFileStatus] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [StorageFileStatus].
         */
        fun fromValueOrThrow(value: String): StorageFileStatus =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for StorageFileStatus: '$value'")
    }
}
