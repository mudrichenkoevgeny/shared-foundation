package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.storagesettings

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.ManagementStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.ManagementStorageSettingsPayload

/**
 * Builds domain [ManagementStorageSettings] from [ManagementStorageSettingsPayload].
 */
public fun ManagementStorageSettingsPayload.toManagementStorageSettings(): ManagementStorageSettings =
    ManagementStorageSettings(
        minUploadSizeBytes = minUploadSizeBytes,
        maxUploadSizeBytes = maxUploadSizeBytes,
        totalStorageQuotaForOpenUserBytes = totalStorageQuotaForOpenUserBytes,
        totalStorageQuotaForManagementUserBytes = totalStorageQuotaForManagementUserBytes,
        presignedUrlExpirationSeconds = presignedUrlExpirationSeconds,
        maxPendingUploadsForOpenUser = maxPendingUploadsForOpenUser,
        maxPendingUploadsForManagementUser = maxPendingUploadsForManagementUser,
        isDeduplicationEnabled = isDeduplicationEnabled,
        deletedFilesCleanupIntervalSeconds = deletedFilesCleanupIntervalSeconds
    )

/**
 * Builds network [ManagementStorageSettingsPayload] from domain [ManagementStorageSettings].
 */
public fun ManagementStorageSettings.toManagementStorageSettingsPayload(): ManagementStorageSettingsPayload =
    ManagementStorageSettingsPayload(
        minUploadSizeBytes = minUploadSizeBytes,
        maxUploadSizeBytes = maxUploadSizeBytes,
        totalStorageQuotaForOpenUserBytes = totalStorageQuotaForOpenUserBytes,
        totalStorageQuotaForManagementUserBytes = totalStorageQuotaForManagementUserBytes,
        presignedUrlExpirationSeconds = presignedUrlExpirationSeconds,
        maxPendingUploadsForOpenUser = maxPendingUploadsForOpenUser,
        maxPendingUploadsForManagementUser = maxPendingUploadsForManagementUser,
        isDeduplicationEnabled = isDeduplicationEnabled,
        deletedFilesCleanupIntervalSeconds = deletedFilesCleanupIntervalSeconds
    )
