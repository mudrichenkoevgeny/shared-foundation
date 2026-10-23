package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.domain.model.storagesettings

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.OpenStorageSettings

/**
 * Creates a test [OpenStorageSettings] domain model instance.
 */
fun createTestOpenStorageSettings(
    minUploadSizeBytes: Long = 1L,
    maxUploadSizeBytes: Long = 52428800L
) = OpenStorageSettings(
    minUploadSizeBytes = minUploadSizeBytes,
    maxUploadSizeBytes = maxUploadSizeBytes
)
