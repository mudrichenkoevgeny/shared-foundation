package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserIdentifierSortBy
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementUserIdentifierListParamsParserTest {

    @Test
    fun `parse extracts user identifier list params correctly`() {
        val params = mapOf(
            UserFilterValues.UserIdentifierFilterValues.USER_ID to listOf("123e4567-e89b-12d3-a456-426614174000"),
            ListingParamNames.Pagination.PAGE_NUMBER to "1",
            ListingParamNames.Pagination.PAGE_SIZE to "20",
            UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER to listOf("email")
        )
        val parsed = ManagementUserIdentifierListParams.parse(params)

        assertEquals(listOf("123e4567-e89b-12d3-a456-426614174000"), parsed.userIds)
        assertEquals(listOf("123e4567-e89b-12d3-a456-426614174000"), parsed.typedUserIds.map { it.asHexDashString() })
        assertEquals(1, parsed.pageNumber)
        assertEquals(20, parsed.pageSize)
        assertEquals(listOf(UserAuthProvider.EMAIL), parsed.userAuthProviders)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = ManagementUserIdentifierListParams(
            userIds = listOf("123e4567-e89b-12d3-a456-426614174000"),
            pageNumber = 2,
            pageSize = 50,
            sortBy = UserIdentifierSortBy.UPDATED_AT,
            sortOrder = SortOrder.ASC,
            userAuthProviders = listOf(UserAuthProvider.EMAIL)
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("123e4567-e89b-12d3-a456-426614174000"), serialized[UserFilterValues.UserIdentifierFilterValues.USER_ID])
        assertEquals(listOf("2"), serialized[ListingParamNames.Pagination.PAGE_NUMBER])
        assertEquals(listOf("50"), serialized[ListingParamNames.Pagination.PAGE_SIZE])
        assertEquals(listOf("updated_at"), serialized[ListingParamNames.Sort.SORT_BY])
        assertEquals(listOf("asc"), serialized[ListingParamNames.Sort.SORT_ORDER])
        assertEquals(listOf("email"), serialized[UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER])
        assertEquals(serialized, ManagementUserIdentifierListParamsParser.serialize(model))
    }
}
