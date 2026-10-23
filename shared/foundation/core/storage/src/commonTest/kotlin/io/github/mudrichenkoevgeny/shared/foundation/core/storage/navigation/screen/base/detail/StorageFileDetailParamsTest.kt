package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.base.detail

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.file.FileId
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract.StorageApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.uuid.Uuid

class StorageFileDetailParamsTest {

    @Test
    fun `parse extracts fileId correctly`() {
        val uuidString = "00000000-0000-4000-8000-000000000001"
        val raw = mapOf(StorageApiPaths.FILE_ID to uuidString)

        val parsed = StorageFileDetailParams.parse(raw)

        assertEquals(uuidString, parsed.fileId)
        assertEquals(FileId(Uuid.parse(uuidString)), parsed.typedFileId)
    }

    @Test
    fun `typedFileId returns null for invalid fileId`() {
        val parsed = StorageFileDetailParams(fileId = "invalid")
        assertNull(parsed.typedFileId)
    }
}
