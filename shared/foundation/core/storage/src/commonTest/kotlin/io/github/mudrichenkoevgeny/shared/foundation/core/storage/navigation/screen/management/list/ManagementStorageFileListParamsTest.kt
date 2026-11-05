package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.management.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementStorageFileListParamsTest {
    @Test
    fun `parse extracts management storage file list params correctly`() {
        val raw =
            mapOf(
                ListingParamNames.Pagination.PAGE_NUMBER to "2",
                ListingParamNames.Pagination.PAGE_SIZE to "50",
                ListingParamNames.Sort.SORT_BY to "file_name",
                ListingParamNames.Sort.SORT_ORDER to "asc",
                StorageFileFilterValues.FILE_ID to listOf("00000000-0000-4000-8000-000000000001"),
                StorageFileFilterValues.OWNER_USER_ID to listOf("usr_123"),
                StorageFileFilterValues.IS_OWNER_USER_ID_SET to "true",
                StorageFileFilterValues.FILE_NAME to "avatar.png",
                StorageFileFilterValues.FILE_SIZE_BYTES_FROM to "1024",
                StorageFileFilterValues.FILE_SIZE_BYTES_TO to "2048",
                StorageFileFilterValues.MIME_TYPE to listOf("image/png"),
                StorageFileFilterValues.CATEGORY to listOf("image", "video"),
                StorageFileFilterValues.EXCLUDE_CATEGORY to listOf("audio"),
                StorageFileFilterValues.VISIBILITY to listOf("public"),
                StorageFileFilterValues.EXCLUDE_VISIBILITY to listOf("private"),
                StorageFileFilterValues.STATUS to listOf("ready"),
                StorageFileFilterValues.EXCLUDE_STATUS to listOf("pending_upload"),
                StorageFileFilterValues.TARGET_SCOPE to listOf("avatar"),
                StorageFileFilterValues.IS_TARGET_SCOPE_SET to "true",
                StorageFileFilterValues.CREATED_AT_FROM to "2026-01-01T00:00:00Z",
                StorageFileFilterValues.CREATED_AT_TO to "2026-03-01T00:00:00Z",
                StorageFileFilterValues.UPDATED_AT_FROM to "2026-01-02T00:00:00Z",
                StorageFileFilterValues.UPDATED_AT_TO to "2026-03-02T00:00:00Z",
                StorageFileFilterValues.IS_UPDATED_AT_SET to "true",
                StorageFileFilterValues.SEARCH_QUERY to "avatar"
            )

        val parsed = ManagementStorageFileListParams.parse(raw)

        assertEquals(2, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(StorageFileSortBy.FILE_NAME, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf("00000000-0000-4000-8000-000000000001"), parsed.fileIds)
        assertEquals(listOf("usr_123"), parsed.ownerUserIds)
        assertEquals(true, parsed.isOwnerUserIdSet)
        assertEquals("avatar.png", parsed.fileName)
        assertEquals(1024L, parsed.fileSizeBytesFrom)
        assertEquals(2048L, parsed.fileSizeBytesTo)
        assertEquals(listOf("image/png"), parsed.mimeTypes)
        assertEquals(listOf(StorageCategory.IMAGE, StorageCategory.VIDEO), parsed.categories)
        assertEquals(listOf(StorageCategory.AUDIO), parsed.excludeCategories)
        assertEquals(listOf(StorageFileVisibility.PUBLIC), parsed.visibilities)
        assertEquals(listOf(StorageFileVisibility.PRIVATE), parsed.excludeVisibilities)
        assertEquals(listOf(StorageFileStatus.READY), parsed.statuses)
        assertEquals(listOf(StorageFileStatus.PENDING_UPLOAD), parsed.excludeStatuses)
        assertEquals(listOf("avatar"), parsed.targetScopes)
        assertEquals(true, parsed.isTargetScopeSet)
        assertEquals("2026-01-01T00:00:00Z", parsed.createdAtFrom)
        assertEquals("2026-03-01T00:00:00Z", parsed.createdAtTo)
        assertEquals("2026-01-02T00:00:00Z", parsed.updatedAtFrom)
        assertEquals("2026-03-02T00:00:00Z", parsed.updatedAtTo)
        assertEquals(true, parsed.isUpdatedAtSet)
        assertEquals("avatar", parsed.searchQuery)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = ManagementStorageFileListParams.parse(emptyMap())
        assertEquals(ManagementStorageFileListParams(), parsed)
    }

    @Test
    fun `serialize and parse roundtrip maintains equality`() {
        val original =
            ManagementStorageFileListParams(
                pageNumber = 2,
                pageSize = 50,
                sortBy = StorageFileSortBy.FILE_NAME,
                sortOrder = SortOrder.ASC,
                fileIds = listOf("00000000-0000-4000-8000-000000000001"),
                ownerUserIds = listOf("usr_123"),
                isOwnerUserIdSet = true,
                fileName = "avatar.png",
                fileSizeBytesFrom = 1024L,
                fileSizeBytesTo = 2048L,
                mimeTypes = listOf("image/png"),
                categories = listOf(StorageCategory.IMAGE),
                excludeCategories = listOf(StorageCategory.AUDIO),
                visibilities = listOf(StorageFileVisibility.PUBLIC),
                excludeVisibilities = listOf(StorageFileVisibility.PRIVATE),
                statuses = listOf(StorageFileStatus.READY),
                excludeStatuses = listOf(StorageFileStatus.PENDING_UPLOAD),
                targetScopes = listOf("avatar"),
                isTargetScopeSet = true,
                createdAtFrom = "2026-01-01T00:00:00Z",
                createdAtTo = "2026-03-01T00:00:00Z",
                updatedAtFrom = "2026-01-02T00:00:00Z",
                updatedAtTo = "2026-03-02T00:00:00Z",
                isUpdatedAtSet = true,
                searchQuery = "avatar"
            )

        val serialized = ManagementStorageFileListParams.serialize(original)
        val restored = ManagementStorageFileListParams.parse(serialized)

        assertEquals(original, restored)
    }
}
