package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.user.identifier.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list.SelfIdentifierListAction
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list.SelfIdentifierListParams

/**
 * Creates a test [SelfIdentifierListParams] navigation params instance.
 *
 * @param action Trigger action ([SelfIdentifierListAction]).
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
public fun createTestSelfIdentifierListParams(
    action: SelfIdentifierListAction? = null,
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
): SelfIdentifierListParams = SelfIdentifierListParams(
    action = action,
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
