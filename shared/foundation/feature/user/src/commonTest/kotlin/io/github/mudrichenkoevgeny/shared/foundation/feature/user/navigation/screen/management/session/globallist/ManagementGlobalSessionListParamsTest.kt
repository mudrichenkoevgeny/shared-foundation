package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementGlobalSessionListParamsTest {

    @Test
    fun `parse extracts global session list params correctly`() {
        val params = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to "1",
            ListingParamNames.Pagination.PAGE_SIZE to "50",
            ListingParamNames.Sort.SORT_BY to "updated_at",
            ListingParamNames.Sort.SORT_ORDER to "asc",
            UserFilterValues.UserSessionFilterValues.USER_ROLE to listOf("admin"),
            UserFilterValues.UserSessionFilterValues.CLIENT_TYPE to listOf("web")
        )
        val parsed = ManagementGlobalSessionListParams.parse(params)

        assertEquals(1, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(UserSessionSortBy.UPDATED_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf(UserRole.ADMIN), parsed.userRoles)
        assertEquals(listOf(ClientType.WEB), parsed.clientTypes)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = ManagementGlobalSessionListParams.parse(emptyMap())
        assertEquals(ManagementGlobalSessionListParams(), parsed)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = ManagementGlobalSessionListParams(
            pageNumber = 2,
            pageSize = 50,
            sortBy = UserSessionSortBy.UPDATED_AT,
            sortOrder = SortOrder.ASC,
            userRoles = listOf(UserRole.ADMIN),
            clientTypes = listOf(ClientType.WEB),
            userAuthProviders = listOf(UserAuthProvider.EMAIL)
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("2"), serialized[ListingParamNames.Pagination.PAGE_NUMBER])
        assertEquals(listOf("50"), serialized[ListingParamNames.Pagination.PAGE_SIZE])
        assertEquals(listOf("updated_at"), serialized[ListingParamNames.Sort.SORT_BY])
        assertEquals(listOf("asc"), serialized[ListingParamNames.Sort.SORT_ORDER])
        assertEquals(listOf("admin"), serialized[UserFilterValues.UserSessionFilterValues.USER_ROLE])
        assertEquals(listOf("web"), serialized[UserFilterValues.UserSessionFilterValues.CLIENT_TYPE])
        assertEquals(listOf("email"), serialized[UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER])
        assertEquals(serialized, ManagementGlobalSessionListParams.serialize(model))
    }
}
