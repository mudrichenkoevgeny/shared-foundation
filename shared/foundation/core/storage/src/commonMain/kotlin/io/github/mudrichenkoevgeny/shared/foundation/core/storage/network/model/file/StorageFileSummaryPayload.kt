package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

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
 * @property fileUrl Direct CDN URL for public files, or short-lived presigned GET read URL for private files generated on-the-fly by the server.
 * @property createdAt Creation timestamp in ISO-8601 format.
 */
@Serializable
data class StorageFileSummaryPayload(
    @SerialName(StorageApiFields.FILE_ID)
    val id: String,

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

    @SerialName(StorageApiFields.FILE_URL)
    val fileUrl: String,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Instant
)
