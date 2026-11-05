package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierSortBy
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementGlobalIdentifierListParamsTest {
    @Test
    fun `parse extracts global identifier list params correctly`() {
        val params =
            mapOf(
                ListingParamNames.Pagination.PAGE_NUMBER to "1",
                ListingParamNames.Pagination.PAGE_SIZE to "50",
                ListingParamNames.Sort.SORT_BY to "updated_at",
                ListingParamNames.Sort.SORT_ORDER to "asc",
                UserIdentifierFilterValues.IDENTIFIER_ID to listOf("id_123"),
                UserIdentifierFilterValues.USER_ID to listOf("usr_123"),
                UserIdentifierFilterValues.USER_AUTH_PROVIDER to listOf("email", "phone"),
                UserIdentifierFilterValues.EXCLUDE_USER_AUTH_PROVIDER to listOf("google"),
                UserIdentifierFilterValues.IDENTIFIER to "admin@example.com",
                UserIdentifierFilterValues.DISPLAY_NAME to "Admin User",
                UserIdentifierFilterValues.EXTERNAL_PROVIDER_EMAIL to "ext@example.com",
                UserIdentifierFilterValues.IS_EXTERNAL_PROVIDER_EMAIL_SET to "true",
                UserIdentifierFilterValues.CREATED_AT_FROM to "2026-01-01T00:00:00Z",
                UserIdentifierFilterValues.CREATED_AT_TO to "2026-03-01T00:00:00Z",
                UserIdentifierFilterValues.UPDATED_AT_FROM to "2026-01-02T00:00:00Z",
                UserIdentifierFilterValues.UPDATED_AT_TO to "2026-03-02T00:00:00Z",
                UserIdentifierFilterValues.IS_UPDATED_AT_SET to "true",
                UserIdentifierFilterValues.SEARCH_QUERY to "admin"
            )
        val parsed = ManagementGlobalIdentifierListParams.parse(params)

        assertEquals(1, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(UserIdentifierSortBy.UPDATED_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf("id_123"), parsed.identifierIds)
        assertEquals(listOf("usr_123"), parsed.userIds)
        assertEquals(listOf(UserAuthProvider.EMAIL, UserAuthProvider.PHONE), parsed.userAuthProviders)
        assertEquals(listOf(UserAuthProvider.GOOGLE), parsed.excludeUserAuthProviders)
        assertEquals("admin@example.com", parsed.identifier)
        assertEquals("Admin User", parsed.displayName)
        assertEquals("ext@example.com", parsed.externalProviderEmail)
        assertEquals(true, parsed.isExternalProviderEmailSet)
        assertEquals("2026-01-01T00:00:00Z", parsed.createdAtFrom)
        assertEquals("2026-03-01T00:00:00Z", parsed.createdAtTo)
        assertEquals("2026-01-02T00:00:00Z", parsed.updatedAtFrom)
        assertEquals("2026-03-02T00:00:00Z", parsed.updatedAtTo)
        assertEquals(true, parsed.isUpdatedAtSet)
        assertEquals("admin", parsed.searchQuery)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = ManagementGlobalIdentifierListParams.parse(emptyMap())
        assertEquals(ManagementGlobalIdentifierListParams(), parsed)
    }

    @Test
    fun `serialize and parse roundtrip maintains equality`() {
        val original =
            ManagementGlobalIdentifierListParams(
                pageNumber = 2,
                pageSize = 50,
                sortBy = UserIdentifierSortBy.UPDATED_AT,
                sortOrder = SortOrder.ASC,
                identifierIds = listOf("id_123"),
                userIds = listOf("usr_123"),
                userAuthProviders = listOf(UserAuthProvider.EMAIL),
                excludeUserAuthProviders = listOf(UserAuthProvider.GOOGLE),
                identifier = "admin@example.com",
                displayName = "Admin User",
                externalProviderEmail = "ext@example.com",
                isExternalProviderEmailSet = true,
                createdAtFrom = "2026-01-01T00:00:00Z",
                createdAtTo = "2026-03-01T00:00:00Z",
                updatedAtFrom = "2026-01-02T00:00:00Z",
                updatedAtTo = "2026-03-02T00:00:00Z",
                isUpdatedAtSet = true,
                searchQuery = "admin"
            )
        val serialized = ManagementGlobalIdentifierListParams.serialize(original)
        val restored = ManagementGlobalIdentifierListParams.parse(serialized)

        assertEquals(original, restored)
    }
}
