package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import kotlin.time.Instant

/**
 * Public summary view of a storage file.
 *
 * Contains non-sensitive metadata required for embedding or public list display.
 *
 * @property id Unique file identifier ([FileId]).
 * @property fileName Original file name provided at upload.
 * @property fileSizeBytes File size in bytes.
 * @property mimeType Media MIME type (e.g. `image/png`).
 * @property category Storage classification ([StorageCategory]).
 * @property visibility Access visibility classification ([StorageFileVisibility]).
 * @property status Current file lifecycle status ([StorageFileStatus]).
 * @property fileUrl Direct CDN URL for public files, or short-lived presigned GET read URL
 *           for private files generated on-the-fly by the server.
 * @property createdAt Timestamp when the file record was created ([Instant]).
 */
data class StorageFileSummary(
    val id: FileId,
    val fileName: String,
    val fileSizeBytes: Long,
    val mimeType: String,
    val category: StorageCategory,
    val visibility: StorageFileVisibility,
    val status: StorageFileStatus,
    val fileUrl: String,
    val createdAt: Instant
)
