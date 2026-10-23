package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class StorageCategoryTest {

    @Test
    fun `fromValue accepts case-insensitive enum names and wire values`() {
        assertEquals(StorageCategory.IMAGE, StorageCategory.fromValueOrNull("image"))
        assertEquals(StorageCategory.VIDEO, StorageCategory.fromValueOrNull("ViDeO"))
        assertEquals(StorageCategory.DOCUMENT, StorageCategory.fromValueOrNull("document"))
    }

    @Test
    fun `fromValue returns null for unknown values`() {
        assertNull(StorageCategory.fromValueOrNull("unknown"))
    }
}
