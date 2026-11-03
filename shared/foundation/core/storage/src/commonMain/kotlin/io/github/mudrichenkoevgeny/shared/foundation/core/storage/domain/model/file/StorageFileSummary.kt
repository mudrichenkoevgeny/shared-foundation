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
 * @property fileUrls Structured URL variants ([FileUrls]) for public CDN or short-lived presigned GET read URLs, or `null`.
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
    val fileUrls: FileUrls?,
    val createdAt: Instant
)
