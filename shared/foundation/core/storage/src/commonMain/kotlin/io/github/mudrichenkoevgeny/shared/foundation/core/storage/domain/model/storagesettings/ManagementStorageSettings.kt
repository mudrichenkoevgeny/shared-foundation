package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings

/**
 * Domain model for platform-wide storage settings accessible by management roles.
 *
 * @property minUploadSizeBytes The minimum permitted file size in bytes for a single upload.
 * @property maxUploadSizeBytes The default maximum permitted file size in bytes for a single upload across the entire platform.
 * @property totalStorageQuotaForOpenUserBytes Cumulative size limit in bytes for active files currently stored per open user, or `null` for unlimited.
 * @property totalStorageQuotaForManagementUserBytes Cumulative size limit in bytes for active files currently stored per management user, or `null` for unlimited.
 * @property presignedUrlExpirationSeconds The validity duration of a presigned upload or download URL in seconds.
 * @property maxPendingUploadsForOpenUser The maximum number of concurrent unfinalized upload sessions allowed for open users.
 * @property maxPendingUploadsForManagementUser The maximum number of concurrent unfinalized upload sessions allowed for management users.
 * @property isDeduplicationEnabled Toggle for enabling content-addressable file deduplication by SHA-256 hash.
 */
data class ManagementStorageSettings(
    val minUploadSizeBytes: Long,
    val maxUploadSizeBytes: Long,
    val totalStorageQuotaForOpenUserBytes: Long?,
    val totalStorageQuotaForManagementUserBytes: Long?,
    val presignedUrlExpirationSeconds: Int,
    val maxPendingUploadsForOpenUser: Int,
    val maxPendingUploadsForManagementUser: Int,
    val isDeduplicationEnabled: Boolean
)
