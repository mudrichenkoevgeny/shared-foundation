package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.request.presigned

import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.NotBlankStringField
import io.github.mudrichenkoevgeny.shared.foundation.core.common.validation.RequiredField
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Request payload for generating a presigned S3 upload URL.
 *
 * @property fileName Original file name to be uploaded.
 * @property fileSizeBytes File size in bytes.
 * @property mimeType MIME type of the file.
 * @property category Storage category wire value (matching [StorageCategory.value]).
 * @property visibility Storage visibility wire value (matching [StorageFileVisibility.value]).
 * @property targetScope Optional target scope or feature context string (e.g. "avatar", "attachment").
 */
@Serializable
data class PresignedUrlRequest(
    @NotBlankStringField
    @SerialName(StorageApiFields.FILE_NAME)
    val fileName: String,

    @RequiredField
    @SerialName(StorageApiFields.FILE_SIZE_BYTES)
    val fileSizeBytes: Long,

    @NotBlankStringField
    @SerialName(StorageApiFields.MIME_TYPE)
    val mimeType: String,

    @NotBlankStringField
    @SerialName(StorageApiFields.CATEGORY)
    val category: String,

    @NotBlankStringField
    @SerialName(StorageApiFields.VISIBILITY)
    val visibility: String = StorageFileVisibility.PRIVATE.value,

    @SerialName(StorageApiFields.TARGET_SCOPE)
    val targetScope: String? = null
)
