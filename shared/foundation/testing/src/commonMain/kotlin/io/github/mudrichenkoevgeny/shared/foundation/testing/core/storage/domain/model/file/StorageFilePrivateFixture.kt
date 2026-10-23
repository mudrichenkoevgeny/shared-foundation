package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.domain.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import kotlin.time.Instant

/**
 * Creates a test [StorageFilePrivate] domain model instance.
 */
fun createTestStorageFilePrivate(
    id: FileId = createTestFileId(),
    ownerUserId: String? = "usr_000000000001",
    fileName: String = "test_image.png",
    fileSizeBytes: Long = 1048576L,
    mimeType: String = "image/png",
    category: StorageCategory = StorageCategory.IMAGE,
    visibility: StorageFileVisibility = StorageFileVisibility.PUBLIC,
    status: StorageFileStatus = StorageFileStatus.READY,
    targetScope: String? = "avatar",
    fileUrl: String = "https://cdn.example.com/files/00000000-0000-4000-8000-000000000001",
    createdAt: Instant = Instant.fromEpochMilliseconds(1700000000000L),
    updatedAt: Instant? = null
) = StorageFilePrivate(
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
