package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.management.session.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.userlist.ManagementUserSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [ManagementUserSessionListParams] navigation params instance.
 *
 * @param userId Owning user ID string.
 * @param pageNumber 1-based page index.
 * @param pageSize Number of items per page.
 * @param sortBy Sort field ([UserSessionSortBy]).
 * @param sortOrder Sort direction ([SortOrder]).
 * @param userRoles Filter access roles list ([UserRole]).
 * @param identifiers Filter identifiers list.
 * @param identifierIds Filter credential IDs list.
 * @param userAuthProviders Filter provider list ([UserAuthProvider]).
 * @param clientTypes Filter client platform types list ([ClientType]).
 * @param userAgents Filter User-Agent strings list.
 * @param ipAddresses Filter IP addresses list.
 * @param languages Filter preferred language codes list.
 * @param deviceIds Filter device IDs list.
 * @param deviceNames Filter device names list.
 * @param appVersions Filter app versions list.
 * @param osVersions Filter operating system versions list.
 */
fun createTestManagementUserSessionListParams(
    userId: String = TEST_USER_ID,
    pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    sortBy: UserSessionSortBy = UserSessionSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    userRoles: List<UserRole> = emptyList(),
    identifiers: List<String> = emptyList(),
    identifierIds: List<String> = emptyList(),
    userAuthProviders: List<UserAuthProvider> = emptyList(),
    clientTypes: List<ClientType> = emptyList(),
    userAgents: List<String> = emptyList(),
    ipAddresses: List<String> = emptyList(),
    languages: List<String> = emptyList(),
    deviceIds: List<String> = emptyList(),
    deviceNames: List<String> = emptyList(),
    appVersions: List<String> = emptyList(),
    osVersions: List<String> = emptyList()
): ManagementUserSessionListParams = ManagementUserSessionListParams(
    userId = userId,
    pageNumber = pageNumber,
    pageSize = pageSize,
    sortBy = sortBy,
    sortOrder = sortOrder,
    userRoles = userRoles,
    identifiers = identifiers,
    identifierIds = identifierIds,
    userAuthProviders = userAuthProviders,
    clientTypes = clientTypes,
    userAgents = userAgents,
    ipAddresses = ipAddresses,
    languages = languages,
    deviceIds = deviceIds,
    deviceNames = deviceNames,
    appVersions = appVersions,
    osVersions = osVersions
)
