package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.ui.component.fileupload

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.ui.component.fileupload.StorageFileUploadUiConfig

/**
 * Creates a test [StorageFileUploadUiConfig] instance.
 */
fun createTestStorageFileUploadUiConfig(
    maxFiles: Int? = 5,
    targetScope: String? = "avatar",
    category: StorageCategory? = StorageCategory.IMAGE,
    acceptedFileTypes: List<String> = listOf(".jpg", ".png", "image/*"),
    maxFileSizeBytes: Long? = 2097152L,
    autoSubmit: Boolean = false
) = StorageFileUploadUiConfig(
    maxFiles = maxFiles,
    targetScope = targetScope,
    category = category,
    acceptedFileTypes = acceptedFileTypes,
    maxFileSizeBytes = maxFileSizeBytes,
    autoSubmit = autoSubmit
)
