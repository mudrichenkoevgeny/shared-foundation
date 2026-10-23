package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StorageFileVisibilityTest {

    @Test
    fun `fromValue accepts case-insensitive wire values`() {
        assertEquals(StorageFileVisibility.PUBLIC, StorageFileVisibility.fromValueOrNull("public"))
        assertEquals(StorageFileVisibility.PRIVATE, StorageFileVisibility.fromValueOrNull("PrIvAtE"))
    }

    @Test
    fun `fromValue returns null for unknown values`() {
        assertNull(StorageFileVisibility.fromValueOrNull("unknown"))
    }
}
