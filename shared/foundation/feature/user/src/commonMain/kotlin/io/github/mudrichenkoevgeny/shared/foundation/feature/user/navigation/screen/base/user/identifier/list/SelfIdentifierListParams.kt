package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseBoolean
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseEnumList
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseNonBlankString
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseStringList
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierSortBy
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
 * @property identifierIds Specific identifier IDs to filter by; wire key is [UserIdentifierFilterValues.IDENTIFIER_ID].
 * @property userAuthProviders List of [UserAuthProvider] items to filter by; wire key is [UserIdentifierFilterValues.USER_AUTH_PROVIDER].
 * @property excludeUserAuthProviders Excluded [UserAuthProvider] items; wire key is [UserIdentifierFilterValues.EXCLUDE_USER_AUTH_PROVIDER].
 * @property identifier Exact raw or masked login identifier string; wire key is [UserIdentifierFilterValues.IDENTIFIER].
 * @property displayName Exact display name; wire key is [UserIdentifierFilterValues.DISPLAY_NAME].
 * @property externalProviderEmail Exact external provider email; wire key is [UserIdentifierFilterValues.EXTERNAL_PROVIDER_EMAIL].
 * @property isExternalProviderEmailSet Filter by external provider email presence; wire key is [UserIdentifierFilterValues.IS_EXTERNAL_PROVIDER_EMAIL_SET].
 * @property createdAtFrom Lower bound creation timestamp; wire key is [UserIdentifierFilterValues.CREATED_AT_FROM].
 * @property createdAtTo Upper bound creation timestamp; wire key is [UserIdentifierFilterValues.CREATED_AT_TO].
 * @property updatedAtFrom Lower bound update timestamp; wire key is [UserIdentifierFilterValues.UPDATED_AT_FROM].
 * @property updatedAtTo Upper bound update timestamp; wire key is [UserIdentifierFilterValues.UPDATED_AT_TO].
 * @property isUpdatedAtSet Filter by update presence; wire key is [UserIdentifierFilterValues.IS_UPDATED_AT_SET].
 * @property searchQuery Search query targeting identifier fields; wire key is [UserIdentifierFilterValues.SEARCH_QUERY].
 */
@Serializable
public data class SelfIdentifierListParams(
    public val action: SelfIdentifierListAction? = null,
    public val pageNumber: Int = ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
    public val pageSize: Int = ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
    public val sortBy: UserIdentifierSortBy = UserIdentifierSortBy.DEFAULT,
    public val sortOrder: SortOrder = SortOrder.DEFAULT,
    public val identifierIds: List<String> = emptyList(),
    public val userAuthProviders: List<UserAuthProvider> = emptyList(),
    public val excludeUserAuthProviders: List<UserAuthProvider> = emptyList(),
    public val identifier: String? = null,
    public val displayName: String? = null,
    public val externalProviderEmail: String? = null,
    public val isExternalProviderEmailSet: Boolean? = null,
    public val createdAtFrom: String? = null,
    public val createdAtTo: String? = null,
    public val updatedAtFrom: String? = null,
    public val updatedAtTo: String? = null,
    public val isUpdatedAtSet: Boolean? = null,
    public val searchQuery: String? = null
) : NavigationScreenParams<SelfIdentifierListParams> {
    override val parser: NavigationScreenParamsParser<SelfIdentifierListParams>
        get() = Companion

    public companion object : NavigationScreenParamsParser<SelfIdentifierListParams> {
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
                ?: ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER

            val pageSize = normalized[ListingParamNames.Pagination.PAGE_SIZE]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it > 0 }
                ?: ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE

            val sortBy = normalized[ListingParamNames.Sort.SORT_BY]
                ?.firstOrNull()
                ?.let { UserIdentifierSortBy.fromValueOrNull(it) }
                ?: UserIdentifierSortBy.DEFAULT

            val sortOrder = normalized[ListingParamNames.Sort.SORT_ORDER]
                ?.firstOrNull()
                ?.let { SortOrder.fromValueOrNull(it) }
                ?: SortOrder.DEFAULT

            return SelfIdentifierListParams(
                action = action,
                pageNumber = pageNumber,
                pageSize = pageSize,
                sortBy = sortBy,
                sortOrder = sortOrder,
                identifierIds = normalized.parseStringList(UserIdentifierFilterValues.IDENTIFIER_ID),
                userAuthProviders = normalized.parseEnumList(
                    UserIdentifierFilterValues.USER_AUTH_PROVIDER
                ) { UserAuthProvider.fromValueOrNull(it) },
                excludeUserAuthProviders = normalized.parseEnumList(
                    UserIdentifierFilterValues.EXCLUDE_USER_AUTH_PROVIDER
                ) { UserAuthProvider.fromValueOrNull(it) },
                identifier = normalized.parseNonBlankString(UserIdentifierFilterValues.IDENTIFIER),
                displayName = normalized.parseNonBlankString(UserIdentifierFilterValues.DISPLAY_NAME),
                externalProviderEmail = normalized.parseNonBlankString(
                    UserIdentifierFilterValues.EXTERNAL_PROVIDER_EMAIL
                ),
                isExternalProviderEmailSet = normalized.parseBoolean(
                    UserIdentifierFilterValues.IS_EXTERNAL_PROVIDER_EMAIL_SET
                ),
                createdAtFrom = normalized.parseNonBlankString(UserIdentifierFilterValues.CREATED_AT_FROM),
                createdAtTo = normalized.parseNonBlankString(UserIdentifierFilterValues.CREATED_AT_TO),
                updatedAtFrom = normalized.parseNonBlankString(UserIdentifierFilterValues.UPDATED_AT_FROM),
                updatedAtTo = normalized.parseNonBlankString(UserIdentifierFilterValues.UPDATED_AT_TO),
                isUpdatedAtSet = normalized.parseBoolean(UserIdentifierFilterValues.IS_UPDATED_AT_SET),
                searchQuery = normalized.parseNonBlankString(UserIdentifierFilterValues.SEARCH_QUERY)
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
            if (params.pageNumber != ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER) {
                result[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE) {
                result[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != UserIdentifierSortBy.DEFAULT) {
                result[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                result[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }
            if (params.identifierIds.isNotEmpty()) {
                result[UserIdentifierFilterValues.IDENTIFIER_ID] = params.identifierIds
            }
            if (params.userAuthProviders.isNotEmpty()) {
                result[UserIdentifierFilterValues.USER_AUTH_PROVIDER] = params.userAuthProviders.map { it.value }
            }
            if (params.excludeUserAuthProviders.isNotEmpty()) {
                result[UserIdentifierFilterValues.EXCLUDE_USER_AUTH_PROVIDER] =
                    params.excludeUserAuthProviders.map { it.value }
            }
            if (!params.identifier.isNullOrBlank()) {
                result[UserIdentifierFilterValues.IDENTIFIER] = listOf(params.identifier.trim())
            }
            if (!params.displayName.isNullOrBlank()) {
                result[UserIdentifierFilterValues.DISPLAY_NAME] = listOf(params.displayName.trim())
            }
            if (!params.externalProviderEmail.isNullOrBlank()) {
                result[UserIdentifierFilterValues.EXTERNAL_PROVIDER_EMAIL] =
                    listOf(params.externalProviderEmail.trim())
            }
            if (params.isExternalProviderEmailSet != null) {
                result[UserIdentifierFilterValues.IS_EXTERNAL_PROVIDER_EMAIL_SET] =
                    listOf(params.isExternalProviderEmailSet.toString())
            }
            if (params.createdAtFrom != null) {
                result[UserIdentifierFilterValues.CREATED_AT_FROM] = listOf(params.createdAtFrom)
            }
            if (params.createdAtTo != null) {
                result[UserIdentifierFilterValues.CREATED_AT_TO] = listOf(params.createdAtTo)
            }
            if (params.updatedAtFrom != null) {
                result[UserIdentifierFilterValues.UPDATED_AT_FROM] = listOf(params.updatedAtFrom)
            }
            if (params.updatedAtTo != null) {
                result[UserIdentifierFilterValues.UPDATED_AT_TO] = listOf(params.updatedAtTo)
            }
            if (params.isUpdatedAtSet != null) {
                result[UserIdentifierFilterValues.IS_UPDATED_AT_SET] = listOf(params.isUpdatedAtSet.toString())
            }
            if (params.searchQuery != null) {
                result[UserIdentifierFilterValues.SEARCH_QUERY] = listOf(params.searchQuery)
            }

            return result
        }
    }
}
