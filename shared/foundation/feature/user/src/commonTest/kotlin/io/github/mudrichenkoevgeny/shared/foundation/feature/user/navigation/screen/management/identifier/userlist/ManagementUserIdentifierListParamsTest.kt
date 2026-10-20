package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserIdentifierSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementUserIdentifierListParamsTest {

    private companion object {
        const val TEST_USER_ID = "00000000-0000-4000-8000-000000000001"
    }

    @Test
    fun `parse extracts user identifier list params correctly`() {
        val params = mapOf(
            UserApiPaths.USER_ID to TEST_USER_ID,
            ListingParamNames.Pagination.PAGE_NUMBER to "1",
            ListingParamNames.Pagination.PAGE_SIZE to "20",
            UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER to listOf("email")
        )
        val parsed = ManagementUserIdentifierListParams.parse(params)

        assertEquals(TEST_USER_ID, parsed.userId)
        assertEquals(TEST_USER_ID, parsed.typedUserId?.asHexDashString())
        assertEquals(1, parsed.pageNumber)
        assertEquals(20, parsed.pageSize)
        assertEquals(listOf(UserAuthProvider.EMAIL), parsed.userAuthProviders)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = ManagementUserIdentifierListParams(
            userId = TEST_USER_ID,
            pageNumber = 2,
            pageSize = 50,
            sortBy = UserIdentifierSortBy.UPDATED_AT,
            sortOrder = SortOrder.ASC,
            userAuthProviders = listOf(UserAuthProvider.EMAIL)
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("2"), serialized[ListingParamNames.Pagination.PAGE_NUMBER])
        assertEquals(listOf("50"), serialized[ListingParamNames.Pagination.PAGE_SIZE])
        assertEquals(listOf("updated_at"), serialized[ListingParamNames.Sort.SORT_BY])
        assertEquals(listOf("asc"), serialized[ListingParamNames.Sort.SORT_ORDER])
        assertEquals(listOf("email"), serialized[UserFilterValues.UserIdentifierFilterValues.USER_AUTH_PROVIDER])
        assertEquals(serialized, ManagementUserIdentifierListParams.serialize(model))
    }
}
