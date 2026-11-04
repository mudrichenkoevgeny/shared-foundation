package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.management.identifier.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.userlist.ManagementUserIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [ManagementUserIdentifierListParams] navigation params instance.
 *
 * @param userId Owning user ID string.
 * @param pageNumber One-based page index.
 * @param pageSize Maximum items per page.
 * @param sortBy Sort field ([UserIdentifierSortBy]).
 * @param sortOrder Sort direction ([SortOrder]).
 * @param identifierIds Specific identifier IDs filter list.
 * @param userAuthProviders Filter provider list ([UserAuthProvider]).
 * @param excludeUserAuthProviders Excluded provider list ([UserAuthProvider]).
 * @param identifier Exact raw or masked login identifier string.
 * @param displayName Exact display name.
 * @param externalProviderEmail Exact external provider email.
 * @param isExternalProviderEmailSet Filter by external provider email presence.
 * @param createdAtFrom Lower bound creation timestamp.
 * @param createdAtTo Upper bound creation timestamp.
 * @param updatedAtFrom Lower bound update timestamp.
 * @param updatedAtTo Upper bound update timestamp.
 * @param isUpdatedAtSet Filter by update presence.
 * @param searchQuery Free-text search query string.
 */
fun createTestManagementUserIdentifierListParams(
    userId: String = TEST_USER_ID,
    pageNumber: Int = ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
    sortBy: UserIdentifierSortBy = UserIdentifierSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    identifierIds: List<String> = emptyList(),
    userAuthProviders: List<UserAuthProvider> = emptyList(),
    excludeUserAuthProviders: List<UserAuthProvider> = emptyList(),
    identifier: String? = null,
    displayName: String? = null,
    externalProviderEmail: String? = null,
    isExternalProviderEmailSet: Boolean? = null,
    createdAtFrom: String? = null,
    createdAtTo: String? = null,
    updatedAtFrom: String? = null,
    updatedAtTo: String? = null,
    isUpdatedAtSet: Boolean? = null,
    searchQuery: String? = null
): ManagementUserIdentifierListParams = ManagementUserIdentifierListParams(
    userId = userId,
    pageNumber = pageNumber,
    pageSize = pageSize,
    sortBy = sortBy,
    sortOrder = sortOrder,
    identifierIds = identifierIds,
    userAuthProviders = userAuthProviders,
    excludeUserAuthProviders = excludeUserAuthProviders,
    identifier = identifier,
    displayName = displayName,
    externalProviderEmail = externalProviderEmail,
    isExternalProviderEmailSet = isExternalProviderEmailSet,
    createdAtFrom = createdAtFrom,
    createdAtTo = createdAtTo,
    updatedAtFrom = updatedAtFrom,
    updatedAtTo = updatedAtTo,
    isUpdatedAtSet = isUpdatedAtSet,
    searchQuery = searchQuery
)
