package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrls
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.FileUrlsPayload

/**
 * Extension functions mapping file URL variants between domain and network representations.
 */

/**
 * Builds domain [FileUrls] from [FileUrlsPayload].
 */
fun FileUrlsPayload.toFileUrls(): FileUrls = FileUrls(
    originalUrl = originalUrl,
    thumbnailUrl = thumbnailUrl,
    previewUrl = previewUrl
)

/**
 * Builds network [FileUrlsPayload] from domain [FileUrls].
 */
fun FileUrls.toFileUrlsPayload(): FileUrlsPayload = FileUrlsPayload(
    originalUrl = originalUrl,
    thumbnailUrl = thumbnailUrl,
    previewUrl = previewUrl
)
