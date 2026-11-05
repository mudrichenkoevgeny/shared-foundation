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
 * @property fileUrls Structured file URL variants object, or `null`.
 * @property createdAt Timestamp when record was created in Unix epoch milliseconds.
 * @property updatedAt Timestamp when record was last updated in Unix epoch milliseconds, or `null`.
 */
@Serializable
public data class StorageFilePrivatePayload(
    @SerialName(StorageApiFields.FILE_ID)
    public val id: String,
    @SerialName(StorageApiFields.OWNER_USER_ID)
    public val ownerUserId: String? = null,
    @SerialName(StorageApiFields.FILE_NAME)
    public val fileName: String,
    @SerialName(StorageApiFields.FILE_SIZE_BYTES)
    public val fileSizeBytes: Long,
    @SerialName(StorageApiFields.MIME_TYPE)
    public val mimeType: String,
    @SerialName(StorageApiFields.CATEGORY)
    public val category: String,
    @SerialName(StorageApiFields.VISIBILITY)
    public val visibility: String,
    @SerialName(StorageApiFields.STATUS)
    public val status: String,
    @SerialName(StorageApiFields.TARGET_SCOPE)
    public val targetScope: String? = null,
    @SerialName(StorageApiFields.FILE_URLS)
    public val fileUrls: FileUrlsPayload? = null,
    @SerialName(CommonApiFields.CREATED_AT)
    public val createdAt: Long,
    @SerialName(CommonApiFields.UPDATED_AT)
    public val updatedAt: Long? = null
)
