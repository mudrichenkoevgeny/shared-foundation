package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseBoolean
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseEnumList
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseInt
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseLong
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseNonBlankString
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.parseStringList
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
public data class ManagementUserListParams(
    public val pageNumber: Int = ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
    public val pageSize: Int = ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
    public val sortBy: UserSortBy = UserSortBy.DEFAULT,
    public val sortOrder: SortOrder = SortOrder.DEFAULT,
    public val userIds: List<String> = emptyList(),
    public val userRoles: List<UserRole> = emptyList(),
    public val excludeUserRoles: List<UserRole> = emptyList(),
    public val accountStatuses: List<UserAccountStatus> = emptyList(),
    public val excludeAccountStatuses: List<UserAccountStatus> = emptyList(),
    public val accountStatusesOnRestore: List<UserAccountStatus> = emptyList(),
    public val excludeAccountStatusesOnRestore: List<UserAccountStatus> = emptyList(),
    public val isAccountStatusOnRestoreSet: Boolean? = null,
    public val authorityLevelFrom: Int? = null,
    public val authorityLevelTo: Int? = null,
    public val permissionCodes: List<String> = emptyList(),
    public val excludePermissionCodes: List<String> = emptyList(),
    public val isTotpEnabled: Boolean? = null,
    public val lastLoginAtFrom: String? = null,
    public val lastLoginAtTo: String? = null,
    public val isLastLoginAtSet: Boolean? = null,
    public val lastActiveAtFrom: String? = null,
    public val lastActiveAtTo: String? = null,
    public val isLastActiveAtSet: Boolean? = null,
    public val createdAtFrom: String? = null,
    public val createdAtTo: String? = null,
    public val updatedAtFrom: String? = null,
    public val updatedAtTo: String? = null,
    public val isUpdatedAtSet: Boolean? = null,
    public val scheduledPermanentDeletionAtFrom: String? = null,
    public val scheduledPermanentDeletionAtTo: String? = null,
    public val isScheduledPermanentDeletionAtSet: Boolean? = null,
    public val accountLockoutTypes: List<AccountLockoutType> = emptyList(),
    public val excludeAccountLockoutTypes: List<AccountLockoutType> = emptyList(),
    public val temporaryLockoutUntilFrom: String? = null,
    public val temporaryLockoutUntilTo: String? = null,
    public val isTemporaryLockoutUntilSet: Boolean? = null,
    public val storageQuotaBytesFrom: Long? = null,
    public val storageQuotaBytesTo: Long? = null,
    public val isStorageQuotaBytesSet: Boolean? = null,
    public val storageMaxUploadSizeBytesFrom: Long? = null,
    public val storageMaxUploadSizeBytesTo: Long? = null,
    public val isStorageMaxUploadSizeBytesSet: Boolean? = null,
    public val storageMaxPendingUploadsFrom: Long? = null,
    public val storageMaxPendingUploadsTo: Long? = null,
    public val isStorageMaxPendingUploadsSet: Boolean? = null,
    public val isStorageConfigurationSet: Boolean? = null,
    public val nickname: String? = null,
    public val isNicknameSet: Boolean? = null,
    public val displayName: String? = null,
    public val isDisplayNameSet: Boolean? = null,
    public val isAvatarSet: Boolean? = null,
    public val birthdateFrom: String? = null,
    public val birthdateTo: String? = null,
    public val isBirthdateSet: Boolean? = null,
    public val searchQuery: String? = null
) : NavigationScreenParams<ManagementUserListParams> {
    override val parser: NavigationScreenParamsParser<ManagementUserListParams>
        get() = Companion

    public companion object : NavigationScreenParamsParser<ManagementUserListParams> {
        /**
         * Parses raw Deep Link or Web Router query parameters into typed [ManagementUserListParams].
         *
         * @param params Map of parameter keys to raw parameter values.
         * @return Validated [ManagementUserListParams] instance.
         */
        override fun parse(params: Map<String, Any?>): ManagementUserListParams {
            val normalized = params.normalizeQueryParams()
            return ManagementUserListParams(
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
                        ?.let { UserSortBy.fromValueOrNull(it) }
                        ?: UserSortBy.DEFAULT,
                sortOrder =
                    normalized[ListingParamNames.Sort.SORT_ORDER]
                        ?.firstOrNull()
                        ?.let { SortOrder.fromValueOrNull(it) }
                        ?: SortOrder.DEFAULT,
                userIds = normalized.parseStringList(UserFilterValues.USER_ID),
                userRoles = normalized.parseEnumList(UserFilterValues.ROLE) { UserRole.fromValueOrNull(it) },
                excludeUserRoles = normalized.parseEnumList(UserFilterValues.EXCLUDE_ROLE) { UserRole.fromValueOrNull(it) },
                accountStatuses = normalized.parseEnumList(UserFilterValues.ACCOUNT_STATUS) { UserAccountStatus.fromValueOrNull(it) },
                excludeAccountStatuses = normalized.parseEnumList(UserFilterValues.EXCLUDE_ACCOUNT_STATUS) { UserAccountStatus.fromValueOrNull(it) },
                accountStatusesOnRestore = normalized.parseEnumList(UserFilterValues.ACCOUNT_STATUS_ON_RESTORE) { UserAccountStatus.fromValueOrNull(it) },
                excludeAccountStatusesOnRestore = normalized.parseEnumList(UserFilterValues.EXCLUDE_ACCOUNT_STATUS_ON_RESTORE) { UserAccountStatus.fromValueOrNull(it) },
                isAccountStatusOnRestoreSet = normalized.parseBoolean(UserFilterValues.IS_ACCOUNT_STATUS_ON_RESTORE_SET),
                authorityLevelFrom = normalized.parseInt(UserFilterValues.AUTHORITY_LEVEL_FROM),
                authorityLevelTo = normalized.parseInt(UserFilterValues.AUTHORITY_LEVEL_TO),
                permissionCodes = normalized.parseStringList(UserFilterValues.PERMISSION_CODES),
                excludePermissionCodes = normalized.parseStringList(UserFilterValues.EXCLUDE_PERMISSION_CODES),
                isTotpEnabled = normalized.parseBoolean(UserFilterValues.IS_TOTP_ENABLED),
                lastLoginAtFrom = normalized.parseNonBlankString(UserFilterValues.LAST_LOGIN_AT_FROM),
                lastLoginAtTo = normalized.parseNonBlankString(UserFilterValues.LAST_LOGIN_AT_TO),
                isLastLoginAtSet = normalized.parseBoolean(UserFilterValues.IS_LAST_LOGIN_AT_SET),
                lastActiveAtFrom = normalized.parseNonBlankString(UserFilterValues.LAST_ACTIVE_AT_FROM),
                lastActiveAtTo = normalized.parseNonBlankString(UserFilterValues.LAST_ACTIVE_AT_TO),
                isLastActiveAtSet = normalized.parseBoolean(UserFilterValues.IS_LAST_ACTIVE_AT_SET),
                createdAtFrom = normalized.parseNonBlankString(UserFilterValues.CREATED_AT_FROM),
                createdAtTo = normalized.parseNonBlankString(UserFilterValues.CREATED_AT_TO),
                updatedAtFrom = normalized.parseNonBlankString(UserFilterValues.UPDATED_AT_FROM),
                updatedAtTo = normalized.parseNonBlankString(UserFilterValues.UPDATED_AT_TO),
                isUpdatedAtSet = normalized.parseBoolean(UserFilterValues.IS_UPDATED_AT_SET),
                scheduledPermanentDeletionAtFrom = normalized.parseNonBlankString(UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_FROM),
                scheduledPermanentDeletionAtTo = normalized.parseNonBlankString(UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_TO),
                isScheduledPermanentDeletionAtSet = normalized.parseBoolean(UserFilterValues.IS_SCHEDULED_PERMANENT_DELETION_AT_SET),
                accountLockoutTypes = normalized.parseEnumList(UserFilterValues.ACCOUNT_LOCKOUT_TYPE) { AccountLockoutType.fromValueOrNull(it) },
                excludeAccountLockoutTypes = normalized.parseEnumList(UserFilterValues.EXCLUDE_ACCOUNT_LOCKOUT_TYPE) { AccountLockoutType.fromValueOrNull(it) },
                temporaryLockoutUntilFrom = normalized.parseNonBlankString(UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_FROM),
                temporaryLockoutUntilTo = normalized.parseNonBlankString(UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_TO),
                isTemporaryLockoutUntilSet = normalized.parseBoolean(UserFilterValues.IS_TEMPORARY_LOCKOUT_UNTIL_SET),
                storageQuotaBytesFrom = normalized.parseLong(UserFilterValues.STORAGE_QUOTA_BYTES_FROM),
                storageQuotaBytesTo = normalized.parseLong(UserFilterValues.STORAGE_QUOTA_BYTES_TO),
                isStorageQuotaBytesSet = normalized.parseBoolean(UserFilterValues.IS_STORAGE_QUOTA_BYTES_SET),
                storageMaxUploadSizeBytesFrom = normalized.parseLong(UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM),
                storageMaxUploadSizeBytesTo = normalized.parseLong(UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_TO),
                isStorageMaxUploadSizeBytesSet = normalized.parseBoolean(UserFilterValues.IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET),
                storageMaxPendingUploadsFrom = normalized.parseLong(UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_FROM),
                storageMaxPendingUploadsTo = normalized.parseLong(UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_TO),
                isStorageMaxPendingUploadsSet = normalized.parseBoolean(UserFilterValues.IS_STORAGE_MAX_PENDING_UPLOADS_SET),
                isStorageConfigurationSet = normalized.parseBoolean(UserFilterValues.IS_STORAGE_CONFIGURATION_SET),
                nickname = normalized.parseNonBlankString(UserFilterValues.NICKNAME),
                isNicknameSet = normalized.parseBoolean(UserFilterValues.IS_NICKNAME_SET),
                displayName = normalized.parseNonBlankString(UserFilterValues.DISPLAY_NAME),
                isDisplayNameSet = normalized.parseBoolean(UserFilterValues.IS_DISPLAY_NAME_SET),
                isAvatarSet = normalized.parseBoolean(UserFilterValues.IS_AVATAR_SET),
                birthdateFrom = normalized.parseNonBlankString(UserFilterValues.BIRTHDATE_FROM),
                birthdateTo = normalized.parseNonBlankString(UserFilterValues.BIRTHDATE_TO),
                isBirthdateSet = normalized.parseBoolean(UserFilterValues.IS_BIRTHDATE_SET),
                searchQuery = normalized.parseNonBlankString(UserFilterValues.SEARCH_QUERY)
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
            result.serializePaginationAndSorting(params)
            result.serializeCoreAndAccessFilters(params)
            result.serializeActivityAndLockoutFilters(params)
            result.serializeStorageAndProfileFilters(params)
            return result
        }

        private fun MutableMap<String, List<String>>.serializePaginationAndSorting(params: ManagementUserListParams) {
            if (params.pageNumber != ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER) {
                this[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE) {
                this[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != UserSortBy.DEFAULT) {
                this[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                this[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }
        }

        private fun MutableMap<String, List<String>>.serializeCoreAndAccessFilters(params: ManagementUserListParams) {
            if (params.userIds.isNotEmpty()) {
                this[UserFilterValues.USER_ID] = params.userIds
            }
            if (params.userRoles.isNotEmpty()) {
                this[UserFilterValues.ROLE] = params.userRoles.map { it.value }
            }
            if (params.excludeUserRoles.isNotEmpty()) {
                this[UserFilterValues.EXCLUDE_ROLE] = params.excludeUserRoles.map { it.value }
            }
            if (params.accountStatuses.isNotEmpty()) {
                this[UserFilterValues.ACCOUNT_STATUS] = params.accountStatuses.map { it.value }
            }
            if (params.excludeAccountStatuses.isNotEmpty()) {
                this[UserFilterValues.EXCLUDE_ACCOUNT_STATUS] = params.excludeAccountStatuses.map { it.value }
            }
            if (params.accountStatusesOnRestore.isNotEmpty()) {
                this[UserFilterValues.ACCOUNT_STATUS_ON_RESTORE] = params.accountStatusesOnRestore.map { it.value }
            }
            if (params.excludeAccountStatusesOnRestore.isNotEmpty()) {
                this[UserFilterValues.EXCLUDE_ACCOUNT_STATUS_ON_RESTORE] =
                    params.excludeAccountStatusesOnRestore.map { it.value }
            }
            if (params.isAccountStatusOnRestoreSet != null) {
                this[UserFilterValues.IS_ACCOUNT_STATUS_ON_RESTORE_SET] =
                    listOf(params.isAccountStatusOnRestoreSet.toString())
            }
            if (params.authorityLevelFrom != null) {
                this[UserFilterValues.AUTHORITY_LEVEL_FROM] = listOf(params.authorityLevelFrom.toString())
            }
            if (params.authorityLevelTo != null) {
                this[UserFilterValues.AUTHORITY_LEVEL_TO] = listOf(params.authorityLevelTo.toString())
            }
            if (params.permissionCodes.isNotEmpty()) {
                this[UserFilterValues.PERMISSION_CODES] = params.permissionCodes
            }
            if (params.excludePermissionCodes.isNotEmpty()) {
                this[UserFilterValues.EXCLUDE_PERMISSION_CODES] = params.excludePermissionCodes
            }
            if (params.isTotpEnabled != null) {
                this[UserFilterValues.IS_TOTP_ENABLED] = listOf(params.isTotpEnabled.toString())
            }
        }

        private fun MutableMap<String, List<String>>.serializeActivityAndLockoutFilters(
            params: ManagementUserListParams
        ) {
            if (params.lastLoginAtFrom != null) {
                this[UserFilterValues.LAST_LOGIN_AT_FROM] = listOf(params.lastLoginAtFrom)
            }
            if (params.lastLoginAtTo != null) {
                this[UserFilterValues.LAST_LOGIN_AT_TO] = listOf(params.lastLoginAtTo)
            }
            if (params.isLastLoginAtSet != null) {
                this[UserFilterValues.IS_LAST_LOGIN_AT_SET] = listOf(params.isLastLoginAtSet.toString())
            }
            if (params.lastActiveAtFrom != null) {
                this[UserFilterValues.LAST_ACTIVE_AT_FROM] = listOf(params.lastActiveAtFrom)
            }
            if (params.lastActiveAtTo != null) {
                this[UserFilterValues.LAST_ACTIVE_AT_TO] = listOf(params.lastActiveAtTo)
            }
            if (params.isLastActiveAtSet != null) {
                this[UserFilterValues.IS_LAST_ACTIVE_AT_SET] = listOf(params.isLastActiveAtSet.toString())
            }
            if (params.createdAtFrom != null) {
                this[UserFilterValues.CREATED_AT_FROM] = listOf(params.createdAtFrom)
            }
            if (params.createdAtTo != null) {
                this[UserFilterValues.CREATED_AT_TO] = listOf(params.createdAtTo)
            }
            if (params.updatedAtFrom != null) {
                this[UserFilterValues.UPDATED_AT_FROM] = listOf(params.updatedAtFrom)
            }
            if (params.updatedAtTo != null) {
                this[UserFilterValues.UPDATED_AT_TO] = listOf(params.updatedAtTo)
            }
            if (params.isUpdatedAtSet != null) {
                this[UserFilterValues.IS_UPDATED_AT_SET] = listOf(params.isUpdatedAtSet.toString())
            }
            if (params.scheduledPermanentDeletionAtFrom != null) {
                this[UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_FROM] =
                    listOf(params.scheduledPermanentDeletionAtFrom)
            }
            if (params.scheduledPermanentDeletionAtTo != null) {
                this[UserFilterValues.SCHEDULED_PERMANENT_DELETION_AT_TO] =
                    listOf(params.scheduledPermanentDeletionAtTo)
            }
            if (params.isScheduledPermanentDeletionAtSet != null) {
                this[UserFilterValues.IS_SCHEDULED_PERMANENT_DELETION_AT_SET] =
                    listOf(params.isScheduledPermanentDeletionAtSet.toString())
            }
            if (params.accountLockoutTypes.isNotEmpty()) {
                this[UserFilterValues.ACCOUNT_LOCKOUT_TYPE] = params.accountLockoutTypes.map { it.value }
            }
            if (params.excludeAccountLockoutTypes.isNotEmpty()) {
                this[UserFilterValues.EXCLUDE_ACCOUNT_LOCKOUT_TYPE] =
                    params.excludeAccountLockoutTypes.map { it.value }
            }
            if (params.temporaryLockoutUntilFrom != null) {
                this[UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_FROM] = listOf(params.temporaryLockoutUntilFrom)
            }
            if (params.temporaryLockoutUntilTo != null) {
                this[UserFilterValues.TEMPORARY_LOCKOUT_UNTIL_TO] = listOf(params.temporaryLockoutUntilTo)
            }
            if (params.isTemporaryLockoutUntilSet != null) {
                this[UserFilterValues.IS_TEMPORARY_LOCKOUT_UNTIL_SET] =
                    listOf(params.isTemporaryLockoutUntilSet.toString())
            }
        }

        private fun MutableMap<String, List<String>>.serializeStorageAndProfileFilters(
            params: ManagementUserListParams
        ) {
            if (params.storageQuotaBytesFrom != null) {
                this[UserFilterValues.STORAGE_QUOTA_BYTES_FROM] = listOf(params.storageQuotaBytesFrom.toString())
            }
            if (params.storageQuotaBytesTo != null) {
                this[UserFilterValues.STORAGE_QUOTA_BYTES_TO] = listOf(params.storageQuotaBytesTo.toString())
            }
            if (params.isStorageQuotaBytesSet != null) {
                this[UserFilterValues.IS_STORAGE_QUOTA_BYTES_SET] =
                    listOf(params.isStorageQuotaBytesSet.toString())
            }
            if (params.storageMaxUploadSizeBytesFrom != null) {
                this[UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM] =
                    listOf(params.storageMaxUploadSizeBytesFrom.toString())
            }
            if (params.storageMaxUploadSizeBytesTo != null) {
                this[UserFilterValues.STORAGE_MAX_UPLOAD_SIZE_BYTES_TO] =
                    listOf(params.storageMaxUploadSizeBytesTo.toString())
            }
            if (params.isStorageMaxUploadSizeBytesSet != null) {
                this[UserFilterValues.IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET] =
                    listOf(params.isStorageMaxUploadSizeBytesSet.toString())
            }
            if (params.storageMaxPendingUploadsFrom != null) {
                this[UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_FROM] =
                    listOf(params.storageMaxPendingUploadsFrom.toString())
            }
            if (params.storageMaxPendingUploadsTo != null) {
                this[UserFilterValues.STORAGE_MAX_PENDING_UPLOADS_TO] =
                    listOf(params.storageMaxPendingUploadsTo.toString())
            }
            if (params.isStorageMaxPendingUploadsSet != null) {
                this[UserFilterValues.IS_STORAGE_MAX_PENDING_UPLOADS_SET] =
                    listOf(params.isStorageMaxPendingUploadsSet.toString())
            }
            if (params.isStorageConfigurationSet != null) {
                this[UserFilterValues.IS_STORAGE_CONFIGURATION_SET] =
                    listOf(params.isStorageConfigurationSet.toString())
            }
            if (!params.nickname.isNullOrBlank()) {
                this[UserFilterValues.NICKNAME] = listOf(params.nickname.trim())
            }
            if (params.isNicknameSet != null) {
                this[UserFilterValues.IS_NICKNAME_SET] = listOf(params.isNicknameSet.toString())
            }
            if (!params.displayName.isNullOrBlank()) {
                this[UserFilterValues.DISPLAY_NAME] = listOf(params.displayName.trim())
            }
            if (params.isDisplayNameSet != null) {
                this[UserFilterValues.IS_DISPLAY_NAME_SET] = listOf(params.isDisplayNameSet.toString())
            }
            if (params.isAvatarSet != null) {
                this[UserFilterValues.IS_AVATAR_SET] = listOf(params.isAvatarSet.toString())
            }
            if (params.birthdateFrom != null) {
                this[UserFilterValues.BIRTHDATE_FROM] = listOf(params.birthdateFrom)
            }
            if (params.birthdateTo != null) {
                this[UserFilterValues.BIRTHDATE_TO] = listOf(params.birthdateTo)
            }
            if (params.isBirthdateSet != null) {
                this[UserFilterValues.IS_BIRTHDATE_SET] = listOf(params.isBirthdateSet.toString())
            }
            if (params.searchQuery != null) {
                this[UserFilterValues.SEARCH_QUERY] = listOf(params.searchQuery)
            }
        }
    }
}
