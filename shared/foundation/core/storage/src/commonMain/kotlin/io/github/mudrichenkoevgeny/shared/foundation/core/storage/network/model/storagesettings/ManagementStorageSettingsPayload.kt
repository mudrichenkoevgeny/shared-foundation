package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire payload representing platform-wide storage settings for management roles.
 *
 * @property minUploadSizeBytes The minimum permitted file size in bytes for a single upload.
 * @property maxUploadSizeBytes The default maximum permitted file size in bytes for a single upload across the entire platform.
 * @property totalStorageQuotaForOpenUserBytes Cumulative size limit in bytes for active files currently stored per open user, or `null` for unlimited.
 * @property totalStorageQuotaForManagementUserBytes Cumulative size limit in bytes for active files currently stored per management user, or `null` for unlimited.
 * @property presignedUrlExpirationSeconds The validity duration of a presigned upload or download URL in seconds.
 * @property maxPendingUploadsForOpenUser The maximum number of concurrent unfinalized upload sessions allowed for open users.
 * @property maxPendingUploadsForManagementUser The maximum number of concurrent unfinalized upload sessions allowed for management users.
 * @property isDeduplicationEnabled Toggle for enabling content-addressable file deduplication by SHA-256 hash.
 * @property deletedFilesCleanupIntervalSeconds The background worker execution interval in seconds for permanently deleting marked files from S3 and database records.
 */
@Serializable
public data class ManagementStorageSettingsPayload(
    @SerialName(StorageApiFields.MIN_UPLOAD_SIZE_BYTES)
    public val minUploadSizeBytes: Long,
    @SerialName(StorageApiFields.MAX_UPLOAD_SIZE_BYTES)
    public val maxUploadSizeBytes: Long,
    @SerialName(StorageApiFields.TOTAL_STORAGE_QUOTA_FOR_OPEN_USER_BYTES)
    public val totalStorageQuotaForOpenUserBytes: Long?,
    @SerialName(StorageApiFields.TOTAL_STORAGE_QUOTA_FOR_MANAGEMENT_USER_BYTES)
    public val totalStorageQuotaForManagementUserBytes: Long?,
    @SerialName(StorageApiFields.PRESIGNED_URL_EXPIRATION_SECONDS)
    public val presignedUrlExpirationSeconds: Int,
    @SerialName(StorageApiFields.MAX_PENDING_UPLOADS_FOR_OPEN_USER)
    public val maxPendingUploadsForOpenUser: Int,
    @SerialName(StorageApiFields.MAX_PENDING_UPLOADS_FOR_MANAGEMENT_USER)
    public val maxPendingUploadsForManagementUser: Int,
    @SerialName(StorageApiFields.IS_DEDUPLICATION_ENABLED)
    public val isDeduplicationEnabled: Boolean,
    @SerialName(StorageApiFields.DELETED_FILES_CLEANUP_INTERVAL_SECONDS)
    public val deletedFilesCleanupIntervalSeconds: Int
)
