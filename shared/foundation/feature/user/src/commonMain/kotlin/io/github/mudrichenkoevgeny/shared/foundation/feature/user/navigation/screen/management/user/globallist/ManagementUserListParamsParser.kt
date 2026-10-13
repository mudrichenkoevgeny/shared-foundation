package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortValues.UserSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/**
 * Bidirectional parser and serializer for [ManagementUserListParams] Deep Link and Web Router URL parameters.
 */
object ManagementUserListParamsParser {

    /**
     * Parses raw Deep Link or Web Router query parameters into typed [ManagementUserListParams].
     *
     * @param params Map of parameter keys to raw parameter values.
     *
     * @return Validated [ManagementUserListParams] instance.
     */
    fun parse(params: Map<String, Any?>): ManagementUserListParams {
        val normalized = params.normalizeQueryParams()

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
            ?.let { UserSortBy.fromValueOrNull(it) }
            ?: UserSortBy.DEFAULT

        val sortOrder = normalized[ListingParamNames.Sort.SORT_ORDER]
            ?.firstOrNull()
            ?.let { SortOrder.fromValueOrNull(it) }
            ?: SortOrder.DEFAULT

        val userRoles = normalized[UserFilterValues.UserFilterValues.ROLE]
            ?.mapNotNull { UserRole.fromValueOrNull(it) }
            ?: emptyList()

        val accountStatuses = normalized[UserFilterValues.UserFilterValues.ACCOUNT_STATUS]
            ?.mapNotNull { UserAccountStatus.fromValueOrNull(it) }
            ?: emptyList()

        val accountStatusesOnRestore = normalized[UserFilterValues.UserFilterValues.ACCOUNT_STATUS_ON_RESTORE]
            ?.mapNotNull { UserAccountStatus.fromValueOrNull(it) }
            ?: emptyList()

        val accountLockoutTypes = normalized[UserFilterValues.UserFilterValues.ACCOUNT_LOCKOUT_TYPE]
            ?.mapNotNull { AccountLockoutType.fromValueOrNull(it) }
            ?: emptyList()

        val authorityLevelFrom = normalized[UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_FROM]
            ?.firstOrNull()
            ?.toIntOrNull()

        val authorityLevelTo = normalized[UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_TO]
            ?.firstOrNull()
            ?.toIntOrNull()

        val isTotpEnabled = normalized[UserFilterValues.UserFilterValues.IS_TOTP_ENABLED]
            ?.firstOrNull()
            ?.toBooleanStrictOrNull()

        val permissionCodes = normalized[UserFilterValues.UserFilterValues.PERMISSION_CODES]
            ?.filter { it.isNotBlank() }
            ?: emptyList()

        return ManagementUserListParams(
            pageNumber = pageNumber,
            pageSize = pageSize,
            sortBy = sortBy,
            sortOrder = sortOrder,
            userRoles = userRoles,
            accountStatuses = accountStatuses,
            accountStatusesOnRestore = accountStatusesOnRestore,
            accountLockoutTypes = accountLockoutTypes,
            authorityLevelFrom = authorityLevelFrom,
            authorityLevelTo = authorityLevelTo,
            isTotpEnabled = isTotpEnabled,
            permissionCodes = permissionCodes
        )
    }

    /**
     * Serializes [ManagementUserListParams] into a map of raw URL parameters.
     *
     * @param params Typed [ManagementUserListParams] to serialize.
     *
     * @return Map of parameter names to string lists suitable for building URL search strings.
     */
    fun serialize(params: ManagementUserListParams): Map<String, List<String>> {
        val result = mutableMapOf<String, List<String>>()

        if (params.pageNumber != ListingDefaultValues.Pagination.PAGE_NUMBER) {
            result[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
        }
        if (params.pageSize != ListingDefaultValues.Pagination.PAGE_SIZE) {
            result[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
        }
        if (params.sortBy != UserSortBy.DEFAULT) {
            result[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
        }
        if (params.sortOrder != SortOrder.DEFAULT) {
            result[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
        }
        if (params.userRoles.isNotEmpty()) {
            result[UserFilterValues.UserFilterValues.ROLE] = params.userRoles.map { it.value }
        }
        if (params.accountStatuses.isNotEmpty()) {
            result[UserFilterValues.UserFilterValues.ACCOUNT_STATUS] = params.accountStatuses.map { it.value }
        }
        if (params.accountStatusesOnRestore.isNotEmpty()) {
            result[UserFilterValues.UserFilterValues.ACCOUNT_STATUS_ON_RESTORE] = params.accountStatusesOnRestore.map { it.value }
        }
        if (params.accountLockoutTypes.isNotEmpty()) {
            result[UserFilterValues.UserFilterValues.ACCOUNT_LOCKOUT_TYPE] = params.accountLockoutTypes.map { it.value }
        }
        if (params.authorityLevelFrom != null) {
            result[UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_FROM] = listOf(params.authorityLevelFrom.toString())
        }
        if (params.authorityLevelTo != null) {
            result[UserFilterValues.UserFilterValues.AUTHORITY_LEVEL_TO] = listOf(params.authorityLevelTo.toString())
        }
        if (params.isTotpEnabled != null) {
            result[UserFilterValues.UserFilterValues.IS_TOTP_ENABLED] = listOf(params.isTotpEnabled.toString())
        }
        if (params.permissionCodes.isNotEmpty()) {
            result[UserFilterValues.UserFilterValues.PERMISSION_CODES] = params.permissionCodes
        }

        return result
    }
}
