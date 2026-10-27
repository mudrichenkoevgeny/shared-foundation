package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.ui.component.fileupload

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.ui.component.fileupload.StorageFileUploadUiConfig

/**
 * Creates a test [StorageFileUploadUiConfig] instance.
 */
fun createTestStorageFileUploadUiConfig(
    maxFiles: Int? = 5,
    targetScope: String? = "avatar",
    category: StorageCategory? = StorageCategory.IMAGE,
    visibility: StorageFileVisibility? = StorageFileVisibility.PUBLIC,
    acceptedFileTypes: List<String> = listOf(".jpg", ".png", "image/*"),
    minFileSizeBytes: Long? = 1024L,
    maxFileSizeBytes: Long? = 2097152L,
    autoSubmit: Boolean = false
) = StorageFileUploadUiConfig(
    maxFiles = maxFiles,
    targetScope = targetScope,
    category = category,
    visibility = visibility,
    acceptedFileTypes = acceptedFileTypes,
    minFileSizeBytes = minFileSizeBytes,
    maxFileSizeBytes = maxFileSizeBytes,
    autoSubmit = autoSubmit
)
