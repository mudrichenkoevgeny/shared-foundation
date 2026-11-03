package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization.FoundationJson
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class FileUrlsTest {

    @Test
    fun testSerializationWithAllFields() {
        val fileUrls = FileUrls(
            originalUrl = "https://cdn.example.com/orig.jpg",
            thumbnailUrl = "https://cdn.example.com/thumb.jpg",
            previewUrl = "https://cdn.example.com/prev.jpg"
        )

        val json = FoundationJson.encodeToString(fileUrls)
        val decoded = FoundationJson.decodeFromString<FileUrls>(json)

        assertEquals("https://cdn.example.com/orig.jpg", decoded.originalUrl)
        assertEquals("https://cdn.example.com/thumb.jpg", decoded.thumbnailUrl)
        assertEquals("https://cdn.example.com/prev.jpg", decoded.previewUrl)
    }

    @Test
    fun testSerializationWithNullOptionalFields() {
        val fileUrls = FileUrls(
            originalUrl = "https://cdn.example.com/orig.jpg"
        )

        val json = FoundationJson.encodeToString(fileUrls)
        val decoded = FoundationJson.decodeFromString<FileUrls>(json)

        assertEquals("https://cdn.example.com/orig.jpg", decoded.originalUrl)
        assertNull(decoded.thumbnailUrl)
        assertNull(decoded.previewUrl)
    }
}
