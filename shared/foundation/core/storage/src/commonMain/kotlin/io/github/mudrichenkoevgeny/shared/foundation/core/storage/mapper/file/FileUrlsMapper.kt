package io.github.mudrichenkoevgeny.shared.foundation.core.storage.mapper.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrls
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.FileUrlsPayload

/**
 * Builds domain [FileUrls] from [FileUrlsPayload].
 */
public fun FileUrlsPayload.toFileUrls(): FileUrls =
    FileUrls(
        originalUrl = originalUrl,
        urlPresets = urlPresets
    )

/**
 * Builds network [FileUrlsPayload] from domain [FileUrls].
 */
public fun FileUrls.toFileUrlsPayload(): FileUrlsPayload =
    FileUrlsPayload(
        originalUrl = originalUrl,
        urlPresets = urlPresets
    )
