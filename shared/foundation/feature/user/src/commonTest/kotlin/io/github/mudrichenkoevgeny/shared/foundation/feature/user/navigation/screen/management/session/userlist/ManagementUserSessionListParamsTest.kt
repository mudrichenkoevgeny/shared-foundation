package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementUserSessionListParamsTest {

    @Test
    fun `parse extracts user session list params correctly`() {
        val params = mapOf(
            UserApiPaths.USER_ID to "123e4567-e89b-12d3-a456-426614174000",
            ListingParamNames.Pagination.PAGE_NUMBER to "1",
            ListingParamNames.Pagination.PAGE_SIZE to "20",
            UserFilterValues.UserSessionFilterValues.USER_ROLE to listOf("user"),
            UserFilterValues.UserSessionFilterValues.CLIENT_TYPE to listOf("android")
        )
        val parsed = ManagementUserSessionListParams.parse(params)

        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.userId)
        assertEquals("123e4567-e89b-12d3-a456-426614174000", parsed.typedUserId?.asHexDashString())
        assertEquals(1, parsed.pageNumber)
        assertEquals(20, parsed.pageSize)
        assertEquals(listOf(UserRole.USER), parsed.userRoles)
        assertEquals(listOf(ClientType.ANDROID), parsed.clientTypes)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = ManagementUserSessionListParams(
            userId = "123e4567-e89b-12d3-a456-426614174000",
            pageNumber = 2,
            pageSize = 50,
            sortBy = UserSessionSortBy.UPDATED_AT,
            sortOrder = SortOrder.ASC,
            userRoles = listOf(UserRole.USER),
            clientTypes = listOf(ClientType.ANDROID),
            userAuthProviders = listOf(UserAuthProvider.EMAIL),
            identifiers = listOf("user@example.com")
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("2"), serialized[ListingParamNames.Pagination.PAGE_NUMBER])
        assertEquals(listOf("50"), serialized[ListingParamNames.Pagination.PAGE_SIZE])
        assertEquals(listOf("updated_at"), serialized[ListingParamNames.Sort.SORT_BY])
        assertEquals(listOf("asc"), serialized[ListingParamNames.Sort.SORT_ORDER])
        assertEquals(listOf("user"), serialized[UserFilterValues.UserSessionFilterValues.USER_ROLE])
        assertEquals(listOf("android"), serialized[UserFilterValues.UserSessionFilterValues.CLIENT_TYPE])
        assertEquals(listOf("email"), serialized[UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER])
        assertEquals(listOf("user@example.com"), serialized[UserFilterValues.UserSessionFilterValues.IDENTIFIER])
        assertEquals(serialized, ManagementUserSessionListParams.serialize(model))
    }
}
