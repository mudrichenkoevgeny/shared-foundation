package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.network.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrlPresets
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.FileUrlsPayload

/**
 * Creates a test [FileUrlsPayload] wire object instance.
 */
fun createTestFileUrlsPayload(
    originalUrl: String = "https://cdn.example.com/files/00000000-0000-4000-8000-000000000001",
    presets: Map<String, String> = mapOf(
        FileUrlPresets.THUMBNAIL to "https://cdn.example.com/files/00000000-0000-4000-8000-000000000001_thumb",
        FileUrlPresets.PREVIEW to "https://cdn.example.com/files/00000000-0000-4000-8000-000000000001_preview"
    )
) = FileUrlsPayload(
    originalUrl = originalUrl,
    urlPresets = presets
)
