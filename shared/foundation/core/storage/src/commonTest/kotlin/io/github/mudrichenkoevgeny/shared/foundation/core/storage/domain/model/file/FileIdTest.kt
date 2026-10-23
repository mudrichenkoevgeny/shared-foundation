package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
import kotlin.uuid.Uuid

class FileIdTest {

    @Test
    fun `parsing roundtrips from dash-hex string`() {
        val uuidString = "00000000-0000-4000-8000-000000000001"
        val fileId = FileId(Uuid.parse(uuidString))
        assertEquals(fileId, fileId.asHexDashString().toFileIdOrNull())
    }

    @Test
    fun `toFileIdOrNull returns null for invalid UUID`() {
        assertNull("invalid".toFileIdOrNull())
    }

    @Test
    fun `toFileIdOrThrow throws for invalid UUID`() {
        assertFailsWith<Throwable> {
            "invalid".toFileIdOrThrow()
        }
    }
}
