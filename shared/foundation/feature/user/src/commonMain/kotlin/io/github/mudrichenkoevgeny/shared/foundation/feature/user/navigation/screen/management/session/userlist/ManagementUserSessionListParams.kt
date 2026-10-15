package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrNull
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for active sessions list screen of a target user.
 *
 * @property userId Raw string representation of the target user ID extracted from path parameters;
 * wire key is [UserApiPaths.USER_ID]. May be empty or unvalidated until parsed via [typedUserId].
 * @property pageNumber One-based page index; wire key is [ListingParamNames.Pagination.PAGE_NUMBER].
 * @property pageSize Maximum items returned per page; wire key is [ListingParamNames.Pagination.PAGE_SIZE].
 * @property sortBy Field to sort listing results by; wire key is [ListingParamNames.Sort.SORT_BY].
 * @property sortOrder Direction of sorting; wire key is [ListingParamNames.Sort.SORT_ORDER].
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
data class ManagementUserSessionListParams(
    val userId: String = "",
    val pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    val pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    val sortBy: UserSessionSortBy = UserSessionSortBy.DEFAULT,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
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
) : NavigationScreenParams<ManagementUserSessionListParams> {

    /**
     * Parsed strongly typed [UserId] representation of [userId].
     *
     * Returns `null` if [userId] is blank or does not conform to the canonical UUID format.
     */
    val typedUserId: UserId?
        get() = userId.toUserIdOrNull()

    override val parser: NavigationScreenParamsParser<ManagementUserSessionListParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<ManagementUserSessionListParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [ManagementUserSessionListParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [ManagementUserSessionListParams] instance.
         */
        override fun parse(params: Map<String, Any?>): ManagementUserSessionListParams {
            val normalized = params.normalizeQueryParams()

            val userId = normalized[UserApiPaths.USER_ID]?.firstOrNull()?.trim() ?: ""

            val pageNumber = normalized[ListingParamNames.Pagination.PAGE_NUMBER]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it > 0 }
                ?: ListingDefaultValues.Pagination.PAGE_NUMBER

            val pageSize = normalized[ListingParamNames.Pagination.PAGE_SIZE]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it > 0 }
                ?: ListingDefaultValues.Pagination.PAGE_SIZE

            val sortBy = normalized[ListingParamNames.Sort.SORT_BY]
                ?.firstOrNull()
                ?.let { UserSessionSortBy.fromValueOrNull(it) }
                ?: UserSessionSortBy.DEFAULT

            val sortOrder = normalized[ListingParamNames.Sort.SORT_ORDER]
                ?.firstOrNull()
                ?.let { SortOrder.fromValueOrNull(it) }
                ?: SortOrder.DEFAULT

            val userRoles = normalized[UserFilterValues.UserSessionFilterValues.USER_ROLE]
                ?.mapNotNull { UserRole.fromValueOrNull(it) }
                ?: emptyList()

            val identifiers = normalized[UserFilterValues.UserSessionFilterValues.IDENTIFIER]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val identifierIds = normalized[UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val userAuthProviders = normalized[UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER]
                ?.mapNotNull { UserAuthProvider.fromValueOrNull(it) }
                ?: emptyList()

            val clientTypes = normalized[UserFilterValues.UserSessionFilterValues.CLIENT_TYPE]
                ?.mapNotNull { ClientType.fromValueOrNull(it) }
                ?: emptyList()

            val userAgents = normalized[UserFilterValues.UserSessionFilterValues.USER_AGENT]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val ipAddresses = normalized[UserFilterValues.UserSessionFilterValues.IP_ADDRESS]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val languages = normalized[UserFilterValues.UserSessionFilterValues.LANGUAGE]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val deviceIds = normalized[UserFilterValues.UserSessionFilterValues.DEVICE_ID]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val deviceNames = normalized[UserFilterValues.UserSessionFilterValues.DEVICE_NAME]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val appVersions = normalized[UserFilterValues.UserSessionFilterValues.APP_VERSION]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val osVersions = normalized[UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            return ManagementUserSessionListParams(
                userId = userId,
                pageNumber = pageNumber,
                pageSize = pageSize,
                sortBy = sortBy,
                sortOrder = sortOrder,
                userRoles = userRoles,
                identifiers = identifiers,
                identifierIds = identifierIds,
                userAuthProviders = userAuthProviders,
                clientTypes = clientTypes,
                userAgents = userAgents,
                ipAddresses = ipAddresses,
                languages = languages,
                deviceIds = deviceIds,
                deviceNames = deviceNames,
                appVersions = appVersions,
                osVersions = osVersions
            )
        }

        /**
         * Serializes [ManagementUserSessionListParams] into a map of raw URL parameters.
         *
         * Note that [ManagementUserSessionListParams.userId] is omitted as it is represented
         * as a path parameter in the route URL rather than a query parameter.
         *
         * @param params Typed [ManagementUserSessionListParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: ManagementUserSessionListParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()

            if (params.pageNumber != ListingDefaultValues.Pagination.PAGE_NUMBER) {
                result[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.PAGE_SIZE) {
                result[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != UserSessionSortBy.DEFAULT) {
                result[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                result[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }
            if (params.userRoles.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.USER_ROLE] = params.userRoles.map { it.value }
            }
            if (params.identifiers.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.IDENTIFIER] = params.identifiers
            }
            if (params.identifierIds.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID] = params.identifierIds
            }
            if (params.userAuthProviders.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER] = params.userAuthProviders.map { it.value }
            }
            if (params.clientTypes.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.CLIENT_TYPE] = params.clientTypes.map { it.value }
            }
            if (params.userAgents.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.USER_AGENT] = params.userAgents
            }
            if (params.ipAddresses.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.IP_ADDRESS] = params.ipAddresses
            }
            if (params.languages.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.LANGUAGE] = params.languages
            }
            if (params.deviceIds.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.DEVICE_ID] = params.deviceIds
            }
            if (params.deviceNames.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.DEVICE_NAME] = params.deviceNames
            }
            if (params.appVersions.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.APP_VERSION] = params.appVersions
            }
            if (params.osVersions.isNotEmpty()) {
                result[UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION] = params.osVersions
            }

            return result
        }
    }
}