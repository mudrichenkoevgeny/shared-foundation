package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.storagesettings

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.ManagementStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.ManagementStorageSettingsPayload

/**
 * Extension functions mapping global storage settings between domain and network representations for management roles.
 */

/**
 * Builds domain [ManagementStorageSettings] from [ManagementStorageSettingsPayload].
 */
fun ManagementStorageSettingsPayload.toManagementStorageSettings(): ManagementStorageSettings = ManagementStorageSettings(
    minUploadSizeBytes = minUploadSizeBytes,
    maxUploadSizeBytes = maxUploadSizeBytes,
    totalStorageQuotaForOpenUserBytes = totalStorageQuotaForOpenUserBytes,
    totalStorageQuotaForManagementUserBytes = totalStorageQuotaForManagementUserBytes,
    presignedUrlExpirationSeconds = presignedUrlExpirationSeconds,
    maxPendingUploadsForOpenUser = maxPendingUploadsForOpenUser,
    maxPendingUploadsForManagementUser = maxPendingUploadsForManagementUser,
    isDeduplicationEnabled = isDeduplicationEnabled
)

/**
 * Builds network [ManagementStorageSettingsPayload] from domain [ManagementStorageSettings].
 */
fun ManagementStorageSettings.toManagementStorageSettingsPayload(): ManagementStorageSettingsPayload = ManagementStorageSettingsPayload(
    minUploadSizeBytes = minUploadSizeBytes,
    maxUploadSizeBytes = maxUploadSizeBytes,
    totalStorageQuotaForOpenUserBytes = totalStorageQuotaForOpenUserBytes,
    totalStorageQuotaForManagementUserBytes = totalStorageQuotaForManagementUserBytes,
    presignedUrlExpirationSeconds = presignedUrlExpirationSeconds,
    maxPendingUploadsForOpenUser = maxPendingUploadsForOpenUser,
    maxPendingUploadsForManagementUser = maxPendingUploadsForManagementUser,
    isDeduplicationEnabled = isDeduplicationEnabled
)
