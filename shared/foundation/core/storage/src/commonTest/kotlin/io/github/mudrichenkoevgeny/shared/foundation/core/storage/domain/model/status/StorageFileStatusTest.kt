package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StorageFileStatusTest {

    @Test
    fun `fromValue accepts case-insensitive wire values`() {
        assertEquals(StorageFileStatus.PENDING_UPLOAD, StorageFileStatus.fromValueOrNull("pending_upload"))
        assertEquals(StorageFileStatus.READY, StorageFileStatus.fromValueOrNull("ReAdY"))
        assertEquals(StorageFileStatus.DELETED, StorageFileStatus.fromValueOrNull("deleted"))
    }

    @Test
    fun `fromValue returns null for unknown values`() {
        assertNull(StorageFileStatus.fromValueOrNull("unknown"))
    }
}
