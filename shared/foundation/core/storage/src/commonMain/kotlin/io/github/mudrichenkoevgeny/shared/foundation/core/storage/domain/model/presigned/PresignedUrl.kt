package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.presigned

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import kotlin.time.Instant

/**
 * Domain model containing a generated presigned S3 upload URL.
 *
 * @property fileId Unique identifier assigned to the file ([FileId]).
 * @property uploadUrl Presigned S3 PUT URL for uploading file binary.
 * @property fileUrl Final URL where the file will be accessible after upload.
 * @property expiresAt Expiration timestamp of the upload URL ([Instant]).
 */
data class PresignedUrl(
    val fileId: FileId,
    val uploadUrl: String,
    val fileUrl: String,
    val expiresAt: Instant
)
