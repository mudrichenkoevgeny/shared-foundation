package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.domain.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileUrls

/**
 * Creates a test [FileUrls] domain model instance.
 */
fun createTestFileUrls(
    originalUrl: String = "https://cdn.example.com/files/00000000-0000-4000-8000-000000000001",
    thumbnailUrl: String? = "https://cdn.example.com/files/00000000-0000-4000-8000-000000000001_thumb",
    previewUrl: String? = "https://cdn.example.com/files/00000000-0000-4000-8000-000000000001_preview"
) = FileUrls(
    originalUrl = originalUrl,
    thumbnailUrl = thumbnailUrl,
    previewUrl = previewUrl
)
