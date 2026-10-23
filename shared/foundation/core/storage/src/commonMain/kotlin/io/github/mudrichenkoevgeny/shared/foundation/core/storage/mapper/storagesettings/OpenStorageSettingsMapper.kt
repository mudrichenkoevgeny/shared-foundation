package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.storagesettings

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.OpenStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload

/**
 * Extension functions mapping global storage settings between domain and network representations for open APIs.
 */

/**
 * Builds domain [OpenStorageSettings] from [OpenStorageSettingsPayload].
 */
fun OpenStorageSettingsPayload.toOpenStorageSettings(): OpenStorageSettings = OpenStorageSettings(
    minUploadSizeBytes = minUploadSizeBytes,
    maxUploadSizeBytes = maxUploadSizeBytes
)

/**
 * Builds network [OpenStorageSettingsPayload] from domain [OpenStorageSettings].
 */
fun OpenStorageSettings.toOpenStorageSettingsPayload(): OpenStorageSettingsPayload = OpenStorageSettingsPayload(
    minUploadSizeBytes = minUploadSizeBytes,
    maxUploadSizeBytes = maxUploadSizeBytes
)
