package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.upload

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.contract.StorageNavigationPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiFields
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class StorageFileUploadParamsTest {

    @Test
    fun `parse correctly extracts all upload screen parameters`() {
        val raw = mapOf(
            StorageNavigationPaths.MAX_FILES to "5",
            StorageApiFields.TARGET_SCOPE to "avatar",
            StorageApiFields.CATEGORY to "IMAGE",
            StorageNavigationPaths.ACCEPTED_FILE_TYPES to ".jpg,.png,image/*",
            StorageNavigationPaths.MAX_FILE_SIZE_BYTES to "2097152",
            StorageNavigationPaths.AUTO_SUBMIT to "true"
        )

        val parsed = StorageFileUploadParams.parse(raw)

        assertEquals(5, parsed.maxFiles)
        assertEquals("avatar", parsed.targetScope)
        assertEquals(StorageCategory.IMAGE, parsed.category)
        assertEquals(listOf(".jpg", ".png", "image/*"), parsed.acceptedFileTypes)
        assertEquals(2097152L, parsed.maxFileSizeBytes)
        assertTrue(parsed.autoSubmit)
    }

    @Test
    fun `parse applies default values for missing or blank parameters`() {
        val parsed = StorageFileUploadParams.parse(emptyMap())

        assertNull(parsed.maxFiles)
        assertNull(parsed.targetScope)
        assertNull(parsed.category)
        assertTrue(parsed.acceptedFileTypes.isEmpty())
        assertNull(parsed.maxFileSizeBytes)
        assertFalse(parsed.autoSubmit)
    }

    @Test
    fun `serialize produces valid query parameters map`() {
        val params = StorageFileUploadParams(
            maxFiles = 3,
            targetScope = "ticket_attachment",
            category = StorageCategory.DOCUMENT,
            acceptedFileTypes = listOf(".pdf", ".docx", "application/pdf"),
            maxFileSizeBytes = 10485760L,
            autoSubmit = true
        )

        val serialized = StorageFileUploadParams.serialize(params)

        assertEquals(listOf("3"), serialized[StorageNavigationPaths.MAX_FILES])
        assertEquals(listOf("ticket_attachment"), serialized[StorageApiFields.TARGET_SCOPE])
        assertEquals(listOf("document"), serialized[StorageApiFields.CATEGORY])
        assertEquals(listOf(".pdf", ".docx", "application/pdf"), serialized[StorageNavigationPaths.ACCEPTED_FILE_TYPES])
        assertEquals(listOf("10485760"), serialized[StorageNavigationPaths.MAX_FILE_SIZE_BYTES])
        assertEquals(listOf("true"), serialized[StorageNavigationPaths.AUTO_SUBMIT])
    }
}
