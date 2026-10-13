package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
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
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        ManagementGlobalIdentifierListParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): ManagementGlobalIdentifierListParams =
            ManagementGlobalIdentifierListParamsParser.parse(params)
    }
}
