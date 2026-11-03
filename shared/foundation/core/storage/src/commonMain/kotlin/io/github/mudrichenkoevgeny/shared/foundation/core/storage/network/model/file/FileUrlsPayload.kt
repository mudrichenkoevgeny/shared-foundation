package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Structured wire payload for file URL variants, including optional visual previews.
 *
 * @property originalUrl Direct CDN URL or presigned GET URL for the original file.
 * @property thumbnailUrl Optional URL for a small visual thumbnail, or `null`.
 * @property previewUrl Optional URL for a medium visual preview, or `null`.
 */
@Serializable
data class FileUrlsPayload(
    @SerialName(StorageApiFields.ORIGINAL_URL)
    val originalUrl: String,

    @SerialName(StorageApiFields.THUMBNAIL_URL)
    val thumbnailUrl: String? = null,

    @SerialName(StorageApiFields.PREVIEW_URL)
    val previewUrl: String? = null
)
