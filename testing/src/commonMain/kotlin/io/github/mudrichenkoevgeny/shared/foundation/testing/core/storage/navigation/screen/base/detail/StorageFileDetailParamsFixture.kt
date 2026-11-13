package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.navigation.screen.base.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.detail.StorageFileDetailParams

/**
 * Creates a test [StorageFileDetailParams] instance.
 */
public fun createTestStorageFileDetailParams(
    fileId: String = "00000000-0000-4000-8000-000000000001"
): StorageFileDetailParams = StorageFileDetailParams(
    fileId = fileId
)
