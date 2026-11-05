package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierSortBy
import kotlin.test.Test
import kotlin.test.assertEquals

class SelfIdentifierListParamsTest {
    @Test
    fun `parse extracts self identifier list params correctly`() {
        val params =
            mapOf(
                ListingParamNames.Pagination.PAGE_NUMBER to "2",
                ListingParamNames.Pagination.PAGE_SIZE to "50",
                ListingParamNames.Sort.SORT_BY to "updated_at",
                ListingParamNames.Sort.SORT_ORDER to "asc",
                UserIdentifierFilterValues.IDENTIFIER_ID to listOf("id_123"),
                UserIdentifierFilterValues.USER_AUTH_PROVIDER to listOf("email", "google"),
                UserIdentifierFilterValues.EXCLUDE_USER_AUTH_PROVIDER to listOf("phone"),
                UserIdentifierFilterValues.IDENTIFIER to "test@example.com",
                UserIdentifierFilterValues.DISPLAY_NAME to "Test User",
                UserIdentifierFilterValues.EXTERNAL_PROVIDER_EMAIL to "ext@example.com",
                UserIdentifierFilterValues.IS_EXTERNAL_PROVIDER_EMAIL_SET to "true",
                UserIdentifierFilterValues.CREATED_AT_FROM to "2026-01-01T00:00:00Z",
                UserIdentifierFilterValues.CREATED_AT_TO to "2026-03-01T00:00:00Z",
                UserIdentifierFilterValues.UPDATED_AT_FROM to "2026-01-02T00:00:00Z",
                UserIdentifierFilterValues.UPDATED_AT_TO to "2026-03-02T00:00:00Z",
                UserIdentifierFilterValues.IS_UPDATED_AT_SET to "true",
                UserIdentifierFilterValues.SEARCH_QUERY to "test"
            )
        val parsed = SelfIdentifierListParams.parse(params)

        assertEquals(2, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(UserIdentifierSortBy.UPDATED_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf("id_123"), parsed.identifierIds)
        assertEquals(listOf(UserAuthProvider.EMAIL, UserAuthProvider.GOOGLE), parsed.userAuthProviders)
        assertEquals(listOf(UserAuthProvider.PHONE), parsed.excludeUserAuthProviders)
        assertEquals("test@example.com", parsed.identifier)
        assertEquals("Test User", parsed.displayName)
        assertEquals("ext@example.com", parsed.externalProviderEmail)
        assertEquals(true, parsed.isExternalProviderEmailSet)
        assertEquals("2026-01-01T00:00:00Z", parsed.createdAtFrom)
        assertEquals("2026-03-01T00:00:00Z", parsed.createdAtTo)
        assertEquals("2026-01-02T00:00:00Z", parsed.updatedAtFrom)
        assertEquals("2026-03-02T00:00:00Z", parsed.updatedAtTo)
        assertEquals(true, parsed.isUpdatedAtSet)
        assertEquals("test", parsed.searchQuery)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = SelfIdentifierListParams.parse(emptyMap())
        assertEquals(SelfIdentifierListParams(), parsed)
    }

    @Test
    fun `serialize and parse roundtrip maintains equality`() {
        val original =
            SelfIdentifierListParams(
                pageNumber = 2,
                pageSize = 50,
                sortBy = UserIdentifierSortBy.UPDATED_AT,
                sortOrder = SortOrder.ASC,
                identifierIds = listOf("id_123"),
                userAuthProviders = listOf(UserAuthProvider.EMAIL),
                excludeUserAuthProviders = listOf(UserAuthProvider.PHONE),
                identifier = "test@example.com",
                displayName = "Test User",
                externalProviderEmail = "ext@example.com",
                isExternalProviderEmailSet = true,
                createdAtFrom = "2026-01-01T00:00:00Z",
                createdAtTo = "2026-03-01T00:00:00Z",
                updatedAtFrom = "2026-01-02T00:00:00Z",
                updatedAtTo = "2026-03-02T00:00:00Z",
                isUpdatedAtSet = true,
                searchQuery = "test"
            )
        val serialized = SelfIdentifierListParams.serialize(original)
        val restored = SelfIdentifierListParams.parse(serialized)

        assertEquals(original, restored)
    }
}
