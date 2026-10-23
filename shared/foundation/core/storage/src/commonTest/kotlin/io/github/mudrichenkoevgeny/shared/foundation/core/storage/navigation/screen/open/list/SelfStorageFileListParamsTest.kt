package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.open.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageSortValues.StorageFileSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class SelfStorageFileListParamsTest {

    @Test
    fun `parse extracts self storage file list params correctly`() {
        val raw = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to "1",
            ListingParamNames.Pagination.PAGE_SIZE to "20",
            ListingParamNames.Sort.SORT_BY to "created_at",
            ListingParamNames.Sort.SORT_ORDER to "desc",
            StorageFilterValues.StorageFileFilterValues.FILE_ID to listOf("00000000-0000-4000-8000-000000000001"),
            StorageFilterValues.StorageFileFilterValues.CATEGORY to listOf("image"),
            StorageFilterValues.StorageFileFilterValues.STATUS to listOf("ready"),
            StorageFilterValues.StorageFileFilterValues.TARGET_SCOPE to listOf("avatar")
        )

        val parsed = SelfStorageFileListParams.parse(raw)

        assertEquals(1, parsed.pageNumber)
        assertEquals(20, parsed.pageSize)
        assertEquals(StorageFileSortBy.CREATED_AT, parsed.sortBy)
        assertEquals(SortOrder.DESC, parsed.sortOrder)
        assertEquals(listOf("00000000-0000-4000-8000-000000000001"), parsed.fileIds)
        assertEquals(listOf(StorageCategory.IMAGE), parsed.categories)
        assertEquals(listOf(StorageFileStatus.READY), parsed.statuses)
        assertEquals(listOf("avatar"), parsed.targetScopes)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = SelfStorageFileListParams.parse(emptyMap())
        assertEquals(SelfStorageFileListParams(), parsed)
    }
}
