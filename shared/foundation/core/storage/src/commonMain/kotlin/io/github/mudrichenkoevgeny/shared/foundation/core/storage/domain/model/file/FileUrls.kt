package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

/**
 * Structured URL variants for accessing a storage file, including optional visual previews.
 *
 * @property originalUrl Direct CDN URL or presigned GET URL for the original file.
 * @property thumbnailUrl Optional URL for a small visual thumbnail (e.g. for images or document cover pages), or `null`.
 * @property previewUrl Optional URL for a medium visual preview, or `null`.
 */
data class FileUrls(
    val originalUrl: String,
    val thumbnailUrl: String?,
    val previewUrl: String?
)
