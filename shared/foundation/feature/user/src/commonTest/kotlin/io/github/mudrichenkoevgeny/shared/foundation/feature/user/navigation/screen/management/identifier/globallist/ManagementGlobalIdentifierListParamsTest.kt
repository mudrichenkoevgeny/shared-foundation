package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserIdentifierSortBy
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementGlobalIdentifierListParamsTest {

    @Test
    fun `parse extracts global identifier list params correctly`() {
        val params = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to "1",
            ListingParamNames.Pagination.PAGE_SIZE to "50",
            ListingParamNames.Sort.SORT_BY to "updated_at",
            ListingParamNames.Sort.SORT_ORDER to "asc",
            UserFilterValues.UserIdentifierFilterValues.USER_ID to listOf("usr_123"),
            UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER to listOf("email", "phone"),
            UserFilterValues.UserIdentifierFilterValues.IDENTIFIER to listOf("admin@example.com")
        )
        val parsed = ManagementGlobalIdentifierListParams.parse(params)

        assertEquals(1, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(UserIdentifierSortBy.UPDATED_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf("usr_123"), parsed.userIds)
        assertEquals(listOf(UserAuthProvider.EMAIL, UserAuthProvider.PHONE), parsed.userAuthProviders)
        assertEquals(listOf("admin@example.com"), parsed.identifiers)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = ManagementGlobalIdentifierListParams.parse(emptyMap())
        assertEquals(ManagementGlobalIdentifierListParams(), parsed)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = ManagementGlobalIdentifierListParams(
            pageNumber = 2,
            pageSize = 50,
            sortBy = UserIdentifierSortBy.UPDATED_AT,
            sortOrder = SortOrder.ASC,
            userIds = listOf("usr_123"),
            userAuthProviders = listOf(UserAuthProvider.EMAIL),
            identifiers = listOf("admin@example.com")
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("2"), serialized[ListingParamNames.Pagination.PAGE_NUMBER])
        assertEquals(listOf("50"), serialized[ListingParamNames.Pagination.PAGE_SIZE])
        assertEquals(listOf("updated_at"), serialized[ListingParamNames.Sort.SORT_BY])
        assertEquals(listOf("asc"), serialized[ListingParamNames.Sort.SORT_ORDER])
        assertEquals(listOf("usr_123"), serialized[UserFilterValues.UserIdentifierFilterValues.USER_ID])
        assertEquals(listOf("email"), serialized[UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER])
        assertEquals(listOf("admin@example.com"), serialized[UserFilterValues.UserIdentifierFilterValues.IDENTIFIER])
        assertEquals(serialized, ManagementGlobalIdentifierListParams.serialize(model))
    }
}
