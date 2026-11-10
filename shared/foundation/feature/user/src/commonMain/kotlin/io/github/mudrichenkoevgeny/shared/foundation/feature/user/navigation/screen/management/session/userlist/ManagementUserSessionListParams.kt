package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseBoolean
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseEnumList
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseNonBlankString
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseStringList
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSessionSortBy
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
 * @property sessionIds Specific session IDs to filter by; wire key is [UserSessionFilterValues.SESSION_ID].
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
public data class ManagementUserSessionListParams(
    public val userId: String = "",
    public val pageNumber: Int = ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
    public val pageSize: Int = ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
    public val sortBy: UserSessionSortBy = UserSessionSortBy.DEFAULT,
    public val sortOrder: SortOrder = SortOrder.DEFAULT,
    public val sessionIds: List<String> = emptyList(),
    public val userRoles: List<UserRole> = emptyList(),
    public val excludeUserRoles: List<UserRole> = emptyList(),
    public val identifier: String? = null,
    public val identifierIds: List<String> = emptyList(),
    public val identifierDisplayName: String? = null,
    public val identifierAuthProviders: List<UserAuthProvider> = emptyList(),
    public val excludeIdentifierAuthProviders: List<UserAuthProvider> = emptyList(),
    public val clientTypes: List<ClientType> = emptyList(),
    public val excludeClientTypes: List<ClientType> = emptyList(),
    public val isClientTypeSet: Boolean? = null,
    public val deviceId: String? = null,
    public val isDeviceIdSet: Boolean? = null,
    public val deviceName: String? = null,
    public val isDeviceNameSet: Boolean? = null,
    public val language: String? = null,
    public val isLanguageSet: Boolean? = null,
    public val appVersion: String? = null,
    public val isAppVersionSet: Boolean? = null,
    public val osVersion: String? = null,
    public val isOperationSystemVersionSet: Boolean? = null,
    public val userAgent: String? = null,
    public val isUserAgentSet: Boolean? = null,
    public val ipAddress: String? = null,
    public val isIpAddressSet: Boolean? = null,
    public val expiresAtFrom: String? = null,
    public val expiresAtTo: String? = null,
    public val lastAccessedAtFrom: String? = null,
    public val lastAccessedAtTo: String? = null,
    public val lastReauthenticatedAtFrom: String? = null,
    public val lastReauthenticatedAtTo: String? = null,
    public val createdAtFrom: String? = null,
    public val createdAtTo: String? = null,
    public val updatedAtFrom: String? = null,
    public val updatedAtTo: String? = null,
    public val isUpdatedAtSet: Boolean? = null,
    public val searchQuery: String? = null
) : NavigationScreenParams<ManagementUserSessionListParams> {
    /**
     * Parsed strongly typed [UserId] representation of [userId].
     *
     * Returns `null` if [userId] is blank or does not conform to the canonical UUID format.
     */
    public val typedUserId: UserId?
        get() = userId.toUserIdOrNull()

    override val parser: NavigationScreenParamsParser<ManagementUserSessionListParams>
        get() = Companion

    public companion object : NavigationScreenParamsParser<ManagementUserSessionListParams> {
        /**
         * Parses raw Deep Link or Web Router query parameters into typed [ManagementUserSessionListParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [ManagementUserSessionListParams] instance.
         */
        override fun parse(params: Map<String, Any?>): ManagementUserSessionListParams {
            val normalized = params.normalizeQueryParams()
            return ManagementUserSessionListParams(
                userId = normalized[UserApiPaths.USER_ID]?.firstOrNull()?.trim() ?: "",
                pageNumber =
                    normalized[ListingParamNames.Pagination.PAGE_NUMBER]
                        ?.firstOrNull()
                        ?.toIntOrNull()
                        ?.takeIf { it > 0 }
                        ?: ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
                pageSize =
                    normalized[ListingParamNames.Pagination.PAGE_SIZE]
                        ?.firstOrNull()
                        ?.toIntOrNull()
                        ?.takeIf { it > 0 }
                        ?: ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
                sortBy =
                    normalized[ListingParamNames.Sort.SORT_BY]
                        ?.firstOrNull()
                        ?.let { UserSessionSortBy.fromValueOrNull(it) }
                        ?: UserSessionSortBy.DEFAULT,
                sortOrder =
                    normalized[ListingParamNames.Sort.SORT_ORDER]
                        ?.firstOrNull()
                        ?.let { SortOrder.fromValueOrNull(it) }
                        ?: SortOrder.DEFAULT,
                sessionIds = normalized.parseStringList(UserSessionFilterValues.SESSION_ID),
                userRoles = normalized.parseEnumList(UserSessionFilterValues.USER_ROLE) { UserRole.fromValueOrNull(it) },
                excludeUserRoles = normalized.parseEnumList(UserSessionFilterValues.EXCLUDE_USER_ROLE) { UserRole.fromValueOrNull(it) },
                identifier = normalized.parseNonBlankString(UserSessionFilterValues.IDENTIFIER),
                identifierIds = normalized.parseStringList(UserSessionFilterValues.IDENTIFIER_ID),
                identifierDisplayName = normalized.parseNonBlankString(UserSessionFilterValues.IDENTIFIER_DISPLAY_NAME),
                identifierAuthProviders = normalized.parseEnumList(UserSessionFilterValues.IDENTIFIER_AUTH_PROVIDER) { UserAuthProvider.fromValueOrNull(it) },
                excludeIdentifierAuthProviders = normalized.parseEnumList(UserSessionFilterValues.EXCLUDE_IDENTIFIER_AUTH_PROVIDER) { UserAuthProvider.fromValueOrNull(it) },
                clientTypes = normalized.parseEnumList(UserSessionFilterValues.CLIENT_TYPE) { ClientType.fromValueOrNull(it) },
                excludeClientTypes = normalized.parseEnumList(UserSessionFilterValues.EXCLUDE_CLIENT_TYPE) { ClientType.fromValueOrNull(it) },
                isClientTypeSet = normalized.parseBoolean(UserSessionFilterValues.IS_CLIENT_TYPE_SET),
                deviceId = normalized.parseNonBlankString(UserSessionFilterValues.DEVICE_ID),
                isDeviceIdSet = normalized.parseBoolean(UserSessionFilterValues.IS_DEVICE_ID_SET),
                deviceName = normalized.parseNonBlankString(UserSessionFilterValues.DEVICE_NAME),
                isDeviceNameSet = normalized.parseBoolean(UserSessionFilterValues.IS_DEVICE_NAME_SET),
                language = normalized.parseNonBlankString(UserSessionFilterValues.LANGUAGE),
                isLanguageSet = normalized.parseBoolean(UserSessionFilterValues.IS_LANGUAGE_SET),
                appVersion = normalized.parseNonBlankString(UserSessionFilterValues.APP_VERSION),
                isAppVersionSet = normalized.parseBoolean(UserSessionFilterValues.IS_APP_VERSION_SET),
                osVersion = normalized.parseNonBlankString(UserSessionFilterValues.OPERATION_SYSTEM_VERSION),
                isOperationSystemVersionSet = normalized.parseBoolean(UserSessionFilterValues.IS_OPERATION_SYSTEM_VERSION_SET),
                userAgent = normalized.parseNonBlankString(UserSessionFilterValues.USER_AGENT),
                isUserAgentSet = normalized.parseBoolean(UserSessionFilterValues.IS_USER_AGENT_SET),
                ipAddress = normalized.parseNonBlankString(UserSessionFilterValues.IP_ADDRESS),
                isIpAddressSet = normalized.parseBoolean(UserSessionFilterValues.IS_IP_ADDRESS_SET),
                expiresAtFrom = normalized.parseNonBlankString(UserSessionFilterValues.EXPIRES_AT_FROM),
                expiresAtTo = normalized.parseNonBlankString(UserSessionFilterValues.EXPIRES_AT_TO),
                lastAccessedAtFrom = normalized.parseNonBlankString(UserSessionFilterValues.LAST_ACCESSED_AT_FROM),
                lastAccessedAtTo = normalized.parseNonBlankString(UserSessionFilterValues.LAST_ACCESSED_AT_TO),
                lastReauthenticatedAtFrom = normalized.parseNonBlankString(UserSessionFilterValues.LAST_REAUTHENTICATED_AT_FROM),
                lastReauthenticatedAtTo = normalized.parseNonBlankString(UserSessionFilterValues.LAST_REAUTHENTICATED_AT_TO),
                createdAtFrom = normalized.parseNonBlankString(UserSessionFilterValues.CREATED_AT_FROM),
                createdAtTo = normalized.parseNonBlankString(UserSessionFilterValues.CREATED_AT_TO),
                updatedAtFrom = normalized.parseNonBlankString(UserSessionFilterValues.UPDATED_AT_FROM),
                updatedAtTo = normalized.parseNonBlankString(UserSessionFilterValues.UPDATED_AT_TO),
                isUpdatedAtSet = normalized.parseBoolean(UserSessionFilterValues.IS_UPDATED_AT_SET),
                searchQuery = normalized.parseNonBlankString(UserSessionFilterValues.SEARCH_QUERY)
            )
        }

        /**
         * Serializes [ManagementUserSessionListParams] into a map of raw URL query parameter strings.
         *
         * Note that [ManagementUserSessionListParams.userId] is omitted as it is represented
         * as a path parameter in the route URL rather than a query parameter.
         *
         * @param params Typed [ManagementUserSessionListParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: ManagementUserSessionListParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()
            result.serializePaginationAndSorting(params)
            result.serializeUserAndIdentifierFilters(params)
            result.serializeClientAndAppFilters(params)
            result.serializeSessionTimestampFilters(params)
            return result
        }

        private fun MutableMap<String, List<String>>.serializePaginationAndSorting(params: ManagementUserSessionListParams) {
            if (params.pageNumber != ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER) {
                this[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE) {
                this[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != UserSessionSortBy.DEFAULT) {
                this[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                this[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }
        }

        private fun MutableMap<String, List<String>>.serializeUserAndIdentifierFilters(params: ManagementUserSessionListParams) {
            if (params.sessionIds.isNotEmpty()) {
                this[UserSessionFilterValues.SESSION_ID] = params.sessionIds
            }
            if (params.userRoles.isNotEmpty()) {
                this[UserSessionFilterValues.USER_ROLE] = params.userRoles.map { it.value }
            }
            if (params.excludeUserRoles.isNotEmpty()) {
                this[UserSessionFilterValues.EXCLUDE_USER_ROLE] = params.excludeUserRoles.map { it.value }
            }
            if (!params.identifier.isNullOrBlank()) {
                this[UserSessionFilterValues.IDENTIFIER] = listOf(params.identifier.trim())
            }
            if (params.identifierIds.isNotEmpty()) {
                this[UserSessionFilterValues.IDENTIFIER_ID] = params.identifierIds
            }
            if (!params.identifierDisplayName.isNullOrBlank()) {
                this[UserSessionFilterValues.IDENTIFIER_DISPLAY_NAME] = listOf(params.identifierDisplayName.trim())
            }
            if (params.identifierAuthProviders.isNotEmpty()) {
                this[UserSessionFilterValues.IDENTIFIER_AUTH_PROVIDER] =
                    params.identifierAuthProviders.map { it.value }
            }
            if (params.excludeIdentifierAuthProviders.isNotEmpty()) {
                this[UserSessionFilterValues.EXCLUDE_IDENTIFIER_AUTH_PROVIDER] =
                    params.excludeIdentifierAuthProviders.map { it.value }
            }
            if (params.clientTypes.isNotEmpty()) {
                this[UserSessionFilterValues.CLIENT_TYPE] = params.clientTypes.map { it.value }
            }
            if (params.excludeClientTypes.isNotEmpty()) {
                this[UserSessionFilterValues.EXCLUDE_CLIENT_TYPE] = params.excludeClientTypes.map { it.value }
            }
            if (params.isClientTypeSet != null) {
                this[UserSessionFilterValues.IS_CLIENT_TYPE_SET] = listOf(params.isClientTypeSet.toString())
            }
            if (!params.deviceId.isNullOrBlank()) {
                this[UserSessionFilterValues.DEVICE_ID] = listOf(params.deviceId.trim())
            }
            if (params.isDeviceIdSet != null) {
                this[UserSessionFilterValues.IS_DEVICE_ID_SET] = listOf(params.isDeviceIdSet.toString())
            }
            if (!params.deviceName.isNullOrBlank()) {
                this[UserSessionFilterValues.DEVICE_NAME] = listOf(params.deviceName.trim())
            }
            if (params.isDeviceNameSet != null) {
                this[UserSessionFilterValues.IS_DEVICE_NAME_SET] = listOf(params.isDeviceNameSet.toString())
            }
        }

        private fun MutableMap<String, List<String>>.serializeClientAndAppFilters(params: ManagementUserSessionListParams) {
            if (!params.language.isNullOrBlank()) {
                this[UserSessionFilterValues.LANGUAGE] = listOf(params.language.trim())
            }
            if (params.isLanguageSet != null) {
                this[UserSessionFilterValues.IS_LANGUAGE_SET] = listOf(params.isLanguageSet.toString())
            }
            if (!params.appVersion.isNullOrBlank()) {
                this[UserSessionFilterValues.APP_VERSION] = listOf(params.appVersion.trim())
            }
            if (params.isAppVersionSet != null) {
                this[UserSessionFilterValues.IS_APP_VERSION_SET] = listOf(params.isAppVersionSet.toString())
            }
            if (!params.osVersion.isNullOrBlank()) {
                this[UserSessionFilterValues.OPERATION_SYSTEM_VERSION] = listOf(params.osVersion.trim())
            }
            if (params.isOperationSystemVersionSet != null) {
                this[UserSessionFilterValues.IS_OPERATION_SYSTEM_VERSION_SET] =
                    listOf(params.isOperationSystemVersionSet.toString())
            }
            if (!params.userAgent.isNullOrBlank()) {
                this[UserSessionFilterValues.USER_AGENT] = listOf(params.userAgent.trim())
            }
            if (params.isUserAgentSet != null) {
                this[UserSessionFilterValues.IS_USER_AGENT_SET] = listOf(params.isUserAgentSet.toString())
            }
            if (!params.ipAddress.isNullOrBlank()) {
                this[UserSessionFilterValues.IP_ADDRESS] = listOf(params.ipAddress.trim())
            }
            if (params.isIpAddressSet != null) {
                this[UserSessionFilterValues.IS_IP_ADDRESS_SET] = listOf(params.isIpAddressSet.toString())
            }
        }

        private fun MutableMap<String, List<String>>.serializeSessionTimestampFilters(params: ManagementUserSessionListParams) {
            if (params.expiresAtFrom != null) {
                this[UserSessionFilterValues.EXPIRES_AT_FROM] = listOf(params.expiresAtFrom)
            }
            if (params.expiresAtTo != null) {
                this[UserSessionFilterValues.EXPIRES_AT_TO] = listOf(params.expiresAtTo)
            }
            if (params.lastAccessedAtFrom != null) {
                this[UserSessionFilterValues.LAST_ACCESSED_AT_FROM] = listOf(params.lastAccessedAtFrom)
            }
            if (params.lastAccessedAtTo != null) {
                this[UserSessionFilterValues.LAST_ACCESSED_AT_TO] = listOf(params.lastAccessedAtTo)
            }
            if (params.lastReauthenticatedAtFrom != null) {
                this[UserSessionFilterValues.LAST_REAUTHENTICATED_AT_FROM] = listOf(params.lastReauthenticatedAtFrom)
            }
            if (params.lastReauthenticatedAtTo != null) {
                this[UserSessionFilterValues.LAST_REAUTHENTICATED_AT_TO] = listOf(params.lastReauthenticatedAtTo)
            }
            if (params.createdAtFrom != null) {
                this[UserSessionFilterValues.CREATED_AT_FROM] = listOf(params.createdAtFrom)
            }
            if (params.createdAtTo != null) {
                this[UserSessionFilterValues.CREATED_AT_TO] = listOf(params.createdAtTo)
            }
            if (params.updatedAtFrom != null) {
                this[UserSessionFilterValues.UPDATED_AT_FROM] = listOf(params.updatedAtFrom)
            }
            if (params.updatedAtTo != null) {
                this[UserSessionFilterValues.UPDATED_AT_TO] = listOf(params.updatedAtTo)
            }
            if (params.isUpdatedAtSet != null) {
                this[UserSessionFilterValues.IS_UPDATED_AT_SET] = listOf(params.isUpdatedAtSet.toString())
            }
            if (params.searchQuery != null) {
                this[UserSessionFilterValues.SEARCH_QUERY] = listOf(params.searchQuery)
            }
        }
    }
}
