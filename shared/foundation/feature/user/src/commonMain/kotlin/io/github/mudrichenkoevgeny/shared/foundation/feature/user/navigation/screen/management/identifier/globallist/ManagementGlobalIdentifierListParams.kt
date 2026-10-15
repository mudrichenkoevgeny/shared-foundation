package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserIdentifierSortBy
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for system-wide user credentials/identifiers management list.
 *
 * @property pageNumber One-based page index; wire key is [ListingParamNames.Pagination.PAGE_NUMBER].
 * @property pageSize Maximum items returned per page; wire key is [ListingParamNames.Pagination.PAGE_SIZE].
 * @property sortBy Field to sort listing results by; wire key is [ListingParamNames.Sort.SORT_BY].
 * @property sortOrder Direction of sorting; wire key is [ListingParamNames.Sort.SORT_ORDER].
 * @property userIds List of user IDs to filter by; wire key is [UserFilterValues.UserIdentifierFilterValues.USER_ID].
 * @property userAuthProviders List of [UserAuthProvider] items to filter by; wire key is [UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER].
 * @property identifiers List of free-text identifier strings to filter by; wire key is [UserFilterValues.UserIdentifierFilterValues.IDENTIFIER].
 */
@Serializable
data class ManagementGlobalIdentifierListParams(
    val pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    val pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    val sortBy: UserIdentifierSortBy = UserIdentifierSortBy.DEFAULT,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val userIds: List<String> = emptyList(),
    val userAuthProviders: List<UserAuthProvider> = emptyList(),
    val identifiers: List<String> = emptyList()
) : NavigationScreenParams<ManagementGlobalIdentifierListParams> {

    override val parser: NavigationScreenParamsParser<ManagementGlobalIdentifierListParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<ManagementGlobalIdentifierListParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [ManagementGlobalIdentifierListParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [ManagementGlobalIdentifierListParams] instance.
         */
        override fun parse(params: Map<String, Any?>): ManagementGlobalIdentifierListParams {
            val normalized = params.normalizeQueryParams()

            val pageNumber = normalized[ListingParamNames.Pagination.PAGE_NUMBER]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it > 0 }
                ?: ListingDefaultValues.Pagination.PAGE_NUMBER

            val pageSize = normalized[ListingParamNames.Pagination.PAGE_SIZE]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it > 0 }
                ?: ListingDefaultValues.Pagination.PAGE_SIZE

            val sortBy = normalized[ListingParamNames.Sort.SORT_BY]
                ?.firstOrNull()
                ?.let { UserIdentifierSortBy.fromValueOrNull(it) }
                ?: UserIdentifierSortBy.DEFAULT

            val sortOrder = normalized[ListingParamNames.Sort.SORT_ORDER]
                ?.firstOrNull()
                ?.let { SortOrder.fromValueOrNull(it) }
                ?: SortOrder.DEFAULT

            val userIds = normalized[UserFilterValues.UserIdentifierFilterValues.USER_ID]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val userAuthProviders = normalized[UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER]
                ?.mapNotNull { UserAuthProvider.fromValueOrNull(it) }
                ?: emptyList()

            val identifiers = normalized[UserFilterValues.UserIdentifierFilterValues.IDENTIFIER]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            return ManagementGlobalIdentifierListParams(
                pageNumber = pageNumber,
                pageSize = pageSize,
                sortBy = sortBy,
                sortOrder = sortOrder,
                userIds = userIds,
                userAuthProviders = userAuthProviders,
                identifiers = identifiers
            )
        }

        /**
         * Serializes [ManagementGlobalIdentifierListParams] into a map of raw URL parameters.
         *
         * @param params Typed [ManagementGlobalIdentifierListParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: ManagementGlobalIdentifierListParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()

            if (params.pageNumber != ListingDefaultValues.Pagination.PAGE_NUMBER) {
                result[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.PAGE_SIZE) {
                result[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != UserIdentifierSortBy.DEFAULT) {
                result[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                result[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }
            if (params.userIds.isNotEmpty()) {
                result[UserFilterValues.UserIdentifierFilterValues.USER_ID] = params.userIds
            }
            if (params.userAuthProviders.isNotEmpty()) {
                result[UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER] = params.userAuthProviders.map { it.value }
            }
            if (params.identifiers.isNotEmpty()) {
                result[UserFilterValues.UserIdentifierFilterValues.IDENTIFIER] = params.identifiers
            }

            return result
        }
    }
}
