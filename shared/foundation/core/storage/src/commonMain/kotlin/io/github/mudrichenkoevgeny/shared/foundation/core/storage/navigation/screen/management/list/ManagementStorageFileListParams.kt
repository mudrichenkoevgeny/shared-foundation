package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.screen.management.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseBoolean
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseEnumList
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseLong
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseNonBlankString
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseStringList
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
            return ManagementStorageFileListParams(
                pageNumber =
                    normalized[ListingParamNames.Pagination.PAGE_NUMBER]
                        ?.firstOrNull()
                        ?.toIntOrNull()
                        ?.takeIf { it > 0 }
                        ?: ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
                pageSize =
                    normalized[ListingParamNames.Pagination.PAGE_SIZE]
                        ?.firstOrNull()
                        ?.toIntOrNull()
                        ?.takeIf { it > 0 }
                        ?: ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
                sortBy =
                    normalized[ListingParamNames.Sort.SORT_BY]
                        ?.firstOrNull()
                        ?.let { StorageFileSortBy.fromValueOrNull(it) }
                        ?: StorageFileSortBy.DEFAULT,
                sortOrder =
                    normalized[ListingParamNames.Sort.SORT_ORDER]
                        ?.firstOrNull()
                        ?.let { SortOrder.fromValueOrNull(it) }
                        ?: SortOrder.DEFAULT,
                fileIds = normalized.parseStringList(StorageFileFilterValues.FILE_ID),
                ownerUserIds = normalized.parseStringList(StorageFileFilterValues.OWNER_USER_ID),
                isOwnerUserIdSet = normalized.parseBoolean(StorageFileFilterValues.IS_OWNER_USER_ID_SET),
                fileName = normalized.parseNonBlankString(StorageFileFilterValues.FILE_NAME),
                fileSizeBytesFrom = normalized.parseLong(StorageFileFilterValues.FILE_SIZE_BYTES_FROM),
                fileSizeBytesTo = normalized.parseLong(StorageFileFilterValues.FILE_SIZE_BYTES_TO),
                mimeTypes = normalized.parseStringList(StorageFileFilterValues.MIME_TYPE),
                categories = normalized.parseEnumList(StorageFileFilterValues.CATEGORY) { StorageCategory.fromValueOrNull(it) },
                excludeCategories = normalized.parseEnumList(StorageFileFilterValues.EXCLUDE_CATEGORY) { StorageCategory.fromValueOrNull(it) },
                visibilities = normalized.parseEnumList(StorageFileFilterValues.VISIBILITY) { StorageFileVisibility.fromValueOrNull(it) },
                excludeVisibilities = normalized.parseEnumList(StorageFileFilterValues.EXCLUDE_VISIBILITY) { StorageFileVisibility.fromValueOrNull(it) },
                statuses = normalized.parseEnumList(StorageFileFilterValues.STATUS) { StorageFileStatus.fromValueOrNull(it) },
                excludeStatuses = normalized.parseEnumList(StorageFileFilterValues.EXCLUDE_STATUS) { StorageFileStatus.fromValueOrNull(it) }
                    .ifEmpty { listOf(StorageFileStatus.DELETED) },
                targetScopes = normalized.parseStringList(StorageFileFilterValues.TARGET_SCOPE),
                isTargetScopeSet = normalized.parseBoolean(StorageFileFilterValues.IS_TARGET_SCOPE_SET),
                createdAtFrom = normalized.parseNonBlankString(StorageFileFilterValues.CREATED_AT_FROM),
                createdAtTo = normalized.parseNonBlankString(StorageFileFilterValues.CREATED_AT_TO),
                updatedAtFrom = normalized.parseNonBlankString(StorageFileFilterValues.UPDATED_AT_FROM),
                updatedAtTo = normalized.parseNonBlankString(StorageFileFilterValues.UPDATED_AT_TO),
                isUpdatedAtSet = normalized.parseBoolean(StorageFileFilterValues.IS_UPDATED_AT_SET),
                searchQuery = normalized.parseNonBlankString(StorageFileFilterValues.SEARCH_QUERY)
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
            result.serializePaginationAndSorting(params)
            result.serializeCoreAndMetadataFilters(params)
            result.serializeCategoryStatusAndScopeFilters(params)
            return result
        }

        private fun MutableMap<String, List<String>>.serializePaginationAndSorting(
            params: ManagementStorageFileListParams
        ) {
            if (params.pageNumber != ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER) {
                this[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE) {
                this[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != StorageFileSortBy.DEFAULT) {
                this[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                this[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }
        }

        private fun MutableMap<String, List<String>>.serializeCoreAndMetadataFilters(
            params: ManagementStorageFileListParams
        ) {
            if (params.fileIds.isNotEmpty()) {
                this[StorageFileFilterValues.FILE_ID] = params.fileIds
            }
            if (params.ownerUserIds.isNotEmpty()) {
                this[StorageFileFilterValues.OWNER_USER_ID] = params.ownerUserIds
            }
            if (params.isOwnerUserIdSet != null) {
                this[StorageFileFilterValues.IS_OWNER_USER_ID_SET] = listOf(params.isOwnerUserIdSet.toString())
            }
            if (!params.fileName.isNullOrBlank()) {
                this[StorageFileFilterValues.FILE_NAME] = listOf(params.fileName.trim())
            }
            if (params.fileSizeBytesFrom != null) {
                this[StorageFileFilterValues.FILE_SIZE_BYTES_FROM] = listOf(params.fileSizeBytesFrom.toString())
            }
            if (params.fileSizeBytesTo != null) {
                this[StorageFileFilterValues.FILE_SIZE_BYTES_TO] = listOf(params.fileSizeBytesTo.toString())
            }
            if (params.mimeTypes.isNotEmpty()) {
                this[StorageFileFilterValues.MIME_TYPE] = params.mimeTypes
            }
            if (params.createdAtFrom != null) {
                this[StorageFileFilterValues.CREATED_AT_FROM] = listOf(params.createdAtFrom)
            }
            if (params.createdAtTo != null) {
                this[StorageFileFilterValues.CREATED_AT_TO] = listOf(params.createdAtTo)
            }
            if (params.updatedAtFrom != null) {
                this[StorageFileFilterValues.UPDATED_AT_FROM] = listOf(params.updatedAtFrom)
            }
            if (params.updatedAtTo != null) {
                this[StorageFileFilterValues.UPDATED_AT_TO] = listOf(params.updatedAtTo)
            }
            if (params.isUpdatedAtSet != null) {
                this[StorageFileFilterValues.IS_UPDATED_AT_SET] = listOf(params.isUpdatedAtSet.toString())
            }
            if (params.searchQuery != null) {
                this[StorageFileFilterValues.SEARCH_QUERY] = listOf(params.searchQuery)
            }
        }

        private fun MutableMap<String, List<String>>.serializeCategoryStatusAndScopeFilters(
            params: ManagementStorageFileListParams
        ) {
            if (params.categories.isNotEmpty()) {
                this[StorageFileFilterValues.CATEGORY] = params.categories.map { it.value }
            }
            if (params.excludeCategories.isNotEmpty()) {
                this[StorageFileFilterValues.EXCLUDE_CATEGORY] = params.excludeCategories.map { it.value }
            }
            if (params.visibilities.isNotEmpty()) {
                this[StorageFileFilterValues.VISIBILITY] = params.visibilities.map { it.value }
            }
            if (params.excludeVisibilities.isNotEmpty()) {
                this[StorageFileFilterValues.EXCLUDE_VISIBILITY] = params.excludeVisibilities.map { it.value }
            }
            if (params.statuses.isNotEmpty()) {
                this[StorageFileFilterValues.STATUS] = params.statuses.map { it.value }
            }
            if (params.excludeStatuses.isNotEmpty()) {
                this[StorageFileFilterValues.EXCLUDE_STATUS] = params.excludeStatuses.map { it.value }
            }
            if (params.targetScopes.isNotEmpty()) {
                this[StorageFileFilterValues.TARGET_SCOPE] = params.targetScopes
            }
            if (params.isTargetScopeSet != null) {
                this[StorageFileFilterValues.IS_TARGET_SCOPE_SET] = listOf(params.isTargetScopeSet.toString())
            }
        }
    }
}
