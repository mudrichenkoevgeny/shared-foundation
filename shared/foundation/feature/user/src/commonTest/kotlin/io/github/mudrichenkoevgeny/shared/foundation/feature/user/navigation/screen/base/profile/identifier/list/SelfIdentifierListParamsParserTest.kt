package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.identifier.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserIdentifierSortBy
import kotlin.test.Test
import kotlin.test.assertEquals

class SelfIdentifierListParamsParserTest {

    @Test
    fun `parse extracts self identifier list params correctly`() {
        val params = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to "2",
            ListingParamNames.Pagination.PAGE_SIZE to "50",
            ListingParamNames.Sort.SORT_BY to "updated_at",
            ListingParamNames.Sort.SORT_ORDER to "asc",
            UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER to listOf("email", "google"),
            UserFilterValues.UserIdentifierFilterValues.IDENTIFIER to listOf("test@example.com")
        )
        val parsed = SelfIdentifierListParams.parse(params)

        assertEquals(2, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(UserIdentifierSortBy.UPDATED_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf(UserAuthProvider.EMAIL, UserAuthProvider.GOOGLE), parsed.userAuthProviders)
        assertEquals(listOf("test@example.com"), parsed.identifiers)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = SelfIdentifierListParams.parse(emptyMap())
        assertEquals(SelfIdentifierListParams(), parsed)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = SelfIdentifierListParams(
            pageNumber = 2,
            pageSize = 50,
            sortBy = UserIdentifierSortBy.UPDATED_AT,
            sortOrder = SortOrder.ASC,
            userAuthProviders = listOf(UserAuthProvider.EMAIL),
            identifiers = listOf("test@example.com")
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("2"), serialized[ListingParamNames.Pagination.PAGE_NUMBER])
        assertEquals(listOf("50"), serialized[ListingParamNames.Pagination.PAGE_SIZE])
        assertEquals(listOf("updated_at"), serialized[ListingParamNames.Sort.SORT_BY])
        assertEquals(listOf("asc"), serialized[ListingParamNames.Sort.SORT_ORDER])
        assertEquals(listOf("email"), serialized[UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER])
        assertEquals(listOf("test@example.com"), serialized[UserFilterValues.UserIdentifierFilterValues.IDENTIFIER])
        assertEquals(serialized, SelfIdentifierListParamsParser.serialize(model))
    }
}
