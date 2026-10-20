package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.management.identifier.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.userlist.ManagementUserIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [ManagementUserIdentifierListParams] navigation params instance.
 *
 * @param userId Owning user ID string.
 * @param pageNumber 1-based page index.
 * @param pageSize Number of items per page.
 * @param sortBy Sort field ([UserIdentifierSortBy]).
 * @param sortOrder Sort direction ([SortOrder]).
 * @param userAuthProviders Filter provider list ([UserAuthProvider]).
 * @param identifiers Filter identifiers list.
 */
fun createTestManagementUserIdentifierListParams(
    userId: String = TEST_USER_ID,
    pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    sortBy: UserIdentifierSortBy = UserIdentifierSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    userAuthProviders: List<UserAuthProvider> = emptyList(),
    identifiers: List<String> = emptyList()
): ManagementUserIdentifierListParams = ManagementUserIdentifierListParams(
    userId = userId,
    pageNumber = pageNumber,
    pageSize = pageSize,
    sortBy = sortBy,
    sortOrder = sortOrder,
    userAuthProviders = userAuthProviders,
    identifiers = identifiers
)
