package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserSortBy
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the global management user list screen and API query.
 *
 * @property pageNumber One-based page index; wire key is [ListingParamNames.Pagination.PAGE_NUMBER].
 * @property pageSize Maximum items returned per page; wire key is [ListingParamNames.Pagination.PAGE_SIZE].
 * @property sortBy Field to sort listing results by; wire key is [ListingParamNames.Sort.SORT_BY].
 * @property sortOrder Direction of sorting; wire key is [ListingParamNames.Sort.SORT_ORDER].
 * @property userIds Specific user IDs to filter by; wire key is [UserFilterValues.USER_ID].
 * @property userRoles List of [UserRole] items to filter by; wire key is [UserFilterValues.ROLE].
 * @property excludeUserRoles Excluded [UserRole] items; wire key is [UserFilterValues.EXCLUDE_ROLE].
 * @property accountStatuses List of [UserAccountStatus] items to filter by; wire key is [UserFilterValues.ACCOUNT_STATUS].
 * @property excludeAccountStatuses Excluded [UserAccountStatus] items; wire key is [UserFilterValues.EXCLUDE_ACCOUNT_STATUS].
 * @property accountStatusesOnRestore Target [UserAccountStatus] items on restore; wire key is [UserFilterValues.ACCOUNT_STATUS_ON_RESTORE].
 * @property excludeAccountStatusesOnRestore Excluded target [UserAccountStatus] items on restore; wire key is [UserFilterValues.EXCLUDE_ACCOUNT_STATUS_ON_RESTORE].
 * @property isAccountStatusOnRestoreSet Filter by target status on restore presence; wire key is [UserFilterValues.IS_ACCOUNT_STATUS_ON_RESTORE_SET].
 * @property authorityLevelFrom Lower bound for authority level; wire key is [UserFilterValues.AUTHORITY_LEVEL_FROM].
 * @property authorityLevelTo Upper bound for authority level; wire key is [UserFilterValues.AUTHORITY_LEVEL_TO].
 * @property permissionCodes Explicit permission code strings to include (OR); wire key is [UserFilterValues.PERMISSION_CODES].
 * @property excludePermissionCodes Excluded permission code strings; wire key is [UserFilterValues.EXCLUDE_PERMISSION_CODES].
 * @property isTotpEnabled Filter by TOTP status; wire key is [UserFilterValues.IS_TOTP_ENABLED].
 * @property lastLoginAtFrom Lower bound last login timestamp; wire key is [UserFilterValues.LAST_LOGIN_AT_FROM].
 * @property lastLoginAtTo Upper bound last login timestamp; wire key is [UserFilterValues.LAST_LOGIN_AT_TO].
 * @property isLastLoginAtSet Filter by login activity presence; wire key is [UserFilterValues.IS_LAST_LOGIN_AT_SET].
 * @property lastActiveAtFrom Lower bound last active timestamp; wire key is [UserFilterValues.LAST_ACTIVE_AT_FROM].
 * @property lastActiveAtTo Upper bound last active timestamp; wire key is [UserFilterValues.LAST_ACTIVE_AT_TO].
 * @property isLastActiveAtSet Filter by user activity presence; wire key is [UserFilterValues.IS_LAST_ACTIVE_AT_SET].
 * @property createdAtFrom Lower bound creation timestamp; wire key is [UserFilterValues.CREATED_AT_FROM].
 * @property createdAtTo Upper bound creation timestamp; wire key is [UserFilterValues.CREATED_AT_TO].
 * @property updatedAtFrom Lower bound update timestamp; wire key is [UserFilterValues.UPDATED_AT_FROM].
 * @property updatedAtTo Upper bound update timestamp; wire key is [UserFilterValues.UPDATED_AT_TO].
 * @property isUpdatedAtSet Filter by update presence; wire key is [UserFilterValues.IS_UPDATED_AT_SET].
 * @property scheduledPermanentDeletionAtFrom Lower bound scheduled deletion timestamp; wire key is [UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_FROM].
 * @property scheduledPermanentDeletionAtTo Upper bound scheduled deletion timestamp; wire key is [UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_TO].
 * @property isScheduledPermanentDeletionAtSet Filter by scheduled deletion presence; wire key is [UserFilterValues.IS_SCHEDULED_PERMANENT_DELETION_AT_SET].
 * @property accountLockoutTypes List of [AccountLockoutType] items to filter by; wire key is [UserFilterValues.ACCOUNT_LOCKOUT_TYPE].
 * @property excludeAccountLockoutTypes Excluded [AccountLockoutType] items; wire key is [UserFilterValues.EXCLUDE_ACCOUNT_LOCKOUT_TYPE].
 * @property temporaryLockoutUntilFrom Lower bound temporary lockout timestamp; wire key is [UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_FROM].
 * @property temporaryLockoutUntilTo Upper bound temporary lockout timestamp; wire key is [UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_TO].
 * @property isTemporaryLockoutUntilSet Filter by temporary lockout presence; wire key is [UserFilterValues.IS_TEMPORARY_LOCKOUT_UNTIL_SET].
 * @property storageQuotaBytesFrom Lower bound custom storage quota in bytes; wire key is [UserFilterValues.STORAGE_QUOTA_BYTES_FROM].
 * @property storageQuotaBytesTo Upper bound custom storage quota in bytes; wire key is [UserFilterValues.STORAGE_QUOTA_BYTES_TO].
 * @property isStorageQuotaBytesSet Filter by custom storage quota presence; wire key is [UserFilterValues.IS_STORAGE_QUOTA_BYTES_SET].
 * @property storageMaxUploadSizeBytesFrom Lower bound custom max upload size in bytes; wire key is [UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM].
 * @property storageMaxUploadSizeBytesTo Upper bound custom max upload size in bytes; wire key is [UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_TO].
 * @property isStorageMaxUploadSizeBytesSet Filter by custom max upload size presence; wire key is [UserFilterValues.IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET].
 * @property storageMaxPendingUploadsFrom Lower bound custom max pending uploads count; wire key is [UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_FROM].
 * @property storageMaxPendingUploadsTo Upper bound custom max pending uploads count; wire key is [UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_TO].
 * @property isStorageMaxPendingUploadsSet Filter by custom max pending uploads presence; wire key is [UserFilterValues.IS_STORAGE_MAX_PENDING_UPLOADS_SET].
 * @property isStorageConfigurationSet Filter by custom storage configuration presence; wire key is [UserFilterValues.IS_STORAGE_CONFIGURATION_SET].
 * @property nickname Exact nickname handle; wire key is [UserFilterValues.NICKNAME].
 * @property isNicknameSet Filter by nickname presence; wire key is [UserFilterValues.IS_NICKNAME_SET].
 * @property displayName Exact display name; wire key is [UserFilterValues.DISPLAY_NAME].
 * @property isDisplayNameSet Filter by display name presence; wire key is [UserFilterValues.IS_DISPLAY_NAME_SET].
 * @property isAvatarSet Filter by avatar image presence; wire key is [UserFilterValues.IS_AVATAR_SET].
 * @property birthdateFrom Lower bound user birthdate; wire key is [UserFilterValues.BIRTHDATE_FROM].
 * @property birthdateTo Upper bound user birthdate; wire key is [UserFilterValues.BIRTHDATE_TO].
 * @property isBirthdateSet Filter by birthdate presence; wire key is [UserFilterValues.IS_BIRTHDATE_SET].
 * @property searchQuery Search query targeting nickname and display name; wire key is [UserFilterValues.SEARCH_QUERY].
 */
@Serializable
data class ManagementUserListParams(
    val pageNumber: Int = ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
    val pageSize: Int = ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
    val sortBy: UserSortBy = UserSortBy.DEFAULT,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val userIds: List<String> = emptyList(),
    val userRoles: List<UserRole> = emptyList(),
    val excludeUserRoles: List<UserRole> = emptyList(),
    val accountStatuses: List<UserAccountStatus> = emptyList(),
    val excludeAccountStatuses: List<UserAccountStatus> = emptyList(),
    val accountStatusesOnRestore: List<UserAccountStatus> = emptyList(),
    val excludeAccountStatusesOnRestore: List<UserAccountStatus> = emptyList(),
    val isAccountStatusOnRestoreSet: Boolean? = null,
    val authorityLevelFrom: Int? = null,
    val authorityLevelTo: Int? = null,
    val permissionCodes: List<String> = emptyList(),
    val excludePermissionCodes: List<String> = emptyList(),
    val isTotpEnabled: Boolean? = null,
    val lastLoginAtFrom: String? = null,
    val lastLoginAtTo: String? = null,
    val isLastLoginAtSet: Boolean? = null,
    val lastActiveAtFrom: String? = null,
    val lastActiveAtTo: String? = null,
    val isLastActiveAtSet: Boolean? = null,
    val createdAtFrom: String? = null,
    val createdAtTo: String? = null,
    val updatedAtFrom: String? = null,
    val updatedAtTo: String? = null,
    val isUpdatedAtSet: Boolean? = null,
    val scheduledPermanentDeletionAtFrom: String? = null,
    val scheduledPermanentDeletionAtTo: String? = null,
    val isScheduledPermanentDeletionAtSet: Boolean? = null,
    val accountLockoutTypes: List<AccountLockoutType> = emptyList(),
    val excludeAccountLockoutTypes: List<AccountLockoutType> = emptyList(),
    val temporaryLockoutUntilFrom: String? = null,
    val temporaryLockoutUntilTo: String? = null,
    val isTemporaryLockoutUntilSet: Boolean? = null,
    val storageQuotaBytesFrom: Long? = null,
    val storageQuotaBytesTo: Long? = null,
    val isStorageQuotaBytesSet: Boolean? = null,
    val storageMaxUploadSizeBytesFrom: Long? = null,
    val storageMaxUploadSizeBytesTo: Long? = null,
    val isStorageMaxUploadSizeBytesSet: Boolean? = null,
    val storageMaxPendingUploadsFrom: Long? = null,
    val storageMaxPendingUploadsTo: Long? = null,
    val isStorageMaxPendingUploadsSet: Boolean? = null,
    val isStorageConfigurationSet: Boolean? = null,
    val nickname: String? = null,
    val isNicknameSet: Boolean? = null,
    val displayName: String? = null,
    val isDisplayNameSet: Boolean? = null,
    val isAvatarSet: Boolean? = null,
    val birthdateFrom: String? = null,
    val birthdateTo: String? = null,
    val isBirthdateSet: Boolean? = null,
    val searchQuery: String? = null
) : NavigationScreenParams<ManagementUserListParams> {

    override val parser: NavigationScreenParamsParser<ManagementUserListParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<ManagementUserListParams> {

        /**
         * Parses raw Deep Link or Web Router query parameters into typed [ManagementUserListParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [ManagementUserListParams] instance.
         */
        override fun parse(params: Map<String, Any?>): ManagementUserListParams {
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
                ?.let { UserSortBy.fromValueOrNull(it) }
                ?: UserSortBy.DEFAULT

            val sortOrder = normalized[ListingParamNames.Sort.SORT_ORDER]
                ?.firstOrNull()
                ?.let { SortOrder.fromValueOrNull(it) }
                ?: SortOrder.DEFAULT

            val userIds = normalized[UserFilterValues.USER_ID]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val userRoles = normalized[UserFilterValues.ROLE]
                ?.mapNotNull { UserRole.fromValueOrNull(it) }
                ?: emptyList()

            val excludeUserRoles = normalized[UserFilterValues.EXCLUDE_ROLE]
                ?.mapNotNull { UserRole.fromValueOrNull(it) }
                ?: emptyList()

            val accountStatuses = normalized[UserFilterValues.ACCOUNT_STATUS]
                ?.mapNotNull { UserAccountStatus.fromValueOrNull(it) }
                ?: emptyList()

            val excludeAccountStatuses = normalized[UserFilterValues.EXCLUDE_ACCOUNT_STATUS]
                ?.mapNotNull { UserAccountStatus.fromValueOrNull(it) }
                ?: emptyList()

            val accountStatusesOnRestore = normalized[UserFilterValues.ACCOUNT_STATUS_ON_RESTORE]
                ?.mapNotNull { UserAccountStatus.fromValueOrNull(it) }
                ?: emptyList()

            val excludeAccountStatusesOnRestore = normalized[UserFilterValues.EXCLUDE_ACCOUNT_STATUS_ON_RESTORE]
                ?.mapNotNull { UserAccountStatus.fromValueOrNull(it) }
                ?: emptyList()

            val isAccountStatusOnRestoreSet = normalized[UserFilterValues.IS_ACCOUNT_STATUS_ON_RESTORE_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val authorityLevelFrom = normalized[UserFilterValues.AUTHORITY_LEVEL_FROM]
                ?.firstOrNull()
                ?.toIntOrNull()

            val authorityLevelTo = normalized[UserFilterValues.AUTHORITY_LEVEL_TO]
                ?.firstOrNull()
                ?.toIntOrNull()

            val permissionCodes = normalized[UserFilterValues.PERMISSION_CODES]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val excludePermissionCodes = normalized[UserFilterValues.EXCLUDE_PERMISSION_CODES]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val isTotpEnabled = normalized[UserFilterValues.IS_TOTP_ENABLED]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val lastLoginAtFrom = normalized[UserFilterValues.LAST_LOGIN_AT_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val lastLoginAtTo = normalized[UserFilterValues.LAST_LOGIN_AT_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isLastLoginAtSet = normalized[UserFilterValues.IS_LAST_LOGIN_AT_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val lastActiveAtFrom = normalized[UserFilterValues.LAST_ACTIVE_AT_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val lastActiveAtTo = normalized[UserFilterValues.LAST_ACTIVE_AT_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isLastActiveAtSet = normalized[UserFilterValues.IS_LAST_ACTIVE_AT_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val createdAtFrom = normalized[UserFilterValues.CREATED_AT_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val createdAtTo = normalized[UserFilterValues.CREATED_AT_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val updatedAtFrom = normalized[UserFilterValues.UPDATED_AT_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val updatedAtTo = normalized[UserFilterValues.UPDATED_AT_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isUpdatedAtSet = normalized[UserFilterValues.IS_UPDATED_AT_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val scheduledPermanentDeletionAtFrom = normalized[UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val scheduledPermanentDeletionAtTo = normalized[UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isScheduledPermanentDeletionAtSet = normalized[UserFilterValues.IS_SCHEDULED_PERMANENT_DELETION_AT_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val accountLockoutTypes = normalized[UserFilterValues.ACCOUNT_LOCKOUT_TYPE]
                ?.mapNotNull { AccountLockoutType.fromValueOrNull(it) }
                ?: emptyList()

            val excludeAccountLockoutTypes = normalized[UserFilterValues.EXCLUDE_ACCOUNT_LOCKOUT_TYPE]
                ?.mapNotNull { AccountLockoutType.fromValueOrNull(it) }
                ?: emptyList()

            val temporaryLockoutUntilFrom = normalized[UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val temporaryLockoutUntilTo = normalized[UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isTemporaryLockoutUntilSet = normalized[UserFilterValues.IS_TEMPORARY_LOCKOUT_UNTIL_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val storageQuotaBytesFrom = normalized[UserFilterValues.STORAGE_QUOTA_BYTES_FROM]
                ?.firstOrNull()
                ?.toLongOrNull()

            val storageQuotaBytesTo = normalized[UserFilterValues.STORAGE_QUOTA_BYTES_TO]
                ?.firstOrNull()
                ?.toLongOrNull()

            val isStorageQuotaBytesSet = normalized[UserFilterValues.IS_STORAGE_QUOTA_BYTES_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val storageMaxUploadSizeBytesFrom = normalized[UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM]
                ?.firstOrNull()
                ?.toLongOrNull()

            val storageMaxUploadSizeBytesTo = normalized[UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_TO]
                ?.firstOrNull()
                ?.toLongOrNull()

            val isStorageMaxUploadSizeBytesSet = normalized[UserFilterValues.IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val storageMaxPendingUploadsFrom = normalized[UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_FROM]
                ?.firstOrNull()
                ?.toLongOrNull()

            val storageMaxPendingUploadsTo = normalized[UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_TO]
                ?.firstOrNull()
                ?.toLongOrNull()

            val isStorageMaxPendingUploadsSet = normalized[UserFilterValues.IS_STORAGE_MAX_PENDING_UPLOADS_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val isStorageConfigurationSet = normalized[UserFilterValues.IS_STORAGE_CONFIGURATION_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val nickname = normalized[UserFilterValues.NICKNAME]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isNicknameSet = normalized[UserFilterValues.IS_NICKNAME_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val displayName = normalized[UserFilterValues.DISPLAY_NAME]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isDisplayNameSet = normalized[UserFilterValues.IS_DISPLAY_NAME_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val isAvatarSet = normalized[UserFilterValues.IS_AVATAR_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val birthdateFrom = normalized[UserFilterValues.BIRTHDATE_FROM]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val birthdateTo = normalized[UserFilterValues.BIRTHDATE_TO]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            val isBirthdateSet = normalized[UserFilterValues.IS_BIRTHDATE_SET]
                ?.firstOrNull()
                ?.toBooleanStrictOrNull()

            val searchQuery = normalized[UserFilterValues.SEARCH_QUERY]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }

            return ManagementUserListParams(
                pageNumber = pageNumber,
                pageSize = pageSize,
                sortBy = sortBy,
                sortOrder = sortOrder,
                userIds = userIds,
                userRoles = userRoles,
                excludeUserRoles = excludeUserRoles,
                accountStatuses = accountStatuses,
                excludeAccountStatuses = excludeAccountStatuses,
                accountStatusesOnRestore = accountStatusesOnRestore,
                excludeAccountStatusesOnRestore = excludeAccountStatusesOnRestore,
                isAccountStatusOnRestoreSet = isAccountStatusOnRestoreSet,
                authorityLevelFrom = authorityLevelFrom,
                authorityLevelTo = authorityLevelTo,
                permissionCodes = permissionCodes,
                excludePermissionCodes = excludePermissionCodes,
                isTotpEnabled = isTotpEnabled,
                lastLoginAtFrom = lastLoginAtFrom,
                lastLoginAtTo = lastLoginAtTo,
                isLastLoginAtSet = isLastLoginAtSet,
                lastActiveAtFrom = lastActiveAtFrom,
                lastActiveAtTo = lastActiveAtTo,
                isLastActiveAtSet = isLastActiveAtSet,
                createdAtFrom = createdAtFrom,
                createdAtTo = createdAtTo,
                updatedAtFrom = updatedAtFrom,
                updatedAtTo = updatedAtTo,
                isUpdatedAtSet = isUpdatedAtSet,
                scheduledPermanentDeletionAtFrom = scheduledPermanentDeletionAtFrom,
                scheduledPermanentDeletionAtTo = scheduledPermanentDeletionAtTo,
                isScheduledPermanentDeletionAtSet = isScheduledPermanentDeletionAtSet,
                accountLockoutTypes = accountLockoutTypes,
                excludeAccountLockoutTypes = excludeAccountLockoutTypes,
                temporaryLockoutUntilFrom = temporaryLockoutUntilFrom,
                temporaryLockoutUntilTo = temporaryLockoutUntilTo,
                isTemporaryLockoutUntilSet = isTemporaryLockoutUntilSet,
                storageQuotaBytesFrom = storageQuotaBytesFrom,
                storageQuotaBytesTo = storageQuotaBytesTo,
                isStorageQuotaBytesSet = isStorageQuotaBytesSet,
                storageMaxUploadSizeBytesFrom = storageMaxUploadSizeBytesFrom,
                storageMaxUploadSizeBytesTo = storageMaxUploadSizeBytesTo,
                isStorageMaxUploadSizeBytesSet = isStorageMaxUploadSizeBytesSet,
                storageMaxPendingUploadsFrom = storageMaxPendingUploadsFrom,
                storageMaxPendingUploadsTo = storageMaxPendingUploadsTo,
                isStorageMaxPendingUploadsSet = isStorageMaxPendingUploadsSet,
                isStorageConfigurationSet = isStorageConfigurationSet,
                nickname = nickname,
                isNicknameSet = isNicknameSet,
                displayName = displayName,
                isDisplayNameSet = isDisplayNameSet,
                isAvatarSet = isAvatarSet,
                birthdateFrom = birthdateFrom,
                birthdateTo = birthdateTo,
                isBirthdateSet = isBirthdateSet,
                searchQuery = searchQuery
            )
        }

        /**
         * Serializes [ManagementUserListParams] into a map of raw URL parameters.
         *
         * @param params Typed [ManagementUserListParams] to serialize.
         * @return Map of parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: ManagementUserListParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()

            if (params.pageNumber != ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER) {
                result[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE) {
                result[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != UserSortBy.DEFAULT) {
                result[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                result[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }
            if (params.userIds.isNotEmpty()) {
                result[UserFilterValues.USER_ID] = params.userIds
            }
            if (params.userRoles.isNotEmpty()) {
                result[UserFilterValues.ROLE] = params.userRoles.map { it.value }
            }
            if (params.excludeUserRoles.isNotEmpty()) {
                result[UserFilterValues.EXCLUDE_ROLE] = params.excludeUserRoles.map { it.value }
            }
            if (params.accountStatuses.isNotEmpty()) {
                result[UserFilterValues.ACCOUNT_STATUS] = params.accountStatuses.map { it.value }
            }
            if (params.excludeAccountStatuses.isNotEmpty()) {
                result[UserFilterValues.EXCLUDE_ACCOUNT_STATUS] = params.excludeAccountStatuses.map { it.value }
            }
            if (params.accountStatusesOnRestore.isNotEmpty()) {
                result[UserFilterValues.ACCOUNT_STATUS_ON_RESTORE] = params.accountStatusesOnRestore.map { it.value }
            }
            if (params.excludeAccountStatusesOnRestore.isNotEmpty()) {
                result[UserFilterValues.EXCLUDE_ACCOUNT_STATUS_ON_RESTORE] =
                    params.excludeAccountStatusesOnRestore.map { it.value }
            }
            if (params.isAccountStatusOnRestoreSet != null) {
                result[UserFilterValues.IS_ACCOUNT_STATUS_ON_RESTORE_SET] =
                    listOf(params.isAccountStatusOnRestoreSet.toString())
            }
            if (params.authorityLevelFrom != null) {
                result[UserFilterValues.AUTHORITY_LEVEL_FROM] = listOf(params.authorityLevelFrom.toString())
            }
            if (params.authorityLevelTo != null) {
                result[UserFilterValues.AUTHORITY_LEVEL_TO] = listOf(params.authorityLevelTo.toString())
            }
            if (params.permissionCodes.isNotEmpty()) {
                result[UserFilterValues.PERMISSION_CODES] = params.permissionCodes
            }
            if (params.excludePermissionCodes.isNotEmpty()) {
                result[UserFilterValues.EXCLUDE_PERMISSION_CODES] = params.excludePermissionCodes
            }
            if (params.isTotpEnabled != null) {
                result[UserFilterValues.IS_TOTP_ENABLED] = listOf(params.isTotpEnabled.toString())
            }
            if (params.lastLoginAtFrom != null) {
                result[UserFilterValues.LAST_LOGIN_AT_FROM] = listOf(params.lastLoginAtFrom)
            }
            if (params.lastLoginAtTo != null) {
                result[UserFilterValues.LAST_LOGIN_AT_TO] = listOf(params.lastLoginAtTo)
            }
            if (params.isLastLoginAtSet != null) {
                result[UserFilterValues.IS_LAST_LOGIN_AT_SET] = listOf(params.isLastLoginAtSet.toString())
            }
            if (params.lastActiveAtFrom != null) {
                result[UserFilterValues.LAST_ACTIVE_AT_FROM] = listOf(params.lastActiveAtFrom)
            }
            if (params.lastActiveAtTo != null) {
                result[UserFilterValues.LAST_ACTIVE_AT_TO] = listOf(params.lastActiveAtTo)
            }
            if (params.isLastActiveAtSet != null) {
                result[UserFilterValues.IS_LAST_ACTIVE_AT_SET] = listOf(params.isLastActiveAtSet.toString())
            }
            if (params.createdAtFrom != null) {
                result[UserFilterValues.CREATED_AT_FROM] = listOf(params.createdAtFrom)
            }
            if (params.createdAtTo != null) {
                result[UserFilterValues.CREATED_AT_TO] = listOf(params.createdAtTo)
            }
            if (params.updatedAtFrom != null) {
                result[UserFilterValues.UPDATED_AT_FROM] = listOf(params.updatedAtFrom)
            }
            if (params.updatedAtTo != null) {
                result[UserFilterValues.UPDATED_AT_TO] = listOf(params.updatedAtTo)
            }
            if (params.isUpdatedAtSet != null) {
                result[UserFilterValues.IS_UPDATED_AT_SET] = listOf(params.isUpdatedAtSet.toString())
            }
            if (params.scheduledPermanentDeletionAtFrom != null) {
                result[UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_FROM] =
                    listOf(params.scheduledPermanentDeletionAtFrom)
            }
            if (params.scheduledPermanentDeletionAtTo != null) {
                result[UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_TO] =
                    listOf(params.scheduledPermanentDeletionAtTo)
            }
            if (params.isScheduledPermanentDeletionAtSet != null) {
                result[UserFilterValues.IS_SCHEDULED_PERMANENT_DELETION_AT_SET] =
                    listOf(params.isScheduledPermanentDeletionAtSet.toString())
            }
            if (params.accountLockoutTypes.isNotEmpty()) {
                result[UserFilterValues.ACCOUNT_LOCKOUT_TYPE] = params.accountLockoutTypes.map { it.value }
            }
            if (params.excludeAccountLockoutTypes.isNotEmpty()) {
                result[UserFilterValues.EXCLUDE_ACCOUNT_LOCKOUT_TYPE] =
                    params.excludeAccountLockoutTypes.map { it.value }
            }
            if (params.temporaryLockoutUntilFrom != null) {
                result[UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_FROM] = listOf(params.temporaryLockoutUntilFrom)
            }
            if (params.temporaryLockoutUntilTo != null) {
                result[UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_TO] = listOf(params.temporaryLockoutUntilTo)
            }
            if (params.isTemporaryLockoutUntilSet != null) {
                result[UserFilterValues.IS_TEMPORARY_LOCKOUT_UNTIL_SET] =
                    listOf(params.isTemporaryLockoutUntilSet.toString())
            }
            if (params.storageQuotaBytesFrom != null) {
                result[UserFilterValues.STORAGE_QUOTA_BYTES_FROM] = listOf(params.storageQuotaBytesFrom.toString())
            }
            if (params.storageQuotaBytesTo != null) {
                result[UserFilterValues.STORAGE_QUOTA_BYTES_TO] = listOf(params.storageQuotaBytesTo.toString())
            }
            if (params.isStorageQuotaBytesSet != null) {
                result[UserFilterValues.IS_STORAGE_QUOTA_BYTES_SET] =
                    listOf(params.isStorageQuotaBytesSet.toString())
            }
            if (params.storageMaxUploadSizeBytesFrom != null) {
                result[UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM] =
                    listOf(params.storageMaxUploadSizeBytesFrom.toString())
            }
            if (params.storageMaxUploadSizeBytesTo != null) {
                result[UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_TO] =
                    listOf(params.storageMaxUploadSizeBytesTo.toString())
            }
            if (params.isStorageMaxUploadSizeBytesSet != null) {
                result[UserFilterValues.IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET] =
                    listOf(params.isStorageMaxUploadSizeBytesSet.toString())
            }
            if (params.storageMaxPendingUploadsFrom != null) {
                result[UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_FROM] =
                    listOf(params.storageMaxPendingUploadsFrom.toString())
            }
            if (params.storageMaxPendingUploadsTo != null) {
                result[UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_TO] =
                    listOf(params.storageMaxPendingUploadsTo.toString())
            }
            if (params.isStorageMaxPendingUploadsSet != null) {
                result[UserFilterValues.IS_STORAGE_MAX_PENDING_UPLOADS_SET] =
                    listOf(params.isStorageMaxPendingUploadsSet.toString())
            }
            if (params.isStorageConfigurationSet != null) {
                result[UserFilterValues.IS_STORAGE_CONFIGURATION_SET] =
                    listOf(params.isStorageConfigurationSet.toString())
            }
            if (!params.nickname.isNullOrBlank()) {
                result[UserFilterValues.NICKNAME] = listOf(params.nickname.trim())
            }
            if (params.isNicknameSet != null) {
                result[UserFilterValues.IS_NICKNAME_SET] = listOf(params.isNicknameSet.toString())
            }
            if (!params.displayName.isNullOrBlank()) {
                result[UserFilterValues.DISPLAY_NAME] = listOf(params.displayName.trim())
            }
            if (params.isDisplayNameSet != null) {
                result[UserFilterValues.IS_DISPLAY_NAME_SET] = listOf(params.isDisplayNameSet.toString())
            }
            if (params.isAvatarSet != null) {
                result[UserFilterValues.IS_AVATAR_SET] = listOf(params.isAvatarSet.toString())
            }
            if (params.birthdateFrom != null) {
                result[UserFilterValues.BIRTHDATE_FROM] = listOf(params.birthdateFrom)
            }
            if (params.birthdateTo != null) {
                result[UserFilterValues.BIRTHDATE_TO] = listOf(params.birthdateTo)
            }
            if (params.isBirthdateSet != null) {
                result[UserFilterValues.IS_BIRTHDATE_SET] = listOf(params.isBirthdateSet.toString())
            }
            if (params.searchQuery != null) {
                result[UserFilterValues.SEARCH_QUERY] = listOf(params.searchQuery)
            }

            return result
        }
    }
}
