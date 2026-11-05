package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.storagesettings

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.OpenStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload

/**
 * Builds domain [OpenStorageSettings] from [OpenStorageSettingsPayload].
 */
public fun OpenStorageSettingsPayload.toOpenStorageSettings(): OpenStorageSettings =
    OpenStorageSettings(
        minUploadSizeBytes = minUploadSizeBytes,
        maxUploadSizeBytes = maxUploadSizeBytes
    )

/**
 * Builds network [OpenStorageSettingsPayload] from domain [OpenStorageSettings].
 */
public fun OpenStorageSettings.toOpenStorageSettingsPayload(): OpenStorageSettingsPayload =
    OpenStorageSettingsPayload(
        minUploadSizeBytes = minUploadSizeBytes,
        maxUploadSizeBytes = maxUploadSizeBytes
    )
