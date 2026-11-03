package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrlPresets
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Structured wire payload for file URL variants, including preset resolution variants.
 *
 * @property originalUrl Direct CDN URL or presigned GET URL for the original file.
 * @property urlPresets Map of preset keys (e.g. [FileUrlPresets.THUMBNAIL]) to resolution variant URLs.
 */
@Serializable
data class FileUrlsPayload(
    @SerialName(StorageApiFields.ORIGINAL_URL)
    val originalUrl: String,

    @SerialName(StorageApiFields.URL_PRESETS)
    val urlPresets: Map<String, String> = emptyMap()
)
