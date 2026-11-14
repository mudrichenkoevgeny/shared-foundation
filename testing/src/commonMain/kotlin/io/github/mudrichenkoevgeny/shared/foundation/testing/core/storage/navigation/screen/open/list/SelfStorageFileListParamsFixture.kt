package io.github.mudrichenkoevgeny.shared.foundation.testing.core.storage.navigation.screen.open.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.open.list.SelfStorageFileListParams

/**
 * Creates a test [SelfStorageFileListParams] instance.
 *
 * @param pageNumber One-based page index.
 * @param pageSize Maximum items per page.
 * @param sortBy Sort field ([StorageFileSortBy]).
 * @param sortOrder Sort direction ([SortOrder]).
 * @param fileIds Specific file IDs filter list.
 * @param fileName Substring match for file name.
 * @param fileSizeBytesFrom Lower bound for file size in bytes.
 * @param fileSizeBytesTo Upper bound for file size in bytes.
 * @param mimeTypes MIME type filter list.
 * @param categories Categories filter list ([StorageCategory]).
 * @param excludeCategories Exclude categories filter list ([StorageCategory]).
 * @param visibilities Visibilities filter list ([StorageFileVisibility]).
 * @param excludeVisibilities Exclude visibilities filter list ([StorageFileVisibility]).
 * @param statuses Statuses filter list ([StorageFileStatus]).
 * @param excludeStatuses Exclude statuses filter list ([StorageFileStatus]).
 * @param targetScopes Target scopes filter list.
 * @param isTargetScopeSet Filter by target scope presence.
 * @param createdAtFrom Lower bound creation timestamp.
 * @param createdAtTo Upper bound creation timestamp.
 * @param updatedAtFrom Lower bound update timestamp.
 * @param updatedAtTo Upper bound update timestamp.
 * @param isUpdatedAtSet Filter by update presence.
 * @param searchQuery Free-text search query string.
 */
public fun createTestSelfStorageFileListParams(
    pageNumber: Int = ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
    sortBy: StorageFileSortBy = StorageFileSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    fileIds: List<String> = emptyList(),
    fileName: String? = null,
    fileSizeBytesFrom: Long? = null,
    fileSizeBytesTo: Long? = null,
    mimeTypes: List<String> = emptyList(),
    categories: List<StorageCategory> = emptyList(),
    excludeCategories: List<StorageCategory> = emptyList(),
    visibilities: List<StorageFileVisibility> = emptyList(),
    excludeVisibilities: List<StorageFileVisibility> = emptyList(),
    statuses: List<StorageFileStatus> = emptyList(),
    excludeStatuses: List<StorageFileStatus> = listOf(StorageFileStatus.DELETED),
    targetScopes: List<String> = emptyList(),
    isTargetScopeSet: Boolean? = null,
    createdAtFrom: String? = null,
    createdAtTo: String? = null,
    updatedAtFrom: String? = null,
    updatedAtTo: String? = null,
    isUpdatedAtSet: Boolean? = null,
    searchQuery: String? = null
): SelfStorageFileListParams = SelfStorageFileListParams(
    pageNumber = pageNumber,
    pageSize = pageSize,
    sortBy = sortBy,
    sortOrder = sortOrder,
    fileIds = fileIds,
    fileName = fileName,
    fileSizeBytesFrom = fileSizeBytesFrom,
    fileSizeBytesTo = fileSizeBytesTo,
    mimeTypes = mimeTypes,
    categories = categories,
    excludeCategories = excludeCategories,
    visibilities = visibilities,
    excludeVisibilities = excludeVisibilities,
    statuses = statuses,
    excludeStatuses = excludeStatuses,
    targetScopes = targetScopes,
    isTargetScopeSet = isTargetScopeSet,
    createdAtFrom = createdAtFrom,
    createdAtTo = createdAtTo,
    updatedAtFrom = updatedAtFrom,
    updatedAtTo = updatedAtTo,
    isUpdatedAtSet = isUpdatedAtSet,
    searchQuery = searchQuery
)
