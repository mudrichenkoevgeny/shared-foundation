package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.domain.model.storagesettings

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.ManagementStorageSettings

/**
 * Creates a test [ManagementStorageSettings] domain model instance.
 */
fun createTestManagementStorageSettings(
    minUploadSizeBytes: Long = 1L,
    maxUploadSizeBytes: Long = 52428800L,
    totalStorageQuotaForOpenUserBytes: Long? = 1073741824L,
    totalStorageQuotaForManagementUserBytes: Long? = null,
    presignedUrlExpirationSeconds: Int = 900,
    maxPendingUploadsForOpenUser: Int = 10,
    maxPendingUploadsForManagementUser: Int = 100,
    isDeduplicationEnabled: Boolean = true
) = ManagementStorageSettings(
    minUploadSizeBytes = minUploadSizeBytes,
    maxUploadSizeBytes = maxUploadSizeBytes,
    totalStorageQuotaForOpenUserBytes = totalStorageQuotaForOpenUserBytes,
    totalStorageQuotaForManagementUserBytes = totalStorageQuotaForManagementUserBytes,
    presignedUrlExpirationSeconds = presignedUrlExpirationSeconds,
    maxPendingUploadsForOpenUser = maxPendingUploadsForOpenUser,
    maxPendingUploadsForManagementUser = maxPendingUploadsForManagementUser,
    isDeduplicationEnabled = isDeduplicationEnabled
)
