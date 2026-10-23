package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.network.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload

/**
 * Creates a test [StorageFilePrivatePayload] wire object instance.
 */
fun createTestStorageFilePrivatePayload(
    id: String = "00000000-0000-4000-8000-000000000001",
    ownerUserId: String? = "usr_000000000001",
    fileName: String = "test_image.png",
    fileSizeBytes: Long = 1048576L,
    mimeType: String = "image/png",
    category: String = StorageCategory.IMAGE.value,
    visibility: String = StorageFileVisibility.PUBLIC.value,
    status: String = StorageFileStatus.READY.value,
    targetScope: String? = "avatar",
    fileUrl: String = "https://cdn.example.com/files/00000000-0000-4000-8000-000000000001",
    createdAt: Long = 1700000000000L,
    updatedAt: Long? = null
) = StorageFilePrivatePayload(
    id = id,
    ownerUserId = ownerUserId,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category,
    visibility = visibility,
    status = status,
    targetScope = targetScope,
    fileUrl = fileUrl,
    createdAt = createdAt,
    updatedAt = updatedAt
)
