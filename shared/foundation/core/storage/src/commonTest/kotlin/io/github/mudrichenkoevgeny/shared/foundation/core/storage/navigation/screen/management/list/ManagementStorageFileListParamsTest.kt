package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.management.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageSortValues.StorageFileSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementStorageFileListParamsTest {

    @Test
    fun `parse extracts management storage file list params correctly`() {
        val raw = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to "2",
            ListingParamNames.Pagination.PAGE_SIZE to "50",
            ListingParamNames.Sort.SORT_BY to "file_name",
            ListingParamNames.Sort.SORT_ORDER to "asc",
            StorageFilterValues.StorageFileFilterValues.FILE_ID to listOf("00000000-0000-4000-8000-000000000001"),
            StorageFilterValues.StorageFileFilterValues.CATEGORY to listOf("image", "video"),
            StorageFilterValues.StorageFileFilterValues.VISIBILITY to listOf("public"),
            StorageFilterValues.StorageFileFilterValues.STATUS to listOf("ready"),
            StorageFilterValues.StorageFileFilterValues.TARGET_SCOPE to listOf("avatar")
        )

        val parsed = ManagementStorageFileListParams.parse(raw)

        assertEquals(2, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(StorageFileSortBy.FILE_NAME, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf("00000000-0000-4000-8000-000000000001"), parsed.fileIds)
        assertEquals(listOf(StorageCategory.IMAGE, StorageCategory.VIDEO), parsed.categories)
        assertEquals(listOf(StorageFileVisibility.PUBLIC), parsed.visibilities)
        assertEquals(listOf(StorageFileStatus.READY), parsed.statuses)
        assertEquals(listOf("avatar"), parsed.targetScopes)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = ManagementStorageFileListParams.parse(emptyMap())
        assertEquals(ManagementStorageFileListParams(), parsed)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = ManagementStorageFileListParams(
            pageNumber = 2,
            pageSize = 50,
            sortBy = StorageFileSortBy.FILE_NAME,
            sortOrder = SortOrder.ASC,
            fileIds = listOf("00000000-0000-4000-8000-000000000001"),
            categories = listOf(StorageCategory.IMAGE),
            visibilities = listOf(StorageFileVisibility.PUBLIC),
            statuses = listOf(StorageFileStatus.READY),
            targetScopes = listOf("avatar")
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("2"), serialized[ListingParamNames.Pagination.PAGE_NUMBER])
        assertEquals(listOf("50"), serialized[ListingParamNames.Pagination.PAGE_SIZE])
        assertEquals(listOf("file_name"), serialized[ListingParamNames.Sort.SORT_BY])
        assertEquals(listOf("asc"), serialized[ListingParamNames.Sort.SORT_ORDER])
        assertEquals(listOf("00000000-0000-4000-8000-000000000001"), serialized[StorageFilterValues.StorageFileFilterValues.FILE_ID])
        assertEquals(listOf("image"), serialized[StorageFilterValues.StorageFileFilterValues.CATEGORY])
        assertEquals(listOf("public"), serialized[StorageFilterValues.StorageFileFilterValues.VISIBILITY])
        assertEquals(listOf("ready"), serialized[StorageFilterValues.StorageFileFilterValues.STATUS])
        assertEquals(listOf("avatar"), serialized[StorageFilterValues.StorageFileFilterValues.TARGET_SCOPE])
        assertEquals(serialized, ManagementStorageFileListParams.serialize(model))
    }
}
