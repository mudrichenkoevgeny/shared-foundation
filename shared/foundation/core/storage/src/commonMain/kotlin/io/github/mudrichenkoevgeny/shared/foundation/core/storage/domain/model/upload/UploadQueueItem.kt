package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate

/**
 * State representation of a single item in the client file upload queue.
 *
 * Shared across client SDKs (Web, KMP Android/iOS/Desktop) to track upload execution.
 *
 * @property id Unique local identifier for the queue item.
 * @property category Storage category assigned to the file.
 * @property fileName Original file name including extension.
 * @property fileSizeBytes File size in bytes.
 * @property mimeType MIME type of the file if known.
 * @property progressPercent Current upload progress percentage (0..100).
 * @property status Current execution state of the queue item.
 * @property error Error details if the upload failed.
 * @property uploadedFile Server file record projection once upload and confirmation succeed.
 */
data class UploadQueueItem(
    val id: UploadQueueItemId,
    val category: StorageCategory,
    val fileName: String,
    val fileSizeBytes: Long,
    val mimeType: String? = null,
    val progressPercent: Int = 0,
    val status: UploadQueueItemStatus = UploadQueueItemStatus.IDLE,
    val error: ApiErrorResponse? = null,
    val uploadedFile: StorageFilePrivate? = null
)
