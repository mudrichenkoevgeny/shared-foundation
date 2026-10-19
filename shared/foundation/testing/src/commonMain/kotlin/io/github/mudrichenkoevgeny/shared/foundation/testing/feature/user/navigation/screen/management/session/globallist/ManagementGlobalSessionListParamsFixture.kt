package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.management.session.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.globallist.ManagementGlobalSessionListParams

fun createTestManagementGlobalSessionListParams(
    pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    sortBy: UserSessionSortBy = UserSessionSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    userIds: List<String> = emptyList(),
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
): ManagementGlobalSessionListParams = ManagementGlobalSessionListParams(
    pageNumber = pageNumber,
    pageSize = pageSize,
    sortBy = sortBy,
    sortOrder = sortOrder,
    userIds = userIds,
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
