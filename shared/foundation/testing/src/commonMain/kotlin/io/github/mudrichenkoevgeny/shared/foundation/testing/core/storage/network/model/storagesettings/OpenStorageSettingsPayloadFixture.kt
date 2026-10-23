package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.network.model.storagesettings

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload

/**
 * Creates a test [OpenStorageSettingsPayload] wire object instance.
 */
fun createTestOpenStorageSettingsPayload(
    minUploadSizeBytes: Long = 1L,
    maxUploadSizeBytes: Long = 52428800L
) = OpenStorageSettingsPayload(
    minUploadSizeBytes = minUploadSizeBytes,
    maxUploadSizeBytes = maxUploadSizeBytes
)
