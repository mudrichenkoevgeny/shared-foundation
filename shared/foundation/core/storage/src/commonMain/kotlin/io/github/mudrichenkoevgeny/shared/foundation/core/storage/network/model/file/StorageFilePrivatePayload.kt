package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Detailed wire payload for a storage file record.
 *
 * @property id File identifier string (hex-dash UUID).
 * @property ownerUserId Optional owner user identifier string (hex-dash UUID), or `null`.
 * @property fileName Original file name.
 * @property fileSizeBytes File size in bytes.
 * @property mimeType File MIME type.
 * @property category Storage category wire value.
 * @property visibility Storage visibility wire value.
 * @property status Storage file status wire value.
 * @property targetScope Optional target scope or feature context string (e.g. "avatar", "attachment").
 * @property fileUrl Direct CDN URL for public files, or short-lived presigned GET read URL for private files generated on-the-fly by the server.
 * @property createdAt Timestamp when record was created in Unix epoch milliseconds.
 * @property updatedAt Timestamp when record was last updated in Unix epoch milliseconds, or `null`.
 */
@Serializable
data class StorageFilePrivatePayload(
    @SerialName(StorageApiFields.FILE_ID)
    val id: String,

    @SerialName(StorageApiFields.OWNER_USER_ID)
    val ownerUserId: String? = null,

    @SerialName(StorageApiFields.FILE_NAME)
    val fileName: String,

    @SerialName(StorageApiFields.FILE_SIZE_BYTES)
    val fileSizeBytes: Long,

    @SerialName(StorageApiFields.MIME_TYPE)
    val mimeType: String,

    @SerialName(StorageApiFields.CATEGORY)
    val category: String,

    @SerialName(StorageApiFields.VISIBILITY)
    val visibility: String,

    @SerialName(StorageApiFields.STATUS)
    val status: String,

    @SerialName(StorageApiFields.TARGET_SCOPE)
    val targetScope: String? = null,

    @SerialName(StorageApiFields.FILE_URL)
    val fileUrl: String,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long,

    @SerialName(CommonApiFields.UPDATED_AT)
    val updatedAt: Long? = null
)
