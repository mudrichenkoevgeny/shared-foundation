package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract.UserNavigationPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the self-service user identifiers list screen.
 *
 * User context is derived from the active session token; NO `userId` parameter is present.
 *
 * @property action Modal view action to trigger over the list screen; wire key is [UserNavigationPaths.ACTION].
 * @property pageNumber One-based page index; wire key is [ListingParamNames.Pagination.PAGE_NUMBER].
 * @property pageSize Maximum items returned per page; wire key is [ListingParamNames.Pagination.PAGE_SIZE].
 * @property sortBy Field to sort listing results by; wire key is [ListingParamNames.Sort.SORT_BY].
 * @property sortOrder Direction of sorting; wire key is [ListingParamNames.Sort.SORT_ORDER].
 * @property userAuthProviders List of [UserAuthProvider] items to filter by; wire key is [UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER].
 * @property identifiers List of free-text identifier strings to filter by; wire key is [UserFilterValues.UserIdentifierFilterValues.IDENTIFIER].
 */
@Serializable
data class SelfIdentifierListParams(
    val action: SelfIdentifierListAction? = null,
    val pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    val pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    val sortBy: UserIdentifierSortBy = UserIdentifierSortBy.DEFAULT,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val userAuthProviders: List<UserAuthProvider> = emptyList(),
    val identifiers: List<String> = emptyList()
) : NavigationScreenParams<SelfIdentifierListParams> {

    override val parser: NavigationScreenParamsParser<SelfIdentifierListParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<SelfIdentifierListParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [SelfIdentifierListParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [SelfIdentifierListParams] instance.
         */
        override fun parse(params: Map<String, Any?>): SelfIdentifierListParams {
            val normalized = params.normalizeQueryParams()

            val action = normalized[UserNavigationPaths.ACTION]
                ?.firstOrNull()
                ?.let { SelfIdentifierListAction.fromValueOrNull(it) }

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

            val userAuthProviders = normalized[UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER]
                ?.mapNotNull { UserAuthProvider.fromValueOrNull(it) }
                ?: emptyList()

            val identifiers = normalized[UserFilterValues.UserIdentifierFilterValues.IDENTIFIER]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            return SelfIdentifierListParams(
                action = action,
                pageNumber = pageNumber,
                pageSize = pageSize,
                sortBy = sortBy,
                sortOrder = sortOrder,
                userAuthProviders = userAuthProviders,
                identifiers = identifiers
            )
        }

        /**
         * Serializes [SelfIdentifierListParams] into a map of raw URL parameters.
         *
         * @param params Typed [SelfIdentifierListParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: SelfIdentifierListParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()

            if (params.action != null) {
                result[UserNavigationPaths.ACTION] = listOf(params.action.value)
            }
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
