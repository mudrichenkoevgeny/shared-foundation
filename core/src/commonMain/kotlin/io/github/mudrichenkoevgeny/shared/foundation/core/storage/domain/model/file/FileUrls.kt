package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

import kotlinx.serialization.Serializable

/**
 * Structured URL variants for accessing a storage file, including preset resolution variants.
 *
 * @property originalUrl Direct CDN URL or presigned GET URL for the original file.
 * @property urlPresets Map of preset keys (e.g. [FileUrlPresets.THUMBNAIL]) to resolution variant URLs.
 */
@Serializable
public data class FileUrls(
    public val originalUrl: String,
    public val urlPresets: Map<String, String> = emptyMap()
)
