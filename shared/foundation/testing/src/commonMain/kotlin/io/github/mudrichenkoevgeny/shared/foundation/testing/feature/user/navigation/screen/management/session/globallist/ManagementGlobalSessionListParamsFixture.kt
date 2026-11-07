package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.management.session.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.globallist.ManagementGlobalSessionListParams

/**
 * Creates a test [ManagementGlobalSessionListParams] navigation params instance.
 *
 * @param pageNumber One-based page index.
 * @param pageSize Maximum items per page.
 * @param sortBy Sort field ([UserSessionSortBy]).
 * @param sortOrder Sort direction ([SortOrder]).
 * @param sessionIds Specific session IDs filter list.
 * @param userIds Filter user IDs list.
 * @param userRoles Filter access roles list ([UserRole]).
 * @param excludeUserRoles Exclude access roles list ([UserRole]).
 * @param identifier Exact raw or masked login identifier string.
 * @param identifierIds Filter credential IDs list.
 * @param identifierDisplayName Exact identifier display name.
 * @param identifierAuthProviders Filter provider list ([UserAuthProvider]).
 * @param excludeIdentifierAuthProviders Exclude provider list ([UserAuthProvider]).
 * @param clientTypes Filter client platform types list ([ClientType]).
 * @param excludeClientTypes Exclude client platform types list ([ClientType]).
 * @param isClientTypeSet Filter by client type presence.
 * @param deviceId Exact stable device ID.
 * @param isDeviceIdSet Filter by device ID presence.
 * @param deviceName Exact device name.
 * @param isDeviceNameSet Filter by device name presence.
 * @param language Primary BCP 47 language code.
 * @param isLanguageSet Filter by language presence.
 * @param appVersion Exact application version.
 * @param isAppVersionSet Filter by app version presence.
 * @param osVersion Exact operating system version.
 * @param isOperationSystemVersionSet Filter by OS version presence.
 * @param userAgent Exact User-Agent string.
 * @param isUserAgentSet Filter by User-Agent presence.
 * @param ipAddress Exact IP address.
 * @param isIpAddressSet Filter by IP address presence.
 * @param expiresAtFrom Lower bound expiration timestamp.
 * @param expiresAtTo Upper bound expiration timestamp.
 * @param lastAccessedAtFrom Lower bound last accessed timestamp.
 * @param lastAccessedAtTo Upper bound last accessed timestamp.
 * @param lastReauthenticatedAtFrom Lower bound re-authentication timestamp.
 * @param lastReauthenticatedAtTo Upper bound re-authentication timestamp.
 * @param createdAtFrom Lower bound creation timestamp.
 * @param createdAtTo Upper bound creation timestamp.
 * @param updatedAtFrom Lower bound update timestamp.
 * @param updatedAtTo Upper bound update timestamp.
 * @param isUpdatedAtSet Filter by update presence.
 * @param searchQuery Free-text search query string.
 */
public fun createTestManagementGlobalSessionListParams(
    pageNumber: Int = ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
    sortBy: UserSessionSortBy = UserSessionSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    sessionIds: List<String> = emptyList(),
    userIds: List<String> = emptyList(),
    userRoles: List<UserRole> = emptyList(),
    excludeUserRoles: List<UserRole> = emptyList(),
    identifier: String? = null,
    identifierIds: List<String> = emptyList(),
    identifierDisplayName: String? = null,
    identifierAuthProviders: List<UserAuthProvider> = emptyList(),
    excludeIdentifierAuthProviders: List<UserAuthProvider> = emptyList(),
    clientTypes: List<ClientType> = emptyList(),
    excludeClientTypes: List<ClientType> = emptyList(),
    isClientTypeSet: Boolean? = null,
    deviceId: String? = null,
    isDeviceIdSet: Boolean? = null,
    deviceName: String? = null,
    isDeviceNameSet: Boolean? = null,
    language: String? = null,
    isLanguageSet: Boolean? = null,
    appVersion: String? = null,
    isAppVersionSet: Boolean? = null,
    osVersion: String? = null,
    isOperationSystemVersionSet: Boolean? = null,
    userAgent: String? = null,
    isUserAgentSet: Boolean? = null,
    ipAddress: String? = null,
    isIpAddressSet: Boolean? = null,
    expiresAtFrom: String? = null,
    expiresAtTo: String? = null,
    lastAccessedAtFrom: String? = null,
    lastAccessedAtTo: String? = null,
    lastReauthenticatedAtFrom: String? = null,
    lastReauthenticatedAtTo: String? = null,
    createdAtFrom: String? = null,
    createdAtTo: String? = null,
    updatedAtFrom: String? = null,
    updatedAtTo: String? = null,
    isUpdatedAtSet: Boolean? = null,
    searchQuery: String? = null
): ManagementGlobalSessionListParams =
    ManagementGlobalSessionListParams(
        pageNumber = pageNumber,
        pageSize = pageSize,
        sortBy = sortBy,
        sortOrder = sortOrder,
        sessionIds = sessionIds,
        userIds = userIds,
        userRoles = userRoles,
        excludeUserRoles = excludeUserRoles,
        identifier = identifier,
        identifierIds = identifierIds,
        identifierDisplayName = identifierDisplayName,
        identifierAuthProviders = identifierAuthProviders,
        excludeIdentifierAuthProviders = excludeIdentifierAuthProviders,
        clientTypes = clientTypes,
        excludeClientTypes = excludeClientTypes,
        isClientTypeSet = isClientTypeSet,
        deviceId = deviceId,
        isDeviceIdSet = isDeviceIdSet,
        deviceName = deviceName,
        isDeviceNameSet = isDeviceNameSet,
        language = language,
        isLanguageSet = isLanguageSet,
        appVersion = appVersion,
        isAppVersionSet = isAppVersionSet,
        osVersion = osVersion,
        isOperationSystemVersionSet = isOperationSystemVersionSet,
        userAgent = userAgent,
        isUserAgentSet = isUserAgentSet,
        ipAddress = ipAddress,
        isIpAddressSet = isIpAddressSet,
        expiresAtFrom = expiresAtFrom,
        expiresAtTo = expiresAtTo,
        lastAccessedAtFrom = lastAccessedAtFrom,
        lastAccessedAtTo = lastAccessedAtTo,
        lastReauthenticatedAtFrom = lastReauthenticatedAtFrom,
        lastReauthenticatedAtTo = lastReauthenticatedAtTo,
        createdAtFrom = createdAtFrom,
        createdAtTo = createdAtTo,
        updatedAtFrom = updatedAtFrom,
        updatedAtTo = updatedAtTo,
        isUpdatedAtSet = isUpdatedAtSet,
        searchQuery = searchQuery
    )
