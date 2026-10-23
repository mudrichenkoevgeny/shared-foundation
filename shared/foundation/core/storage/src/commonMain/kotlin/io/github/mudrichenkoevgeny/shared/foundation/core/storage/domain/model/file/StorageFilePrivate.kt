package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import kotlin.time.Instant

/**
 * Detailed private view of a storage file.
 *
 * Contains full owner, lifecycle, and timestamp information accessible by file owners or management roles.
 *
 * @property id Unique file identifier ([FileId]).
 * @property ownerUserId Optional user ID string (hex-dash UUID) of the account that uploaded the file, or `null`.
 * @property fileName Original file name provided at upload.
 * @property fileSizeBytes File size in bytes.
 * @property mimeType Media MIME type (e.g. `image/png`).
 * @property category Storage classification ([StorageCategory]).
 * @property visibility Access visibility classification ([StorageFileVisibility]).
 * @property status Current file lifecycle status ([StorageFileStatus]).
 * @property targetScope Optional target scope or feature context string (e.g. "avatar", "attachment").
 * @property fileUrl Direct CDN URL for public files, or short-lived presigned GET read URL for private files generated on-the-fly by the server.
 * @property createdAt Timestamp when the file record was created ([Instant]).
 * @property updatedAt Timestamp when the file record was last modified ([Instant]), or `null`.
 */
data class StorageFilePrivate(
    val id: FileId,
    val ownerUserId: String?,
    val fileName: String,
    val fileSizeBytes: Long,
    val mimeType: String,
    val category: StorageCategory,
    val visibility: StorageFileVisibility,
    val status: StorageFileStatus,
    val targetScope: String?,
    val fileUrl: String,
    val createdAt: Instant,
    val updatedAt: Instant?
)
