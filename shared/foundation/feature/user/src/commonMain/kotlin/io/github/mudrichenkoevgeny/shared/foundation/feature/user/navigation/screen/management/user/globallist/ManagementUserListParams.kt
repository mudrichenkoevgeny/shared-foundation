package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the global management user list screen and API query.
 *
 * @property pageNumber One-based page index; wire key is [ListingParamNames.Pagination.PAGE_NUMBER].
 * @property pageSize Maximum items returned per page; wire key is [ListingParamNames.Pagination.PAGE_SIZE].
 * @property sortBy Field to sort listing results by; wire key is [ListingParamNames.Sort.SORT_BY].
 * @property sortOrder Direction of sorting; wire key is [ListingParamNames.Sort.SORT_ORDER].
 * @property userRoles List of [UserRole] items to filter by; wire key is [UserFilterValues.UserFilterValues.ROLE].
 * @property accountStatuses List of [UserAccountStatus] items to filter by; wire key is [UserFilterValues.UserFilterValues.ACCOUNT_STATUS].
 * @property accountStatusesOnRestore List of target [UserAccountStatus] items assigned on restore; wire key is [UserFilterValues.UserFilterValues.ACCOUNT_STATUS_ON_RESTORE].
 * @property accountLockoutTypes List of [AccountLockoutType] items to filter by; wire key is [UserFilterValues.UserFilterValues.ACCOUNT_LOCKOUT_TYPE].
 * @property authorityLevelFrom Inclusive lower bound for authority level; wire key is [UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_FROM].
 * @property authorityLevelTo Inclusive upper bound for authority level; wire key is [UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_TO].
 * @property isTotpEnabled Filter by TOTP status; wire key is [UserFilterValues.UserFilterValues.IS_TOTP_ENABLED].
 * @property permissionCodes List of permission code strings to filter by; wire key is [UserFilterValues.UserFilterValues.PERMISSION_CODES].
 */
@Serializable
data class ManagementUserListParams(
    val pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    val pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    val sortBy: UserSortBy = UserSortBy.DEFAULT,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val userRoles: List<UserRole> = emptyList(),
    val accountStatuses: List<UserAccountStatus> = emptyList(),
    val accountStatusesOnRestore: List<UserAccountStatus> = emptyList(),
    val accountLockoutTypes: List<AccountLockoutType> = emptyList(),
    val authorityLevelFrom: Int? = null,
    val authorityLevelTo: Int? = null,
    val isTotpEnabled: Boolean? = null,
    val permissionCodes: List<String> = emptyList()
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        ManagementUserListParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): ManagementUserListParams =
            ManagementUserListParamsParser.parse(params)
    }
}
