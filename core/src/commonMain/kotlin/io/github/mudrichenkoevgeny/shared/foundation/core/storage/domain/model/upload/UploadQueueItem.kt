package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.upload

import io.github.mudrichenkoevgeny.shared.foundation.core.common.error.model.ApiErrorResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.StorageFilePrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility

/**
 * State representation of a single item in the client file upload queue.
 *
 * Shared across client SDKs (Web, KMP Android/iOS/Desktop) to track upload execution.
 *
 * @property id Unique local identifier for the queue item.
 * @property category Storage category assigned to the file.
 * @property visibility Storage file access visibility level ([StorageFileVisibility]).
 * @property fileName Original file name including extension.
 * @property fileSizeBytes File size in bytes.
 * @property mimeType MIME type of the file if known.
 * @property progressPercent Current upload progress percentage (0..100).
 * @property status Current execution state of the queue item.
 * @property error Error details if the upload failed.
 * @property uploadedFile Server file record projection once upload and confirmation succeed.
 */
public data class UploadQueueItem(
    public val id: UploadQueueItemId,
    public val category: StorageCategory,
    public val visibility: StorageFileVisibility = StorageFileVisibility.PRIVATE,
    public val fileName: String,
    public val fileSizeBytes: Long,
    public val mimeType: String? = null,
    public val progressPercent: Int = 0,
    public val status: UploadQueueItemStatus = UploadQueueItemStatus.IDLE,
    public val error: ApiErrorResponse? = null,
    public val uploadedFile: StorageFilePrivate? = null
)
