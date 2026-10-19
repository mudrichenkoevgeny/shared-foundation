package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.management.user.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist.ManagementUserListParams

fun createTestManagementUserListParams(
    pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    sortBy: UserSortBy = UserSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    userRoles: List<UserRole> = emptyList(),
    accountStatuses: List<UserAccountStatus> = emptyList(),
    accountStatusesOnRestore: List<UserAccountStatus> = emptyList(),
    accountLockoutTypes: List<AccountLockoutType> = emptyList(),
    authorityLevelFrom: Int? = null,
    authorityLevelTo: Int? = null,
    isTotpEnabled: Boolean? = null,
    permissionCodes: List<String> = emptyList()
): ManagementUserListParams = ManagementUserListParams(
    pageNumber = pageNumber,
    pageSize = pageSize,
    sortBy = sortBy,
    sortOrder = sortOrder,
    userRoles = userRoles,
    accountStatuses = accountStatuses,
    accountStatusesOnRestore = accountStatusesOnRestore,
    accountLockoutTypes = accountLockoutTypes,
    authorityLevelFrom = authorityLevelFrom,
    authorityLevelTo = authorityLevelTo,
    isTotpEnabled = isTotpEnabled,
    permissionCodes = permissionCodes
)
