package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire summary payload for a storage file record.
 *
 * @property id File identifier string (hex-dash UUID).
 * @property fileName Original file name.
 * @property fileSizeBytes File size in bytes.
 * @property mimeType File MIME type.
 * @property category Storage category wire value.
 * @property visibility Access visibility classification wire value.
 * @property status Current file lifecycle status wire value.
 * @property fileUrls Structured file URL variants object, or `null`.
 * @property createdAt Timestamp when record was created in Unix epoch milliseconds.
 */
@Serializable
public data class StorageFileSummaryPayload(
    @SerialName(StorageApiFields.FILE_ID)
    public val id: String,
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
    @SerialName(StorageApiFields.FILE_URLS)
    public val fileUrls: FileUrlsPayload? = null,
    @SerialName(CommonApiFields.CREATED_AT)
    public val createdAt: Long
)
