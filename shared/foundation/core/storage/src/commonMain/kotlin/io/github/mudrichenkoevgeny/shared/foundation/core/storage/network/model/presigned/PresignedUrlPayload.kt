package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.presigned

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire payload containing generated presigned S3 upload URL details.
 *
 * @property fileId Unique file identifier string (hex-dash UUID).
 * @property uploadUrl Presigned S3 PUT URL for uploading binary data.
 * @property fileUrl Direct CDN URL for public files, or short-lived presigned GET read URL for private files generated on-the-fly by the server.
 * @property expiresAt Expiration timestamp in Unix epoch milliseconds.
 */
@Serializable
data class PresignedUrlPayload(
    @SerialName(StorageApiFields.FILE_ID)
    val fileId: String,

    @SerialName(StorageApiFields.UPLOAD_URL)
    val uploadUrl: String,

    @SerialName(StorageApiFields.FILE_URL)
    val fileUrl: String,

    @SerialName(StorageApiFields.EXPIRES_AT)
    val expiresAt: Long
)
