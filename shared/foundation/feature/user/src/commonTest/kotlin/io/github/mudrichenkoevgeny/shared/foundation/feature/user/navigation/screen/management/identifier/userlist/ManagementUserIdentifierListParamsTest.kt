package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementUserIdentifierListParamsTest {
    @Test
    fun `parse extracts user identifier list params correctly`() {
        val params =
            mapOf(
                UserApiPaths.USER_ID to TEST_USER_ID,
                ListingParamNames.Pagination.PAGE_NUMBER to "1",
                ListingParamNames.Pagination.PAGE_SIZE to "20",
                UserIdentifierFilterValues.IDENTIFIER_ID to listOf("id_123"),
                UserIdentifierFilterValues.USER_AUTH_PROVIDER to listOf("email"),
                UserIdentifierFilterValues.EXCLUDE_USER_AUTH_PROVIDER to listOf("google"),
                UserIdentifierFilterValues.IDENTIFIER to "user@example.com",
                UserIdentifierFilterValues.DISPLAY_NAME to "User Name",
                UserIdentifierFilterValues.EXTERNAL_PROVIDER_EMAIL to "ext@example.com",
                UserIdentifierFilterValues.IS_EXTERNAL_PROVIDER_EMAIL_SET to "true",
                UserIdentifierFilterValues.CREATED_AT_FROM to "2026-01-01T00:00:00Z",
                UserIdentifierFilterValues.CREATED_AT_TO to "2026-03-01T00:00:00Z",
                UserIdentifierFilterValues.UPDATED_AT_FROM to "2026-01-02T00:00:00Z",
                UserIdentifierFilterValues.UPDATED_AT_TO to "2026-03-02T00:00:00Z",
                UserIdentifierFilterValues.IS_UPDATED_AT_SET to "true",
                UserIdentifierFilterValues.SEARCH_QUERY to "user"
            )
        val parsed = ManagementUserIdentifierListParams.parse(params)

        assertEquals(TEST_USER_ID, parsed.userId)
        assertEquals(TEST_USER_ID, parsed.typedUserId?.asHexDashString())
        assertEquals(1, parsed.pageNumber)
        assertEquals(20, parsed.pageSize)
        assertEquals(listOf("id_123"), parsed.identifierIds)
        assertEquals(listOf(UserAuthProvider.EMAIL), parsed.userAuthProviders)
        assertEquals(listOf(UserAuthProvider.GOOGLE), parsed.excludeUserAuthProviders)
        assertEquals("user@example.com", parsed.identifier)
        assertEquals("User Name", parsed.displayName)
        assertEquals("ext@example.com", parsed.externalProviderEmail)
        assertEquals(true, parsed.isExternalProviderEmailSet)
        assertEquals("2026-01-01T00:00:00Z", parsed.createdAtFrom)
        assertEquals("2026-03-01T00:00:00Z", parsed.createdAtTo)
        assertEquals("2026-01-02T00:00:00Z", parsed.updatedAtFrom)
        assertEquals("2026-03-02T00:00:00Z", parsed.updatedAtTo)
        assertEquals(true, parsed.isUpdatedAtSet)
        assertEquals("user", parsed.searchQuery)
    }

    @Test
    fun `serialize and parse roundtrip maintains equality`() {
        val original =
            ManagementUserIdentifierListParams(
                userId = TEST_USER_ID,
                pageNumber = 2,
                pageSize = 50,
                sortBy = UserIdentifierSortBy.UPDATED_AT,
                sortOrder = SortOrder.ASC,
                identifierIds = listOf("id_123"),
                userAuthProviders = listOf(UserAuthProvider.EMAIL),
                excludeUserAuthProviders = listOf(UserAuthProvider.GOOGLE),
                identifier = "user@example.com",
                displayName = "User Name",
                externalProviderEmail = "ext@example.com",
                isExternalProviderEmailSet = true,
                createdAtFrom = "2026-01-01T00:00:00Z",
                createdAtTo = "2026-03-01T00:00:00Z",
                updatedAtFrom = "2026-01-02T00:00:00Z",
                updatedAtTo = "2026-03-02T00:00:00Z",
                isUpdatedAtSet = true,
                searchQuery = "user"
            )
        val serialized = ManagementUserIdentifierListParams.serialize(original)
        val restored = ManagementUserIdentifierListParams.parse(serialized + (UserApiPaths.USER_ID to TEST_USER_ID))

        assertEquals(original, restored)
    }

    private companion object {
        const val TEST_USER_ID = "00000000-0000-4000-8000-000000000001"
    }
}
