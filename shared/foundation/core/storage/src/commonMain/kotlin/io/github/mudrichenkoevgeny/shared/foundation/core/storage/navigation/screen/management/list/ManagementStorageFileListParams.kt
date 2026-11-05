package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.management.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.category.StorageCategory
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.listing.StorageFileSortBy
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
 * @property fileIds Specific file IDs to filter by; wire key is [StorageFileFilterValues.FILE_ID].
 * @property ownerUserIds Owner user IDs to filter by; wire key is [StorageFileFilterValues.OWNER_USER_ID].
 * @property isOwnerUserIdSet Filter by owner presence; wire key is [StorageFileFilterValues.IS_OWNER_USER_ID_SET].
 * @property fileName Substring match for original file name; wire key is [StorageFileFilterValues.FILE_NAME].
 * @property fileSizeBytesFrom Lower bound for file size in bytes; wire key is [StorageFileFilterValues.FILE_SIZE_BYTES_FROM].
 * @property fileSizeBytesTo Upper bound for file size in bytes; wire key is [StorageFileFilterValues.FILE_SIZE_BYTES_TO].
 * @property mimeTypes MIME type filters; wire key is [StorageFileFilterValues.MIME_TYPE].
 * @property categories List of [StorageCategory] items to filter by; wire key is [StorageFileFilterValues.CATEGORY].
 * @property excludeCategories List of [StorageCategory] items to exclude; wire key is [StorageFileFilterValues.EXCLUDE_CATEGORY].
 * @property visibilities List of [StorageFileVisibility] items to filter by; wire key is [StorageFileFilterValues.VISIBILITY].
 * @property excludeVisibilities Excluded [StorageFileVisibility] items; wire key is [StorageFileFilterValues.EXCLUDE_VISIBILITY].
 * @property statuses List of [StorageFileStatus] items to filter by; wire key is [StorageFileFilterValues.STATUS].
 * @property excludeStatuses List of [StorageFileStatus] items to exclude; wire key is [StorageFileFilterValues.EXCLUDE_STATUS].
 * @property targetScopes Target scopes to filter by; wire key is [StorageFileFilterValues.TARGET_SCOPE].
 * @property isTargetScopeSet Filter by target scope presence; wire key is [StorageFileFilterValues.IS_TARGET_SCOPE_SET].
 * @property createdAtFrom Lower bound creation timestamp; wire key is [StorageFileFilterValues.CREATED_AT_FROM].
 * @property createdAtTo Upper bound creation timestamp; wire key is [StorageFileFilterValues.CREATED_AT_TO].
 * @property updatedAtFrom Lower bound update timestamp; wire key is [StorageFileFilterValues.UPDATED_AT_FROM].
 * @property updatedAtTo Upper bound update timestamp; wire key is [StorageFileFilterValues.UPDATED_AT_TO].
 * @property isUpdatedAtSet Filter by update presence; wire key is [StorageFileFilterValues.IS_UPDATED_AT_SET].
 * @property searchQuery Search query targeting file name; wire key is [StorageFileFilterValues.SEARCH_QUERY].
 */
@Serializable
public data class ManagementStorageFileListParams(
    public val pageNumber: Int = ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
    public val pageSize: Int = ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
    public val sortBy: StorageFileSortBy = StorageFileSortBy.DEFAULT,
    public val sortOrder: SortOrder = SortOrder.DEFAULT,
    public val fileIds: List<String> = emptyList(),
    public val ownerUserIds: List<String> = emptyList(),
    public val isOwnerUserIdSet: Boolean? = null,
    public val fileName: String? = null,
    public val fileSizeBytesFrom: Long? = null,
    public val fileSizeBytesTo: Long? = null,
    public val mimeTypes: List<String> = emptyList(),
    public val categories: List<StorageCategory> = emptyList(),
    public val excludeCategories: List<StorageCategory> = emptyList(),
    public val visibilities: List<StorageFileVisibility> = emptyList(),
    public val excludeVisibilities: List<StorageFileVisibility> = emptyList(),
    public val statuses: List<StorageFileStatus> = emptyList(),
    public val excludeStatuses: List<StorageFileStatus> = listOf(StorageFileStatus.DELETED),
    public val targetScopes: List<String> = emptyList(),
    public val isTargetScopeSet: Boolean? = null,
    public val createdAtFrom: String? = null,
    public val createdAtTo: String? = null,
    public val updatedAtFrom: String? = null,
    public val updatedAtTo: String? = null,
    public val isUpdatedAtSet: Boolean? = null,
    public val searchQuery: String? = null
) : NavigationScreenParams<ManagementStorageFileListParams> {
    override val parser: NavigationScreenParamsParser<ManagementStorageFileListParams>
        get() = Companion

    public companion object : NavigationScreenParamsParser<ManagementStorageFileListParams> {
        /**
         * Parses raw URL query parameters into typed [ManagementStorageFileListParams].
         *
         * Accepts both canonical `Map<String, List<String>>` and flexible `Map<String, Any?>`
         * (e.g. single strings or arrays from JS/TS query objects).
         *
         * @param params Map of query parameter keys to raw parameter values (single strings, lists, arrays, or nulls).
         * @return Validated [ManagementStorageFileListParams] instance with defaults applied.
         */
        override fun parse(params: Map<String, Any?>): ManagementStorageFileListParams {
            val normalized = params.normalizeQueryParams()

            val pageNumber =
                normalized[ListingParamNames.Pagination.PAGE_NUMBER]
                    ?.firstOrNull()
                    ?.toIntOrNull()
                    ?.takeIf { it > 0 }
                    ?: ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER

            val pageSize =
                normalized[ListingParamNames.Pagination.PAGE_SIZE]
                    ?.firstOrNull()
                    ?.toIntOrNull()
                    ?.takeIf { it > 0 }
                    ?: ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE

            val sortBy =
                normalized[ListingParamNames.Sort.SORT_BY]
                    ?.firstOrNull()
                    ?.let { StorageFileSortBy.fromValueOrNull(it) }
                    ?: StorageFileSortBy.DEFAULT

            val sortOrder =
                normalized[ListingParamNames.Sort.SORT_ORDER]
                    ?.firstOrNull()
                    ?.let { SortOrder.fromValueOrNull(it) }
                    ?: SortOrder.DEFAULT

            val fileIds =
                normalized[StorageFileFilterValues.FILE_ID]
                    ?.filter { it.isNotBlank() }
                    ?: emptyList()

            val ownerUserIds =
                normalized[StorageFileFilterValues.OWNER_USER_ID]
                    ?.filter { it.isNotBlank() }
                    ?: emptyList()

            val isOwnerUserIdSet =
                normalized[StorageFileFilterValues.IS_OWNER_USER_ID_SET]
                    ?.firstOrNull()
                    ?.toBooleanStrictOrNull()

            val fileName =
                normalized[StorageFileFilterValues.FILE_NAME]
                    ?.firstOrNull()
                    ?.takeIf { it.isNotBlank() }

            val fileSizeBytesFrom =
                normalized[StorageFileFilterValues.FILE_SIZE_BYTES_FROM]
                    ?.firstOrNull()
                    ?.toLongOrNull()

            val fileSizeBytesTo =
                normalized[StorageFileFilterValues.FILE_SIZE_BYTES_TO]
                    ?.firstOrNull()
                    ?.toLongOrNull()

            val mimeTypes =
                normalized[StorageFileFilterValues.MIME_TYPE]
                    ?.filter { it.isNotBlank() }
                    ?: emptyList()

            val categories =
                normalized[StorageFileFilterValues.CATEGORY]
                    ?.mapNotNull { StorageCategory.fromValueOrNull(it) }
                    ?: emptyList()

            val excludeCategories =
                normalized[StorageFileFilterValues.EXCLUDE_CATEGORY]
                    ?.mapNotNull { StorageCategory.fromValueOrNull(it) }
                    ?: emptyList()

            val visibilities =
                normalized[StorageFileFilterValues.VISIBILITY]
                    ?.mapNotNull { StorageFileVisibility.fromValueOrNull(it) }
                    ?: emptyList()

            val excludeVisibilities =
                normalized[StorageFileFilterValues.EXCLUDE_VISIBILITY]
                    ?.mapNotNull { StorageFileVisibility.fromValueOrNull(it) }
                    ?: emptyList()

            val statuses =
                normalized[StorageFileFilterValues.STATUS]
                    ?.mapNotNull { StorageFileStatus.fromValueOrNull(it) }
                    ?: emptyList()

            val excludeStatuses =
                normalized[StorageFileFilterValues.EXCLUDE_STATUS]
                    ?.mapNotNull { StorageFileStatus.fromValueOrNull(it) }
                    ?: listOf(StorageFileStatus.DELETED)

            val targetScopes =
                normalized[StorageFileFilterValues.TARGET_SCOPE]
                    ?.filter { it.isNotBlank() }
                    ?: emptyList()

            val isTargetScopeSet =
                normalized[StorageFileFilterValues.IS_TARGET_SCOPE_SET]
                    ?.firstOrNull()
                    ?.toBooleanStrictOrNull()

            val createdAtFrom =
                normalized[StorageFileFilterValues.CREATED_AT_FROM]
                    ?.firstOrNull()
                    ?.takeIf { it.isNotBlank() }

            val createdAtTo =
                normalized[StorageFileFilterValues.CREATED_AT_TO]
                    ?.firstOrNull()
                    ?.takeIf { it.isNotBlank() }

            val updatedAtFrom =
                normalized[StorageFileFilterValues.UPDATED_AT_FROM]
                    ?.firstOrNull()
                    ?.takeIf { it.isNotBlank() }

            val updatedAtTo =
                normalized[StorageFileFilterValues.UPDATED_AT_TO]
                    ?.firstOrNull()
                    ?.takeIf { it.isNotBlank() }

            val isUpdatedAtSet =
                normalized[StorageFileFilterValues.IS_UPDATED_AT_SET]
                    ?.firstOrNull()
                    ?.toBooleanStrictOrNull()

            val searchQuery =
                normalized[StorageFileFilterValues.SEARCH_QUERY]
                    ?.firstOrNull()
                    ?.takeIf { it.isNotBlank() }

            return ManagementStorageFileListParams(
                pageNumber = pageNumber,
                pageSize = pageSize,
                sortBy = sortBy,
                sortOrder = sortOrder,
                fileIds = fileIds,
                ownerUserIds = ownerUserIds,
                isOwnerUserIdSet = isOwnerUserIdSet,
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
        }

        /**
         * Serializes [ManagementStorageFileListParams] into a map of raw URL query parameter strings.
         *
         * @param params Typed [ManagementStorageFileListParams] to serialize.
         * @return Map of query parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: ManagementStorageFileListParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()

            if (params.pageNumber != ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER) {
                result[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE) {
                result[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != StorageFileSortBy.DEFAULT) {
                result[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                result[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }
            if (params.fileIds.isNotEmpty()) {
                result[StorageFileFilterValues.FILE_ID] = params.fileIds
            }
            if (params.ownerUserIds.isNotEmpty()) {
                result[StorageFileFilterValues.OWNER_USER_ID] = params.ownerUserIds
            }
            if (params.isOwnerUserIdSet != null) {
                result[StorageFileFilterValues.IS_OWNER_USER_ID_SET] = listOf(params.isOwnerUserIdSet.toString())
            }
            if (!params.fileName.isNullOrBlank()) {
                result[StorageFileFilterValues.FILE_NAME] = listOf(params.fileName.trim())
            }
            if (params.fileSizeBytesFrom != null) {
                result[StorageFileFilterValues.FILE_SIZE_BYTES_FROM] = listOf(params.fileSizeBytesFrom.toString())
            }
            if (params.fileSizeBytesTo != null) {
                result[StorageFileFilterValues.FILE_SIZE_BYTES_TO] = listOf(params.fileSizeBytesTo.toString())
            }
            if (params.mimeTypes.isNotEmpty()) {
                result[StorageFileFilterValues.MIME_TYPE] = params.mimeTypes
            }
            if (params.categories.isNotEmpty()) {
                result[StorageFileFilterValues.CATEGORY] = params.categories.map { it.value }
            }
            if (params.excludeCategories.isNotEmpty()) {
                result[StorageFileFilterValues.EXCLUDE_CATEGORY] = params.excludeCategories.map { it.value }
            }
            if (params.visibilities.isNotEmpty()) {
                result[StorageFileFilterValues.VISIBILITY] = params.visibilities.map { it.value }
            }
            if (params.excludeVisibilities.isNotEmpty()) {
                result[StorageFileFilterValues.EXCLUDE_VISIBILITY] = params.excludeVisibilities.map { it.value }
            }
            if (params.statuses.isNotEmpty()) {
                result[StorageFileFilterValues.STATUS] = params.statuses.map { it.value }
            }
            if (params.excludeStatuses.isNotEmpty()) {
                result[StorageFileFilterValues.EXCLUDE_STATUS] = params.excludeStatuses.map { it.value }
            }
            if (params.targetScopes.isNotEmpty()) {
                result[StorageFileFilterValues.TARGET_SCOPE] = params.targetScopes
            }
            if (params.isTargetScopeSet != null) {
                result[StorageFileFilterValues.IS_TARGET_SCOPE_SET] = listOf(params.isTargetScopeSet.toString())
            }
            if (params.createdAtFrom != null) {
                result[StorageFileFilterValues.CREATED_AT_FROM] = listOf(params.createdAtFrom)
            }
            if (params.createdAtTo != null) {
                result[StorageFileFilterValues.CREATED_AT_TO] = listOf(params.createdAtTo)
            }
            if (params.updatedAtFrom != null) {
                result[StorageFileFilterValues.UPDATED_AT_FROM] = listOf(params.updatedAtFrom)
            }
            if (params.updatedAtTo != null) {
                result[StorageFileFilterValues.UPDATED_AT_TO] = listOf(params.updatedAtTo)
            }
            if (params.isUpdatedAtSet != null) {
                result[StorageFileFilterValues.IS_UPDATED_AT_SET] = listOf(params.isUpdatedAtSet.toString())
            }
            if (params.searchQuery != null) {
                result[StorageFileFilterValues.SEARCH_QUERY] = listOf(params.searchQuery)
            }

            return result
        }
    }
}
