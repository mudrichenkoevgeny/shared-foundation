package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.network.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFileSummaryPayload

/**
 * Creates a test [StorageFileSummaryPayload] wire object instance.
 */
fun createTestStorageFileSummaryPayload(
    id: String = "00000000-0000-4000-8000-000000000001",
    fileName: String = "test_image.png",
    fileSizeBytes: Long = 1048576L,
    mimeType: String = "image/png",
    category: String = StorageCategory.IMAGE.value,
    fileUrl: String = "https://cdn.example.com/files/00000000-0000-4000-8000-000000000001"
) = StorageFileSummaryPayload(
    id = id,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category,
    fileUrl = fileUrl
)
