package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionSortBy
import kotlin.test.Test
import kotlin.test.assertEquals

class SelfSessionListParamsTest {
    @Test
    fun `parse extracts self session list params correctly`() {
        val params =
            mapOf(
                ListingParamNames.Pagination.PAGE_NUMBER to "2",
                ListingParamNames.Pagination.PAGE_SIZE to "50",
                ListingParamNames.Sort.SORT_BY to "last_accessed_at",
                ListingParamNames.Sort.SORT_ORDER to "asc",
                UserSessionFilterValues.SESSION_ID to listOf("sess_123"),
                UserSessionFilterValues.IDENTIFIER to "test@example.com",
                UserSessionFilterValues.IDENTIFIER_ID to listOf("id_123"),
                UserSessionFilterValues.IDENTIFIER_DISPLAY_NAME to "Test",
                UserSessionFilterValues.IDENTIFIER_AUTH_PROVIDER to listOf("email", "google"),
                UserSessionFilterValues.EXCLUDE_IDENTIFIER_AUTH_PROVIDER to listOf("phone"),
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
                UserSessionFilterValues.SEARCH_QUERY to "test"
            )
        val parsed = SelfSessionListParams.parse(params)

        assertEquals(2, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(UserSessionSortBy.LAST_ACCESSED_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf("sess_123"), parsed.sessionIds)
        assertEquals("test@example.com", parsed.identifier)
        assertEquals(listOf("id_123"), parsed.identifierIds)
        assertEquals("Test", parsed.identifierDisplayName)
        assertEquals(listOf(UserAuthProvider.EMAIL, UserAuthProvider.GOOGLE), parsed.identifierAuthProviders)
        assertEquals(listOf(UserAuthProvider.PHONE), parsed.excludeIdentifierAuthProviders)
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
        assertEquals("test", parsed.searchQuery)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = SelfSessionListParams.parse(emptyMap())
        assertEquals(SelfSessionListParams(), parsed)
    }

    @Test
    fun `serialize and parse roundtrip maintains equality`() {
        val original =
            SelfSessionListParams(
                pageNumber = 2,
                pageSize = 50,
                sortBy = UserSessionSortBy.LAST_ACCESSED_AT,
                sortOrder = SortOrder.ASC,
                sessionIds = listOf("sess_123"),
                identifier = "test@example.com",
                identifierIds = listOf("id_123"),
                identifierDisplayName = "Test",
                identifierAuthProviders = listOf(UserAuthProvider.EMAIL),
                excludeIdentifierAuthProviders = listOf(UserAuthProvider.PHONE),
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
                searchQuery = "test"
            )

        val serialized = SelfSessionListParams.serialize(original)
        val restored = SelfSessionListParams.parse(serialized)

        assertEquals(original, restored)
    }
}
