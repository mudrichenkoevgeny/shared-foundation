package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.management.identifier.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.globallist.ManagementGlobalIdentifierListParams

fun createTestManagementGlobalIdentifierListParams(
    pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    sortBy: UserIdentifierSortBy = UserIdentifierSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    userIds: List<String> = emptyList(),
    userAuthProviders: List<UserAuthProvider> = emptyList(),
    identifiers: List<String> = emptyList()
): ManagementGlobalIdentifierListParams = ManagementGlobalIdentifierListParams(
    pageNumber = pageNumber,
    pageSize = pageSize,
    sortBy = sortBy,
    sortOrder = sortOrder,
    userIds = userIds,
    userAuthProviders = userAuthProviders,
    identifiers = identifiers
)
