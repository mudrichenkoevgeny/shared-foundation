package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementUserListParamsParserTest {

    @Test
    fun `parse extracts management user list params correctly`() {
        val raw = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to "2",
            ListingParamNames.Pagination.PAGE_SIZE to "50",
            ListingParamNames.Sort.SORT_BY to "last_login_at",
            ListingParamNames.Sort.SORT_ORDER to "asc",
            UserFilterValues.UserFilterValues.ROLE to listOf("admin", "staff"),
            UserFilterValues.UserFilterValues.ACCOUNT_STATUS to listOf("active")
        )

        val parsed = ManagementUserListParams.parse(raw)

        assertEquals(2, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(UserSortBy.LAST_LOGIN_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf(UserRole.ADMIN, UserRole.STAFF), parsed.userRoles)
        assertEquals(listOf(UserAccountStatus.ACTIVE), parsed.accountStatuses)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = ManagementUserListParams.parse(emptyMap())
        assertEquals(ManagementUserListParams(), parsed)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = ManagementUserListParams(
            pageNumber = 2,
            pageSize = 50,
            sortBy = UserSortBy.LAST_LOGIN_AT,
            sortOrder = SortOrder.ASC,
            userRoles = listOf(UserRole.ADMIN),
            accountStatuses = listOf(UserAccountStatus.ACTIVE)
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("2"), serialized[ListingParamNames.Pagination.PAGE_NUMBER])
        assertEquals(listOf("50"), serialized[ListingParamNames.Pagination.PAGE_SIZE])
        assertEquals(listOf("last_login_at"), serialized[ListingParamNames.Sort.SORT_BY])
        assertEquals(listOf("asc"), serialized[ListingParamNames.Sort.SORT_ORDER])
        assertEquals(listOf("admin"), serialized[UserFilterValues.UserFilterValues.ROLE])
        assertEquals(listOf("active"), serialized[UserFilterValues.UserFilterValues.ACCOUNT_STATUS])
        assertEquals(serialized, ManagementUserListParamsParser.serialize(model))
    }
}
