package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.domain.model.upload

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload.UploadQueueItem
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload.UploadQueueItemId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload.UploadQueueItemStatus

/**
 * Creates a test [UploadQueueItem] instance.
 */
fun createTestUploadQueueItem(
    id: UploadQueueItemId = UploadQueueItemId.generate(),
    category: StorageCategory = StorageCategory.IMAGE,
    fileName: String = "avatar.png",
    fileSizeBytes: Long = 1048576L,
    mimeType: String? = "image/png",
    progressPercent: Int = 0,
    status: UploadQueueItemStatus = UploadQueueItemStatus.IDLE,
    error: ApiErrorResponse? = null,
    uploadedFile: StorageFilePrivate? = null
) = UploadQueueItem(
    id = id,
    category = category,
    fileName = fileName,
    fileSizeBytes = fileSizeBytes,
    mimeType = mimeType,
    progressPercent = progressPercent,
    status = status,
    error = error,
    uploadedFile = uploadedFile
)
