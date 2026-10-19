package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.management.session.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.userlist.ManagementUserSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

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
