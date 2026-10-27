package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.navigation.screen.base.upload

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.upload.StorageFileUploadParams

/**
 * Creates a test [StorageFileUploadParams] instance.
 */
fun createTestStorageFileUploadParams(
    maxFiles: Int? = 5,
    targetScope: String? = "avatar",
    category: StorageCategory? = StorageCategory.IMAGE,
    acceptedFileTypes: List<String> = listOf(".jpg", ".png", "image/*"),
    maxFileSizeBytes: Long? = 2097152L,
    autoSubmit: Boolean = false
) = StorageFileUploadParams(
    maxFiles = maxFiles,
    targetScope = targetScope,
    category = category,
    acceptedFileTypes = acceptedFileTypes,
    maxFileSizeBytes = maxFileSizeBytes,
    autoSubmit = autoSubmit
)
