package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementUserSessionListParamsTest {
    private companion object {
        const val TEST_USER_ID = "00000000-0000-4000-8000-000000000001"
    }

    @Test
    fun `parse extracts user session list params correctly`() {
        val params =
            mapOf(
                UserApiPaths.USER_ID to TEST_USER_ID,
                ListingParamNames.Pagination.PAGE_NUMBER to "1",
                ListingParamNames.Pagination.PAGE_SIZE to "20",
                UserSessionFilterValues.SESSION_ID to listOf("sess_123"),
                UserSessionFilterValues.USER_ROLE to listOf("user"),
                UserSessionFilterValues.EXCLUDE_USER_ROLE to listOf("staff"),
                UserSessionFilterValues.IDENTIFIER to "user@example.com",
                UserSessionFilterValues.IDENTIFIER_ID to listOf("id_123"),
                UserSessionFilterValues.IDENTIFIER_DISPLAY_NAME to "User",
                UserSessionFilterValues.IDENTIFIER_AUTH_PROVIDER to listOf("email"),
                UserSessionFilterValues.EXCLUDE_IDENTIFIER_AUTH_PROVIDER to listOf("google"),
                UserSessionFilterValues.CLIENT_TYPE to listOf("android"),
                UserSessionFilterValues.DEVICE_ID to "dev_123",
                UserSessionFilterValues.DEVICE_NAME to "Pixel 8",
                UserSessionFilterValues.APP_VERSION to "1.0.0",
                UserSessionFilterValues.OPERATION_SYSTEM_VERSION to "Android 14",
                UserSessionFilterValues.USER_AGENT to "Mozilla/5.0",
                UserSessionFilterValues.IS_USER_AGENT_SET to "true",
                UserSessionFilterValues.IP_ADDRESS to "127.0.0.1",
                UserSessionFilterValues.IS_IP_ADDRESS_SET to "true",
                UserSessionFilterValues.EXPIRES_AT_FROM to "2026-01-01T00:00:00Z",
                UserSessionFilterValues.EXPIRES_AT_TO to "2026-03-01T00:00:00Z",
                UserSessionFilterValues.LAST_ACCESSED_AT_FROM to "2026-01-02T00:00:00Z",
                UserSessionFilterValues.LAST_ACCESSED_AT_TO to "2026-03-02T00:00:00Z",
                UserSessionFilterValues.LAST_REAUTHENTICATED_AT_FROM to "2026-01-03T00:00:00Z",
                UserSessionFilterValues.LAST_REAUTHENTICATED_AT_TO to "2026-03-03T00:00:00Z",
                UserSessionFilterValues.CREATED_AT_FROM to "2026-01-04T00:00:00Z",
                UserSessionFilterValues.CREATED_AT_TO to "2026-03-04T00:00:00Z",
                UserSessionFilterValues.UPDATED_AT_FROM to "2026-01-05T00:00:00Z",
                UserSessionFilterValues.UPDATED_AT_TO to "2026-03-05T00:00:00Z",
                UserSessionFilterValues.IS_UPDATED_AT_SET to "true",
                UserSessionFilterValues.SEARCH_QUERY to "user"
            )
        val parsed = ManagementUserSessionListParams.parse(params)

        assertEquals(TEST_USER_ID, parsed.userId)
        assertEquals(TEST_USER_ID, parsed.typedUserId?.asHexDashString())
        assertEquals(1, parsed.pageNumber)
        assertEquals(20, parsed.pageSize)
        assertEquals(listOf("sess_123"), parsed.sessionIds)
        assertEquals(listOf(UserRole.USER), parsed.userRoles)
        assertEquals(listOf(UserRole.STAFF), parsed.excludeUserRoles)
        assertEquals("user@example.com", parsed.identifier)
        assertEquals(listOf("id_123"), parsed.identifierIds)
        assertEquals("User", parsed.identifierDisplayName)
        assertEquals(listOf(UserAuthProvider.EMAIL), parsed.identifierAuthProviders)
        assertEquals(listOf(UserAuthProvider.GOOGLE), parsed.excludeIdentifierAuthProviders)
        assertEquals(listOf(ClientType.ANDROID), parsed.clientTypes)
        assertEquals("dev_123", parsed.deviceId)
        assertEquals("Pixel 8", parsed.deviceName)
        assertEquals("1.0.0", parsed.appVersion)
        assertEquals("Android 14", parsed.osVersion)
        assertEquals("Mozilla/5.0", parsed.userAgent)
        assertEquals(true, parsed.isUserAgentSet)
        assertEquals("127.0.0.1", parsed.ipAddress)
        assertEquals(true, parsed.isIpAddressSet)
        assertEquals("2026-01-01T00:00:00Z", parsed.expiresAtFrom)
        assertEquals("2026-03-01T00:00:00Z", parsed.expiresAtTo)
        assertEquals("2026-01-02T00:00:00Z", parsed.lastAccessedAtFrom)
        assertEquals("2026-03-02T00:00:00Z", parsed.lastAccessedAtTo)
        assertEquals("2026-01-03T00:00:00Z", parsed.lastReauthenticatedAtFrom)
        assertEquals("2026-03-03T00:00:00Z", parsed.lastReauthenticatedAtTo)
        assertEquals("2026-01-04T00:00:00Z", parsed.createdAtFrom)
        assertEquals("2026-03-04T00:00:00Z", parsed.createdAtTo)
        assertEquals("2026-01-05T00:00:00Z", parsed.updatedAtFrom)
        assertEquals("2026-03-05T00:00:00Z", parsed.updatedAtTo)
        assertEquals(true, parsed.isUpdatedAtSet)
        assertEquals("user", parsed.searchQuery)
    }

    @Test
    fun `serialize and parse roundtrip maintains equality`() {
        val original =
            ManagementUserSessionListParams(
                userId = TEST_USER_ID,
                pageNumber = 2,
                pageSize = 50,
                sortBy = UserSessionSortBy.LAST_ACCESSED_AT,
                sortOrder = SortOrder.ASC,
                sessionIds = listOf("sess_123"),
                userRoles = listOf(UserRole.USER),
                excludeUserRoles = listOf(UserRole.STAFF),
                identifier = "user@example.com",
                identifierIds = listOf("id_123"),
                identifierDisplayName = "User",
                identifierAuthProviders = listOf(UserAuthProvider.EMAIL),
                excludeIdentifierAuthProviders = listOf(UserAuthProvider.GOOGLE),
                clientTypes = listOf(ClientType.ANDROID),
                deviceId = "dev_123",
                deviceName = "Pixel 8",
                appVersion = "1.0.0",
                osVersion = "Android 14",
                userAgent = "Mozilla/5.0",
                isUserAgentSet = true,
                ipAddress = "127.0.0.1",
                isIpAddressSet = true,
                expiresAtFrom = "2026-01-01T00:00:00Z",
                expiresAtTo = "2026-03-01T00:00:00Z",
                lastAccessedAtFrom = "2026-01-02T00:00:00Z",
                lastAccessedAtTo = "2026-03-02T00:00:00Z",
                lastReauthenticatedAtFrom = "2026-01-03T00:00:00Z",
                lastReauthenticatedAtTo = "2026-03-03T00:00:00Z",
                createdAtFrom = "2026-01-04T00:00:00Z",
                createdAtTo = "2026-03-04T00:00:00Z",
                updatedAtFrom = "2026-01-05T00:00:00Z",
                updatedAtTo = "2026-03-05T00:00:00Z",
                isUpdatedAtSet = true,
                searchQuery = "user"
            )
        val serialized = ManagementUserSessionListParams.serialize(original)
        val restored = ManagementUserSessionListParams.parse(serialized + (UserApiPaths.USER_ID to TEST_USER_ID))

        assertEquals(original, restored)
    }
}
