package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.domain.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileSummary

/**
 * Creates a test [StorageFileSummary] domain model instance.
 */
fun createTestStorageFileSummary(
    id: FileId = createTestFileId(),
    fileName: String = "test_image.png",
    fileSizeBytes: Long = 1048576L,
    mimeType: String = "image/png",
    category: StorageCategory = StorageCategory.IMAGE,
    fileUrl: String = "https://cdn.example.com/files/00000000-0000-4000-8000-000000000001"
) = StorageFileSummary(
    id = id,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category,
    fileUrl = fileUrl
)
