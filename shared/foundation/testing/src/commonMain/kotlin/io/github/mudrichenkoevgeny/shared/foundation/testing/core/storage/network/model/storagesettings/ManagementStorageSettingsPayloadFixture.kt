package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.network.model.storagesettings

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.ManagementStorageSettingsPayload

/**
 * Creates a test [ManagementStorageSettingsPayload] wire object instance.
 */
fun createTestManagementStorageSettingsPayload(
    minUploadSizeBytes: Long = 1L,
    maxUploadSizeBytes: Long = 52428800L,
    totalStorageQuotaForOpenUserBytes: Long? = 1073741824L,
    totalStorageQuotaForManagementUserBytes: Long? = null,
    presignedUrlExpirationSeconds: Int = 900,
    maxPendingUploadsForOpenUser: Int = 10,
    maxPendingUploadsForManagementUser: Int = 100,
    isDeduplicationEnabled: Boolean = true
) = ManagementStorageSettingsPayload(
    minUploadSizeBytes = minUploadSizeBytes,
    maxUploadSizeBytes = maxUploadSizeBytes,
    totalStorageQuotaForOpenUserBytes = totalStorageQuotaForOpenUserBytes,
    totalStorageQuotaForManagementUserBytes = totalStorageQuotaForManagementUserBytes,
    presignedUrlExpirationSeconds = presignedUrlExpirationSeconds,
    maxPendingUploadsForOpenUser = maxPendingUploadsForOpenUser,
    maxPendingUploadsForManagementUser = maxPendingUploadsForManagementUser,
    isDeduplicationEnabled = isDeduplicationEnabled
)
