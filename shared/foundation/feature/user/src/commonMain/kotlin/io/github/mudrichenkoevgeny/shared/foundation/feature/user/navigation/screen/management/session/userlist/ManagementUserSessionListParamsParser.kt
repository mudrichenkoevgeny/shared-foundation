package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.userlist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSessionSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Bidirectional parser and serializer for [ManagementUserSessionListParams] Deep Link and Web Router URL parameters.
 */
object ManagementUserSessionListParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [ManagementUserSessionListParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [ManagementUserSessionListParams] instance.
     */
    fun parse(params: Map<String, Any?>): ManagementUserSessionListParams {
        val normalized = params.normalizeQueryParams()

        val userIds = normalized[UserFilterValues.UserSessionFilterValues.USER_ID]?.filter { it.isNotBlank() } ?: emptyList()

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

        val userRoles = normalized[UserFilterValues.UserSessionFilterValues.USER_ROLE]?.mapNotNull { UserRole.fromValueOrNull(it) } ?: emptyList()
        val identifiers = normalized[UserFilterValues.UserSessionFilterValues.IDENTIFIER]?.filter { it.isNotBlank() } ?: emptyList()
        val identifierIds = normalized[UserFilterValues.UserSessionFilterValues.IDENTIFIER_ID]?.filter { it.isNotBlank() } ?: emptyList()
        val userAuthProviders = normalized[UserFilterValues.UserSessionFilterValues.USER_AUTH_PROVIDER]?.mapNotNull { UserAuthProvider.fromValueOrNull(it) } ?: emptyList()
        val clientTypes = normalized[UserFilterValues.UserSessionFilterValues.CLIENT_TYPE]?.mapNotNull { ClientType.fromValueOrNull(it) } ?: emptyList()
        val userAgents = normalized[UserFilterValues.UserSessionFilterValues.USER_AGENT]?.filter { it.isNotBlank() } ?: emptyList()
        val ipAddresses = normalized[UserFilterValues.UserSessionFilterValues.IP_ADDRESS]?.filter { it.isNotBlank() } ?: emptyList()
        val languages = normalized[UserFilterValues.UserSessionFilterValues.LANGUAGE]?.filter { it.isNotBlank() } ?: emptyList()
        val deviceIds = normalized[UserFilterValues.UserSessionFilterValues.DEVICE_ID]?.filter { it.isNotBlank() } ?: emptyList()
        val deviceNames = normalized[UserFilterValues.UserSessionFilterValues.DEVICE_NAME]?.filter { it.isNotBlank() } ?: emptyList()
        val appVersions = normalized[UserFilterValues.UserSessionFilterValues.APP_VERSION]?.filter { it.isNotBlank() } ?: emptyList()
        val osVersions = normalized[UserFilterValues.UserSessionFilterValues.OPERATION_SYSTEM_VERSION]?.filter { it.isNotBlank() } ?: emptyList()

        return ManagementUserSessionListParams(
            userIds = userIds,
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
     * @param params Typed [ManagementUserSessionListParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: ManagementUserSessionListParams): Map<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()

        if (params.userIds.isNotEmpty()) {
            result[UserFilterValues.UserSessionFilterValues.USER_ID] = params.userIds
        }
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
