package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import kotlin.time.Instant

/**
 * Internal domain model of a storage file.
 *
 * Contains low-level S3 bucket and object key details strictly used in backend/DB logic.
 * MUST NEVER be exposed via API or DTO payloads.
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
 * @property contentHash Optional hash of the file content (e.g., SHA-256) used for deduplication.
 * @property s3Bucket Internal S3 bucket name.
 * @property s3Key Internal S3 object key/path.
 * @property fileUrls Structured file URL variants ([FileUrls]) for public CDN or presigned GET read URLs, or `null` if not yet generated.
 * @property createdAt Timestamp when the file record was created ([Instant]).
 * @property updatedAt Timestamp when the file record was last modified ([Instant]), or `null`.
 */
public data class StorageFileInternal(
    public val id: FileId,
    public val ownerUserId: String?,
    public val fileName: String,
    public val fileSizeBytes: Long,
    public val mimeType: String,
    public val category: StorageCategory,
    public val visibility: StorageFileVisibility,
    public val status: StorageFileStatus,
    public val targetScope: String?,
    public val contentHash: String?,
    public val s3Bucket: String,
    public val s3Key: String,
    public val fileUrls: FileUrls?,
    public val createdAt: Instant,
    public val updatedAt: Instant?
)
