package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

import io.github.mudrichenkoevgeny.shared.foundation.core.common.serialization.FoundationJson
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FileUrlsTest {
    @Test
    fun testSerializationWithPresets() {
        val presetsMap =
            mapOf(
                FileUrlPresets.THUMBNAIL to "https://cdn.example.com/thumb.jpg",
                FileUrlPresets.PREVIEW to "https://cdn.example.com/prev.jpg",
                FileUrlPresets.AVATAR_SM to "https://cdn.example.com/avatar_sm.jpg"
            )
        val fileUrls =
            FileUrls(
                originalUrl = "https://cdn.example.com/orig.jpg",
                urlPresets = presetsMap
            )

        val json = FoundationJson.encodeToString(fileUrls)
        val decoded = FoundationJson.decodeFromString<FileUrls>(json)

        assertEquals("https://cdn.example.com/orig.jpg", decoded.originalUrl)
        assertEquals(3, decoded.urlPresets.size)
        assertEquals("https://cdn.example.com/thumb.jpg", decoded.urlPresets[FileUrlPresets.THUMBNAIL])
        assertEquals("https://cdn.example.com/prev.jpg", decoded.urlPresets[FileUrlPresets.PREVIEW])
        assertEquals("https://cdn.example.com/avatar_sm.jpg", decoded.urlPresets[FileUrlPresets.AVATAR_SM])
    }

    @Test
    fun testSerializationWithEmptyPresets() {
        val fileUrls =
            FileUrls(
                originalUrl = "https://cdn.example.com/orig.jpg"
            )

        val json = FoundationJson.encodeToString(fileUrls)
        val decoded = FoundationJson.decodeFromString<FileUrls>(json)

        assertEquals("https://cdn.example.com/orig.jpg", decoded.originalUrl)
        assertTrue(decoded.urlPresets.isEmpty())
    }
}
