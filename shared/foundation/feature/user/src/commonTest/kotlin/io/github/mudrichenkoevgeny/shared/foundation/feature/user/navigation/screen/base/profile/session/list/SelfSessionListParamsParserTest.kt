package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.session.list

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSessionSortBy
import kotlin.test.Test
import kotlin.test.assertEquals

class SelfSessionListParamsParserTest {

    @Test
    fun `parse extracts self session list params correctly`() {
        val params = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to "3",
            ListingParamNames.Pagination.PAGE_SIZE to "50",
            ListingParamNames.Sort.SORT_BY to "updated_at",
            ListingParamNames.Sort.SORT_ORDER to "asc",
            UserFilterValues.UserSessionFilterValues.IDENTIFIER to listOf("user@example.com"),
            UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID to listOf("id_123"),
            UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER to listOf("email"),
            UserFilterValues.UserSessionFilterValues.CLIENT_TYPE to listOf("android", "web"),
            UserFilterValues.UserSessionFilterValues.USER_AGENT to listOf("Mozilla/5.0"),
            UserFilterValues.UserSessionFilterValues.IP_ADDRESS to listOf("127.0.0.1"),
            UserFilterValues.UserSessionFilterValues.LANGUAGE to listOf("en-US"),
            UserFilterValues.UserSessionFilterValues.DEVICE_ID to listOf("dev_456"),
            UserFilterValues.UserSessionFilterValues.DEVICE_NAME to listOf("Pixel 7"),
            UserFilterValues.UserSessionFilterValues.APP_VERSION to listOf("1.0.0"),
            UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION to listOf("Android 13")
        )
        val parsed = SelfSessionListParams.parse(params)

        assertEquals(3, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(UserSessionSortBy.UPDATED_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf("user@example.com"), parsed.identifiers)
        assertEquals(listOf("id_123"), parsed.identifierIds)
        assertEquals(listOf(UserAuthProvider.EMAIL), parsed.userAuthProviders)
        assertEquals(listOf(ClientType.ANDROID, ClientType.WEB), parsed.clientTypes)
        assertEquals(listOf("Mozilla/5.0"), parsed.userAgents)
        assertEquals(listOf("127.0.0.1"), parsed.ipAddresses)
        assertEquals(listOf("en-US"), parsed.languages)
        assertEquals(listOf("dev_456"), parsed.deviceIds)
        assertEquals(listOf("Pixel 7"), parsed.deviceNames)
        assertEquals(listOf("1.0.0"), parsed.appVersions)
        assertEquals(listOf("Android 13"), parsed.osVersions)
    }

    @Test
    fun `parse applies default values when map is empty`() {
        val parsed = SelfSessionListParams.parse(emptyMap())
        assertEquals(SelfSessionListParams(), parsed)
    }

    @Test
    fun `serialize and toQueryMap produce correct map`() {
        val model = SelfSessionListParams(
            pageNumber = 3,
            pageSize = 50,
            sortBy = UserSessionSortBy.UPDATED_AT,
            sortOrder = SortOrder.ASC,
            identifiers = listOf("user@example.com"),
            identifierIds = listOf("id_123"),
            userAuthProviders = listOf(UserAuthProvider.EMAIL),
            clientTypes = listOf(ClientType.ANDROID),
            userAgents = listOf("Mozilla/5.0"),
            ipAddresses = listOf("127.0.0.1"),
            languages = listOf("en-US"),
            deviceIds = listOf("dev_456"),
            deviceNames = listOf("Pixel 7"),
            appVersions = listOf("1.0.0"),
            osVersions = listOf("Android 13")
        )
        val serialized = model.toQueryMap()

        assertEquals(listOf("3"), serialized[ListingParamNames.Pagination.PAGE_NUMBER])
        assertEquals(listOf("50"), serialized[ListingParamNames.Pagination.PAGE_SIZE])
        assertEquals(listOf("updated_at"), serialized[ListingParamNames.Sort.SORT_BY])
        assertEquals(listOf("asc"), serialized[ListingParamNames.Sort.SORT_ORDER])
        assertEquals(listOf("user@example.com"), serialized[UserFilterValues.UserSessionFilterValues.IDENTIFIER])
        assertEquals(listOf("id_123"), serialized[UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID])
        assertEquals(listOf("email"), serialized[UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER])
        assertEquals(listOf("android"), serialized[UserFilterValues.UserSessionFilterValues.CLIENT_TYPE])
        assertEquals(listOf("Mozilla/5.0"), serialized[UserFilterValues.UserSessionFilterValues.USER_AGENT])
        assertEquals(listOf("127.0.0.1"), serialized[UserFilterValues.UserSessionFilterValues.IP_ADDRESS])
        assertEquals(listOf("en-US"), serialized[UserFilterValues.UserSessionFilterValues.LANGUAGE])
        assertEquals(listOf("dev_456"), serialized[UserFilterValues.UserSessionFilterValues.DEVICE_ID])
        assertEquals(listOf("Pixel 7"), serialized[UserFilterValues.UserSessionFilterValues.DEVICE_NAME])
        assertEquals(listOf("1.0.0"), serialized[UserFilterValues.UserSessionFilterValues.APP_VERSION])
        assertEquals(listOf("Android 13"), serialized[UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION])
        assertEquals(serialized, SelfSessionListParamsParser.serialize(model))
    }
}
