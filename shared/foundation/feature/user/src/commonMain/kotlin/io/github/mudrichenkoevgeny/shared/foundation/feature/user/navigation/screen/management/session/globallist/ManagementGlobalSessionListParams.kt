package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for system-wide active sessions management list.
 *
 * @property pageNumber One-based page index; wire key is [ListingParamNames.Pagination.PAGE_NUMBER].
 * @property pageSize Maximum items returned per page; wire key is [ListingParamNames.Pagination.PAGE_SIZE].
 * @property sortBy Field to sort listing results by; wire key is [ListingParamNames.Sort.SORT_BY].
 * @property sortOrder Direction of sorting; wire key is [ListingParamNames.Sort.SORT_ORDER].
 * @property userIds List of user IDs to filter by; wire key is [UserFilterValues.UserSessionFilterValues.USER_ID].
 * @property userRoles List of [UserRole] items to filter by; wire key is [UserFilterValues.UserSessionFilterValues.USER_ROLE].
 * @property identifiers List of login identifier strings to filter by; wire key is [UserFilterValues.UserSessionFilterValues.IDENTIFIER].
 * @property identifierIds List of identifier record IDs to filter by; wire key is [UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID].
 * @property userAuthProviders List of [UserAuthProvider] items to filter by; wire key is [UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER].
 * @property clientTypes List of [ClientType] items to filter by; wire key is [UserFilterValues.UserSessionFilterValues.CLIENT_TYPE].
 * @property userAgents List of user agent strings to filter by; wire key is [UserFilterValues.UserSessionFilterValues.USER_AGENT].
 * @property ipAddresses List of IP address strings to filter by; wire key is [UserFilterValues.UserSessionFilterValues.IP_ADDRESS].
 * @property languages List of language strings to filter by; wire key is [UserFilterValues.UserSessionFilterValues.LANGUAGE].
 * @property deviceIds List of device IDs to filter by; wire key is [UserFilterValues.UserSessionFilterValues.DEVICE_ID].
 * @property deviceNames List of device names to filter by; wire key is [UserFilterValues.UserSessionFilterValues.DEVICE_NAME].
 * @property appVersions List of application versions to filter by; wire key is [UserFilterValues.UserSessionFilterValues.APP_VERSION].
 * @property osVersions List of OS versions to filter by; wire key is [UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION].
 */
@Serializable
data class ManagementGlobalSessionListParams(
    val pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    val pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    val sortBy: UserSessionSortBy = UserSessionSortBy.DEFAULT,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val userIds: List<String> = emptyList(),
    val userRoles: List<UserRole> = emptyList(),
    val identifiers: List<String> = emptyList(),
    val identifierIds: List<String> = emptyList(),
    val userAuthProviders: List<UserAuthProvider> = emptyList(),
    val clientTypes: List<ClientType> = emptyList(),
    val userAgents: List<String> = emptyList(),
    val ipAddresses: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val deviceIds: List<String> = emptyList(),
    val deviceNames: List<String> = emptyList(),
    val appVersions: List<String> = emptyList(),
    val osVersions: List<String> = emptyList()
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        ManagementGlobalSessionListParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): ManagementGlobalSessionListParams =
            ManagementGlobalSessionListParamsParser.parse(params)
    }
}
