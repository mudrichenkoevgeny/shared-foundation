package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for system-wide active sessions management list.
 *
 * @property pageNumber One-based page index; wire key is [ListingParamNames.Pagination.PAGE_NUMBER].
 * @property pageSize Maximum items returned per page; wire key is [ListingParamNames.Pagination.PAGE_SIZE].
 * @property sortBy Field to sort listing results by; wire key is [ListingParamNames.Sort.SORT_BY].
 * @property sortOrder Direction of sorting; wire key is [ListingParamNames.Sort.SORT_ORDER].
 * @property sessionIds Specific session IDs to filter by; wire key is [UserSessionFilterValues.SESSION_ID].
 * @property userIds Owning user IDs to filter by; wire key is [UserSessionFilterValues.USER_ID].
 * @property userRoles List of [UserRole] items to filter by; wire key is [UserSessionFilterValues.USER_ROLE].
 * @property excludeUserRoles Excluded [UserRole] items; wire key is [UserSessionFilterValues.EXCLUDE_USER_ROLE].
 * @property identifier Exact raw or masked login identifier string; wire key is [UserSessionFilterValues.IDENTIFIER].
 * @property identifierIds Identifier record IDs to filter by; wire key is [UserSessionFilterValues.IDENTIFIER_ID].
 * @property identifierDisplayName Exact identifier display name; wire key is [UserSessionFilterValues.IDENTIFIER_DISPLAY_NAME].
 * @property identifierAuthProviders List of [UserAuthProvider] items to filter by; wire key is [UserSessionFilterValues.IDENTIFIER_AUTH_PROVIDER].
 * @property excludeIdentifierAuthProviders Excluded [UserAuthProvider] items; wire key is [UserSessionFilterValues.EXCLUDE_IDENTIFIER_AUTH_PROVIDER].
 * @property clientTypes List of [ClientType] items to filter by; wire key is [UserSessionFilterValues.CLIENT_TYPE].
 * @property excludeClientTypes Excluded [ClientType] items; wire key is [UserSessionFilterValues.EXCLUDE_CLIENT_TYPE].
 * @property isClientTypeSet Filter by client type presence; wire key is [UserSessionFilterValues.IS_CLIENT_TYPE_SET].
 * @property deviceId Exact stable device ID; wire key is [UserSessionFilterValues.DEVICE_ID].
 * @property isDeviceIdSet Filter by device ID presence; wire key is [UserSessionFilterValues.IS_DEVICE_ID_SET].
 * @property deviceName Exact device name; wire key is [UserSessionFilterValues.DEVICE_NAME].
 * @property isDeviceNameSet Filter by device name presence; wire key is [UserSessionFilterValues.IS_DEVICE_NAME_SET].
 * @property language Primary BCP 47 language code; wire key is [UserSessionFilterValues.LANGUAGE].
 * @property isLanguageSet Filter by language presence; wire key is [UserSessionFilterValues.IS_LANGUAGE_SET].
 * @property appVersion Exact application version; wire key is [UserSessionFilterValues.APP_VERSION].
 * @property isAppVersionSet Filter by app version presence; wire key is [UserSessionFilterValues.IS_APP_VERSION_SET].
 * @property osVersion Exact operating system version; wire key is [UserSessionFilterValues.OPERATION_SYSTEM_VERSION].
 * @property isOperationSystemVersionSet Filter by OS version presence; wire key is [UserSessionFilterValues.IS_OPERATION_SYSTEM_VERSION_SET].
 * @property userAgent Exact HTTP User-Agent string; wire key is [UserSessionFilterValues.USER_AGENT].
 * @property isUserAgentSet Filter by User-Agent header presence; wire key is [UserSessionFilterValues.IS_USER_AGENT_SET].
 * @property ipAddress Exact raw or masked IP address; wire key is [UserSessionFilterValues.IP_ADDRESS].
 * @property isIpAddressSet Filter by IP address presence; wire key is [UserSessionFilterValues.IS_IP_ADDRESS_SET].
 * @property expiresAtFrom Lower bound expiration timestamp; wire key is [UserSessionFilterValues.EXPIRES_AT_FROM].
 * @property expiresAtTo Upper bound expiration timestamp; wire key is [UserSessionFilterValues.EXPIRES_AT_TO].
 * @property lastAccessedAtFrom Lower bound last accessed timestamp; wire key is [UserSessionFilterValues.LAST_ACCESSED_AT_FROM].
 * @property lastAccessedAtTo Upper bound last accessed timestamp; wire key is [UserSessionFilterValues.LAST_ACCESSED_AT_TO].
 * @property lastReauthenticatedAtFrom Lower bound re-authentication timestamp; wire key is [UserSessionFilterValues.LAST_REAUTHENTICATED_AT_FROM].
 * @property lastReauthenticatedAtTo Upper bound re-authentication timestamp; wire key is [UserSessionFilterValues.LAST_REAUTHENTICATED_AT_TO].
 * @property createdAtFrom Lower bound creation timestamp; wire key is [UserSessionFilterValues.CREATED_AT_FROM].
 * @property createdAtTo Upper bound creation timestamp; wire key is [UserSessionFilterValues.CREATED_AT_TO].
 * @property updatedAtFrom Lower bound update timestamp; wire key is [UserSessionFilterValues.UPDATED_AT_FROM].
 * @property updatedAtTo Upper bound update timestamp; wire key is [UserSessionFilterValues.UPDATED_AT_TO].
 * @property isUpdatedAtSet Filter by update presence; wire key is [UserSessionFilterValues.IS_UPDATED_AT_SET].
 * @property searchQuery Search query targeting identifier and device name; wire key is [UserSessionFilterValues.SEARCH_QUERY].
 */
@Serializable
data class ManagementGlobalSessionListParams(
    val pageNumber: Int = ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
    val pageSize: Int = ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
    val sortBy: UserSessionSortBy = UserSessionSortBy.DEFAULT,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val sessionIds: List<String> = emptyList(),
    val userIds: List<String> = emptyList(),
    val userRoles: List<UserRole> = emptyList(),
    val excludeUserRoles: List<UserRole> = emptyList(),
    val identifier: String? = null,
    val identifierIds: List<String> = emptyList(),
    val identifierDisplayName: String? = null,
    val identifierAuthProviders: List<UserAuthProvider> = emptyList(),
    val excludeIdentifierAuthProviders: List<UserAuthProvider> = emptyList(),
    val clientTypes: List<ClientType> = emptyList(),
    val excludeClientTypes: List<ClientType> = emptyList(),
    val isClientTypeSet: Boolean? = null,
    val deviceId: String? = null,
    val isDeviceIdSet: Boolean? = null,
    val deviceName: String? = null,
    val isDeviceNameSet: Boolean? = null,
    val language: String? = null,
    val isLanguageSet: Boolean? = null,
    val appVersion: String? = null,
    val isAppVersionSet: Boolean? = null,
    val osVersion: String? = null,
    val isOperationSystemVersionSet: Boolean? = null,
    val userAgent: String? = null,
    val isUserAgentSet: Boolean? = null,
    val ipAddress: String? = null,
    val isIpAddressSet: Boolean? = null,
    val expiresAtFrom: String? = null,
    val expiresAtTo: String? = null,
    val lastAccessedAtFrom: String? = null,
    val lastAccessedAtTo: String? = null,
    val lastReauthenticatedAtFrom: String? = null,
    val lastReauthenticatedAtTo: String? = null,
    val createdAtFrom: String? = null,
    val createdAtTo: String? = null,
    val updatedAtFrom: String? = null,
    val updatedAtTo: String? = null,
    val isUpdatedAtSet: Boolean? = null,
    val searchQuery: String? = null
) : NavigationScreenParams<ManagementGlobalSessionListParams> {

    override val parser: NavigationScreenParamsParser<ManagementGlobalSessionListParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<ManagementGlobalSessionListParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [ManagementGlobalSessionListParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [ManagementGlobalSessionListParams] instance.
         */
        override fun parse(params: Map<String, Any?>): ManagementGlobalSessionListParams {
            val normalized = params.normalizeQueryParams()

            val pageNumber = normalized[ListingParamNames.Pagination.PAGE_NUMBER]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it > 0 }
                ?: ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER

            val pageSize = normalized[ListingParamNames.Pagination.PAGE_SIZE]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it > 0 }
                ?: ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE

            val sortBy = normalized[ListingParamNames.Sort.SORT_BY]
                ?.firstOrNull()
                ?.let { UserSessionSortBy.fromValueOrNull(it) }
                ?: UserSessionSortBy.DEFAULT

            val sortOrder = normalized[ListingParamNames.Sort.SORT_ORDER]
                ?.firstOrNull()
                ?.let { SortOrder.fromValueOrNull(it) }
                ?: SortOrder.DEFAULT

            val sessionIds = normalized[UserSessionFilterValues.SESSION_ID]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val userIds = normalized[UserSessionFilterValues.USER_ID]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val userRoles = normalized[UserSessionFilterValues.USER_ROLE]
                ?.mapNotNull { UserRole.fromValueOrNull(it) }
                ?: emptyList()

            val excludeUserRoles = normalized[UserSessionFilterValues.EXCLUDE_USER_ROLE]
                ?.mapNotNull { UserRole.fromValueOrNull(it) }
                ?: emptyList()

            val identifier = normalized[UserSessionFilterValues.IDENTIFIER]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val identifierIds = normalized[UserSessionFilterValues.IDENTIFIER_ID]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val identifierDisplayName = normalized[UserSessionFilterValues.IDENTIFIER_DISPLAY_NAME]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val identifierAuthProviders = normalized[UserSessionFilterValues.IDENTIFIER_AUTH_PROVIDER]
                ?.mapNotNull { UserAuthProvider.fromValueOrNull(it) }
                ?: emptyList()

            val excludeIdentifierAuthProviders = normalized[UserSessionFilterValues.EXCLUDE_IDENTIFIER_AUTH_PROVIDER]
                ?.mapNotNull { UserAuthProvider.fromValueOrNull(it) }
                ?: emptyList()

            val clientTypes = normalized[UserSessionFilterValues.CLIENT_TYPE]
                ?.mapNotNull { ClientType.fromValueOrNull(it) }
                ?: emptyList()

            val excludeClientTypes = normalized[UserSessionFilterValues.EXCLUDE_CLIENT_TYPE]
                ?.mapNotNull { ClientType.fromValueOrNull(it) }
                ?: emptyList()

            val isClientTypeSet = normalized[UserSessionFilterValues.IS_CLIENT_TYPE_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val deviceId = normalized[UserSessionFilterValues.DEVICE_ID]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isDeviceIdSet = normalized[UserSessionFilterValues.IS_DEVICE_ID_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val deviceName = normalized[UserSessionFilterValues.DEVICE_NAME]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isDeviceNameSet = normalized[UserSessionFilterValues.IS_DEVICE_NAME_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val language = normalized[UserSessionFilterValues.LANGUAGE]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isLanguageSet = normalized[UserSessionFilterValues.IS_LANGUAGE_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val appVersion = normalized[UserSessionFilterValues.APP_VERSION]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isAppVersionSet = normalized[UserSessionFilterValues.IS_APP_VERSION_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val osVersion = normalized[UserSessionFilterValues.OPERATION_SYSTEM_VERSION]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isOperationSystemVersionSet = normalized[UserSessionFilterValues.IS_OPERATION_SYSTEM_VERSION_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val userAgent = normalized[UserSessionFilterValues.USER_AGENT]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isUserAgentSet = normalized[UserSessionFilterValues.IS_USER_AGENT_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val ipAddress = normalized[UserSessionFilterValues.IP_ADDRESS]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isIpAddressSet = normalized[UserSessionFilterValues.IS_IP_ADDRESS_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val expiresAtFrom = normalized[UserSessionFilterValues.EXPIRES_AT_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val expiresAtTo = normalized[UserSessionFilterValues.EXPIRES_AT_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val lastAccessedAtFrom = normalized[UserSessionFilterValues.LAST_ACCESSED_AT_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val lastAccessedAtTo = normalized[UserSessionFilterValues.LAST_ACCESSED_AT_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val lastReauthenticatedAtFrom = normalized[UserSessionFilterValues.LAST_REAUTHENTICATED_AT_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val lastReauthenticatedAtTo = normalized[UserSessionFilterValues.LAST_REAUTHENTICATED_AT_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val createdAtFrom = normalized[UserSessionFilterValues.CREATED_AT_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val createdAtTo = normalized[UserSessionFilterValues.CREATED_AT_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val updatedAtFrom = normalized[UserSessionFilterValues.UPDATED_AT_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val updatedAtTo = normalized[UserSessionFilterValues.UPDATED_AT_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isUpdatedAtSet = normalized[UserSessionFilterValues.IS_UPDATED_AT_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val searchQuery = normalized[UserSessionFilterValues.SEARCH_QUERY]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            return ManagementGlobalSessionListParams(
                pageNumber = pageNumber,
                pageSize = pageSize,
                sortBy = sortBy,
                sortOrder = sortOrder,
                sessionIds = sessionIds,
                userIds = userIds,
                userRoles = userRoles,
                excludeUserRoles = excludeUserRoles,
                identifier = identifier,
                identifierIds = identifierIds,
                identifierDisplayName = identifierDisplayName,
                identifierAuthProviders = identifierAuthProviders,
                excludeIdentifierAuthProviders = excludeIdentifierAuthProviders,
                clientTypes = clientTypes,
                excludeClientTypes = excludeClientTypes,
                isClientTypeSet = isClientTypeSet,
                deviceId = deviceId,
                isDeviceIdSet = isDeviceIdSet,
                deviceName = deviceName,
                isDeviceNameSet = isDeviceNameSet,
                language = language,
                isLanguageSet = isLanguageSet,
                appVersion = appVersion,
                isAppVersionSet = isAppVersionSet,
                osVersion = osVersion,
                isOperationSystemVersionSet = isOperationSystemVersionSet,
                userAgent = userAgent,
                isUserAgentSet = isUserAgentSet,
                ipAddress = ipAddress,
                isIpAddressSet = isIpAddressSet,
                expiresAtFrom = expiresAtFrom,
                expiresAtTo = expiresAtTo,
                lastAccessedAtFrom = lastAccessedAtFrom,
                lastAccessedAtTo = lastAccessedAtTo,
                lastReauthenticatedAtFrom = lastReauthenticatedAtFrom,
                lastReauthenticatedAtTo = lastReauthenticatedAtTo,
                createdAtFrom = createdAtFrom,
                createdAtTo = createdAtTo,
                updatedAtFrom = updatedAtFrom,
                updatedAtTo = updatedAtTo,
                isUpdatedAtSet = isUpdatedAtSet,
                searchQuery = searchQuery
            )
        }

        /**
         * Serializes [ManagementGlobalSessionListParams] into a map of raw URL parameters.
         *
         * @param params Typed [ManagementGlobalSessionListParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: ManagementGlobalSessionListParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()

            if (params.pageNumber != ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER) {
                result[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE) {
                result[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != UserSessionSortBy.DEFAULT) {
                result[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                result[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }
            if (params.sessionIds.isNotEmpty()) {
                result[UserSessionFilterValues.SESSION_ID] = params.sessionIds
            }
            if (params.userIds.isNotEmpty()) {
                result[UserSessionFilterValues.USER_ID] = params.userIds
            }
            if (params.userRoles.isNotEmpty()) {
                result[UserSessionFilterValues.USER_ROLE] = params.userRoles.map { it.value }
            }
            if (params.excludeUserRoles.isNotEmpty()) {
                result[UserSessionFilterValues.EXCLUDE_USER_ROLE] = params.excludeUserRoles.map { it.value }
            }
            if (!params.identifier.isNullOrBlank()) {
                result[UserSessionFilterValues.IDENTIFIER] = listOf(params.identifier.trim())
            }
            if (params.identifierIds.isNotEmpty()) {
                result[UserSessionFilterValues.IDENTIFIER_ID] = params.identifierIds
            }
            if (!params.identifierDisplayName.isNullOrBlank()) {
                result[UserSessionFilterValues.IDENTIFIER_DISPLAY_NAME] = listOf(params.identifierDisplayName.trim())
            }
            if (params.identifierAuthProviders.isNotEmpty()) {
                result[UserSessionFilterValues.IDENTIFIER_AUTH_PROVIDER] =
                    params.identifierAuthProviders.map { it.value }
            }
            if (params.excludeIdentifierAuthProviders.isNotEmpty()) {
                result[UserSessionFilterValues.EXCLUDE_IDENTIFIER_AUTH_PROVIDER] =
                    params.excludeIdentifierAuthProviders.map { it.value }
            }
            if (params.clientTypes.isNotEmpty()) {
                result[UserSessionFilterValues.CLIENT_TYPE] = params.clientTypes.map { it.value }
            }
            if (params.excludeClientTypes.isNotEmpty()) {
                result[UserSessionFilterValues.EXCLUDE_CLIENT_TYPE] = params.excludeClientTypes.map { it.value }
            }
            if (params.isClientTypeSet != null) {
                result[UserSessionFilterValues.IS_CLIENT_TYPE_SET] = listOf(params.isClientTypeSet.toString())
            }
            if (!params.deviceId.isNullOrBlank()) {
                result[UserSessionFilterValues.DEVICE_ID] = listOf(params.deviceId.trim())
            }
            if (params.isDeviceIdSet != null) {
                result[UserSessionFilterValues.IS_DEVICE_ID_SET] = listOf(params.isDeviceIdSet.toString())
            }
            if (!params.deviceName.isNullOrBlank()) {
                result[UserSessionFilterValues.DEVICE_NAME] = listOf(params.deviceName.trim())
            }
            if (params.isDeviceNameSet != null) {
                result[UserSessionFilterValues.IS_DEVICE_NAME_SET] = listOf(params.isDeviceNameSet.toString())
            }
            if (!params.language.isNullOrBlank()) {
                result[UserSessionFilterValues.LANGUAGE] = listOf(params.language.trim())
            }
            if (params.isLanguageSet != null) {
                result[UserSessionFilterValues.IS_LANGUAGE_SET] = listOf(params.isLanguageSet.toString())
            }
            if (!params.appVersion.isNullOrBlank()) {
                result[UserSessionFilterValues.APP_VERSION] = listOf(params.appVersion.trim())
            }
            if (params.isAppVersionSet != null) {
                result[UserSessionFilterValues.IS_APP_VERSION_SET] = listOf(params.isAppVersionSet.toString())
            }
            if (!params.osVersion.isNullOrBlank()) {
                result[UserSessionFilterValues.OPERATION_SYSTEM_VERSION] = listOf(params.osVersion.trim())
            }
            if (params.isOperationSystemVersionSet != null) {
                result[UserSessionFilterValues.IS_OPERATION_SYSTEM_VERSION_SET] =
                    listOf(params.isOperationSystemVersionSet.toString())
            }
            if (!params.userAgent.isNullOrBlank()) {
                result[UserSessionFilterValues.USER_AGENT] = listOf(params.userAgent.trim())
            }
            if (params.isUserAgentSet != null) {
                result[UserSessionFilterValues.IS_USER_AGENT_SET] = listOf(params.isUserAgentSet.toString())
            }
            if (!params.ipAddress.isNullOrBlank()) {
                result[UserSessionFilterValues.IP_ADDRESS] = listOf(params.ipAddress.trim())
            }
            if (params.isIpAddressSet != null) {
                result[UserSessionFilterValues.IS_IP_ADDRESS_SET] = listOf(params.isIpAddressSet.toString())
            }
            if (params.expiresAtFrom != null) {
                result[UserSessionFilterValues.EXPIRES_AT_FROM] = listOf(params.expiresAtFrom)
            }
            if (params.expiresAtTo != null) {
                result[UserSessionFilterValues.EXPIRES_AT_TO] = listOf(params.expiresAtTo)
            }
            if (params.lastAccessedAtFrom != null) {
                result[UserSessionFilterValues.LAST_ACCESSED_AT_FROM] = listOf(params.lastAccessedAtFrom)
            }
            if (params.lastAccessedAtTo != null) {
                result[UserSessionFilterValues.LAST_ACCESSED_AT_TO] = listOf(params.lastAccessedAtTo)
            }
            if (params.lastReauthenticatedAtFrom != null) {
                result[UserSessionFilterValues.LAST_REAUTHENTICATED_AT_FROM] = listOf(params.lastReauthenticatedAtFrom)
            }
            if (params.lastReauthenticatedAtTo != null) {
                result[UserSessionFilterValues.LAST_REAUTHENTICATED_AT_TO] = listOf(params.lastReauthenticatedAtTo)
            }
            if (params.createdAtFrom != null) {
                result[UserSessionFilterValues.CREATED_AT_FROM] = listOf(params.createdAtFrom)
            }
            if (params.createdAtTo != null) {
                result[UserSessionFilterValues.CREATED_AT_TO] = listOf(params.createdAtTo)
            }
            if (params.updatedAtFrom != null) {
                result[UserSessionFilterValues.UPDATED_AT_FROM] = listOf(params.updatedAtFrom)
            }
            if (params.updatedAtTo != null) {
                result[UserSessionFilterValues.UPDATED_AT_TO] = listOf(params.updatedAtTo)
            }
            if (params.isUpdatedAtSet != null) {
                result[UserSessionFilterValues.IS_UPDATED_AT_SET] = listOf(params.isUpdatedAtSet.toString())
            }
            if (params.searchQuery != null) {
                result[UserSessionFilterValues.SEARCH_QUERY] = listOf(params.searchQuery)
            }

            return result
        }
    }
}
