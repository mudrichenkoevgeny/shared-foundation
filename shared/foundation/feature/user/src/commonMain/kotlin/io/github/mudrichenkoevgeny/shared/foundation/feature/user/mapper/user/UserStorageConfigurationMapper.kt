package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserStorageConfiguration
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserStorageConfigurationPayload

/**
 * Extension functions mapping user storage configuration between domain and network representations.
 */

/**
 * Builds domain [UserStorageConfiguration] from [UserStorageConfigurationPayload].
 */
fun UserStorageConfigurationPayload.toUserStorageConfiguration(): UserStorageConfiguration = UserStorageConfiguration(
    quotaBytes = quotaBytes,
    maxUploadSizeBytes = maxUploadSizeBytes,
    maxPendingUploads = maxPendingUploads
)

/**
 * Builds network [UserStorageConfigurationPayload] from domain [UserStorageConfiguration].
 */
fun UserStorageConfiguration.toUserStorageConfigurationPayload(): UserStorageConfigurationPayload = UserStorageConfigurationPayload(
    quotaBytes = quotaBytes,
    maxUploadSizeBytes = maxUploadSizeBytes,
    maxPendingUploads = maxPendingUploads
)
