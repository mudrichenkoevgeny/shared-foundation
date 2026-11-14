package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.domain.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrls
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFileSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import kotlin.time.Instant

/**
 * Creates a test [StorageFileSummary] domain model instance.
 */
public fun createTestStorageFileSummary(
    id: FileId = createTestFileId(),
    fileName: String = "test_image.png",
    fileSizeBytes: Long = 1048576L,
    mimeType: String = "image/png",
    category: StorageCategory = StorageCategory.IMAGE,
    visibility: StorageFileVisibility = StorageFileVisibility.PUBLIC,
    status: StorageFileStatus = StorageFileStatus.READY,
    fileUrls: FileUrls = createTestFileUrls(),
    createdAt: Instant = Instant.fromEpochMilliseconds(1700000000000L)
): StorageFileSummary = StorageFileSummary(
    id = id,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    category = category,
    visibility = visibility,
    status = status,
    fileUrls = fileUrls,
    createdAt = createdAt
)
