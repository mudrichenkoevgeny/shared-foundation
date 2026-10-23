package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditMetadataKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val KEY_FILE_NAME = "file_name"
private const val KEY_FILE_SIZE_BYTES = "file_size_bytes"
private const val KEY_CATEGORY = "category"
private const val KEY_S3_KEY = "s3_key"

/**
 * Storage-module audit metadata keys for [AuditEventMetadata].
 *
 * @property value String representation of the [StorageAuditMetadataKey].
 * @property valueSensitivity Sensitivity level for audit logging.
 */
@Serializable
enum class StorageAuditMetadataKey(
    override val value: String,
    override val valueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE
) : AuditMetadataKey {

    /** Original file name provided at upload. */
    @SerialName(KEY_FILE_NAME)
    FILE_NAME(KEY_FILE_NAME),

    /** Binary file size in bytes. */
    @SerialName(KEY_FILE_SIZE_BYTES)
    FILE_SIZE_BYTES(KEY_FILE_SIZE_BYTES),

    /** Storage classification category. */
    @SerialName(KEY_CATEGORY)
    CATEGORY(KEY_CATEGORY),

    /** Internal S3 object key or storage path. */
    @SerialName(KEY_S3_KEY)
    S3_KEY(KEY_S3_KEY);

    companion object {
        /**
         * Returns [StorageAuditMetadataKey] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): StorageAuditMetadataKey? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [StorageAuditMetadataKey] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [StorageAuditMetadataKey].
         */
        fun fromValueOrThrow(value: String): StorageAuditMetadataKey =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for StorageAuditMetadataKey: '$value'")
    }
}
