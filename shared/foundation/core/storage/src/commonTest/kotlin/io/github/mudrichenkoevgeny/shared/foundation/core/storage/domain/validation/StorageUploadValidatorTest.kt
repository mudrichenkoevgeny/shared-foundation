package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.validation

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class StorageUploadValidatorTest {

    @Test
    fun `isFileTypeAccepted accepts any file when filter list is empty`() {
        assertTrue(StorageUploadValidator.isFileTypeAccepted(emptyList(), "avatar.png", "image/png"))
        assertTrue(StorageUploadValidator.isFileTypeAccepted(emptyList(), "doc.pdf", null))
    }

    @Test
    fun `isFileTypeAccepted matches extension filters case-insensitively`() {
        val filters = listOf(".jpg", ".png", "pdf")

        assertTrue(StorageUploadValidator.isFileTypeAccepted(filters, "avatar.PNG", null))
        assertTrue(StorageUploadValidator.isFileTypeAccepted(filters, "PHOTO.JPG", null))
        assertTrue(StorageUploadValidator.isFileTypeAccepted(filters, "document.PDF", null))

        assertFalse(StorageUploadValidator.isFileTypeAccepted(filters, "archive.zip", null))
        assertFalse(StorageUploadValidator.isFileTypeAccepted(filters, "script.sh", null))
    }

    @Test
    fun `isFileTypeAccepted matches MIME type wildcards and exact types`() {
        val filters = listOf("image/*", "application/pdf")

        assertTrue(StorageUploadValidator.isFileTypeAccepted(filters, "photo.unknown", "image/webp"))
        assertTrue(StorageUploadValidator.isFileTypeAccepted(filters, "doc.bin", "application/pdf"))

        assertFalse(StorageUploadValidator.isFileTypeAccepted(filters, "track.mp3", "audio/mpeg"))
        assertFalse(StorageUploadValidator.isFileTypeAccepted(filters, "data.json", "application/json"))
    }

    @Test
    fun `isFileTypeAccepted handles blank filter strings and files without extension`() {
        val filters = listOf("", "   ", ".png")

        assertFalse(StorageUploadValidator.isFileTypeAccepted(listOf("", "  "), "avatar.png", "image/png"))
        assertFalse(StorageUploadValidator.isFileTypeAccepted(filters, "noextension", null))
        assertTrue(StorageUploadValidator.isFileTypeAccepted(filters, "avatar.png", null))
    }

    @Test
    fun `isFileSizeValid checks maximum file size bounds and rejects negative sizes`() {
        assertTrue(StorageUploadValidator.isFileSizeValid(maxFileSizeBytes = 1000L, fileSizeBytes = 500L))
        assertTrue(StorageUploadValidator.isFileSizeValid(maxFileSizeBytes = 1000L, fileSizeBytes = 1000L))
        assertTrue(StorageUploadValidator.isFileSizeValid(maxFileSizeBytes = null, fileSizeBytes = 9999999L))

        assertFalse(StorageUploadValidator.isFileSizeValid(maxFileSizeBytes = 1000L, fileSizeBytes = 1001L))
        assertFalse(StorageUploadValidator.isFileSizeValid(maxFileSizeBytes = 1000L, fileSizeBytes = -1L))
    }

    @Test
    fun `isBatchSizeValid checks maximum file count bounds and rejects negative counts`() {
        assertTrue(StorageUploadValidator.isBatchSizeValid(maxFiles = 5, currentCount = 0))
        assertTrue(StorageUploadValidator.isBatchSizeValid(maxFiles = 5, currentCount = 3))
        assertTrue(StorageUploadValidator.isBatchSizeValid(maxFiles = 5, currentCount = 5))
        assertTrue(StorageUploadValidator.isBatchSizeValid(maxFiles = null, currentCount = 100))

        assertFalse(StorageUploadValidator.isBatchSizeValid(maxFiles = 5, currentCount = 6))
        assertFalse(StorageUploadValidator.isBatchSizeValid(maxFiles = 5, currentCount = -1))
    }
}
