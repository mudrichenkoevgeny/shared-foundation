package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.management.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageSortValues.StorageFileSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.status.StorageFileStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.visibility.StorageFileVisibility
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the management global storage file list screen and API query.
 *
 * @property pageNumber One-based page index; wire key is [ListingParamNames.Pagination.PAGE_NUMBER].
 * @property pageSize Maximum items returned per page; wire key is [ListingParamNames.Pagination.PAGE_SIZE].
 * @property sortBy Field to sort listing results by; wire key is [ListingParamNames.Sort.SORT_BY].
 * @property sortOrder Direction of sorting; wire key is [ListingParamNames.Sort.SORT_ORDER].
 * @property fileIds List of specific file IDs to filter by; wire key is [StorageFilterValues.StorageFileFilterValues.FILE_ID].
 * @property ownerUserIds List of specific owner user IDs to filter by; wire key is [StorageFilterValues.StorageFileFilterValues.OWNER_USER_ID].
 * @property fileName Substring match for original file name; wire key is [StorageFilterValues.StorageFileFilterValues.FILE_NAME].
 * @property mimeTypes List of MIME type substrings to filter by; wire key is [StorageFilterValues.StorageFileFilterValues.MIME_TYPE].
 * @property categories List of [StorageCategory] items to filter by; wire key is [StorageFilterValues.StorageFileFilterValues.CATEGORY].
 * @property visibilities List of [StorageFileVisibility] items to filter by; wire key is [StorageFilterValues.StorageFileFilterValues.VISIBILITY].
 * @property statuses List of [StorageFileStatus] items to filter by; wire key is [StorageFilterValues.StorageFileFilterValues.STATUS].
 * @property targetScopes List of target scopes to filter by; wire key is [StorageFilterValues.StorageFileFilterValues.TARGET_SCOPE].
 */
@Serializable
data class ManagementStorageFileListParams(
    val pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    val pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    val sortBy: StorageFileSortBy = StorageFileSortBy.DEFAULT,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val fileIds: List<String> = emptyList(),
    val ownerUserIds: List<String> = emptyList(),
    val fileName: String? = null,
    val mimeTypes: List<String> = emptyList(),
    val categories: List<StorageCategory> = emptyList(),
    val visibilities: List<StorageFileVisibility> = emptyList(),
    val statuses: List<StorageFileStatus> = emptyList(),
    val targetScopes: List<String> = emptyList()
) : NavigationScreenParams<ManagementStorageFileListParams> {

    override val parser: NavigationScreenParamsParser<ManagementStorageFileListParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<ManagementStorageFileListParams> {

        override fun parse(params: Map<String, Any?>): ManagementStorageFileListParams {
            val normalized = params.normalizeQueryParams()

            val pageNumber = normalized[ListingParamNames.Pagination.PAGE_NUMBER]?.firstOrNull()?.toIntOrNull()
                ?.takeIf { it > 0 } ?: ListingDefaultValues.Pagination.PAGE_NUMBER
            val pageSize = normalized[ListingParamNames.Pagination.PAGE_SIZE]?.firstOrNull()?.toIntOrNull()
                ?.takeIf { it > 0 } ?: ListingDefaultValues.Pagination.PAGE_SIZE
            val sortBy = normalized[ListingParamNames.Sort.SORT_BY]?.firstOrNull()
                ?.let { StorageFileSortBy.fromValueOrNull(it) } ?: StorageFileSortBy.DEFAULT
            val sortOrder = normalized[ListingParamNames.Sort.SORT_ORDER]?.firstOrNull()
                ?.let { SortOrder.fromValueOrNull(it) } ?: SortOrder.DEFAULT

            val fileIds = normalized[StorageFilterValues.StorageFileFilterValues.FILE_ID] ?: emptyList()
            val ownerUserIds = normalized[StorageFilterValues.StorageFileFilterValues.OWNER_USER_ID] ?: emptyList()
            val fileName = normalized[StorageFilterValues.StorageFileFilterValues.FILE_NAME]?.firstOrNull()?.trim()
            val mimeTypes = normalized[StorageFilterValues.StorageFileFilterValues.MIME_TYPE] ?: emptyList()
            val categories = normalized[StorageFilterValues.StorageFileFilterValues.CATEGORY]
                ?.mapNotNull { StorageCategory.fromValueOrNull(it) } ?: emptyList()
            val visibilities = normalized[StorageFilterValues.StorageFileFilterValues.VISIBILITY]
                ?.mapNotNull { StorageFileVisibility.fromValueOrNull(it) } ?: emptyList()
            val statuses = normalized[StorageFilterValues.StorageFileFilterValues.STATUS]
                ?.mapNotNull { StorageFileStatus.fromValueOrNull(it) } ?: emptyList()
            val targetScopes = normalized[StorageFilterValues.StorageFileFilterValues.TARGET_SCOPE] ?: emptyList()

            return ManagementStorageFileListParams(
                pageNumber = pageNumber,
                pageSize = pageSize,
                sortBy = sortBy,
                sortOrder = sortOrder,
                fileIds = fileIds,
                ownerUserIds = ownerUserIds,
                fileName = fileName,
                mimeTypes = mimeTypes,
                categories = categories,
                visibilities = visibilities,
                statuses = statuses,
                targetScopes = targetScopes
            )
        }

        override fun serialize(params: ManagementStorageFileListParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()

            if (params.pageNumber != ListingDefaultValues.Pagination.PAGE_NUMBER) {
                result[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.PAGE_SIZE) {
                result[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != StorageFileSortBy.DEFAULT) {
                result[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                result[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }

            if (params.fileIds.isNotEmpty()) {
                result[StorageFilterValues.StorageFileFilterValues.FILE_ID] = params.fileIds
            }
            if (params.ownerUserIds.isNotEmpty()) {
                result[StorageFilterValues.StorageFileFilterValues.OWNER_USER_ID] = params.ownerUserIds
            }
            if (!params.fileName.isNullOrBlank()) {
                result[StorageFilterValues.StorageFileFilterValues.FILE_NAME] = listOf(params.fileName.trim())
            }
            if (params.mimeTypes.isNotEmpty()) {
                result[StorageFilterValues.StorageFileFilterValues.MIME_TYPE] = params.mimeTypes
            }
            if (params.categories.isNotEmpty()) {
                result[StorageFilterValues.StorageFileFilterValues.CATEGORY] = params.categories.map { it.value }
            }
            if (params.visibilities.isNotEmpty()) {
                result[StorageFilterValues.StorageFileFilterValues.VISIBILITY] = params.visibilities.map { it.value }
            }
            if (params.statuses.isNotEmpty()) {
                result[StorageFilterValues.StorageFileFilterValues.STATUS] = params.statuses.map { it.value }
            }
            if (params.targetScopes.isNotEmpty()) {
                result[StorageFilterValues.StorageFileFilterValues.TARGET_SCOPE] = params.targetScopes
            }

            return result
        }
    }
}
