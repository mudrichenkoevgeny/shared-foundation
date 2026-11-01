package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiFields

/**
 * Stable **keys** for filtering user collection list endpoints.
 *
 * Keys follow the declaration order of properties in [UserPrivate].
 * Use these constants when building HTTP query strings, parsing list requests, or mapping filters in services and
 * persistence so clients, servers, and documentation stay aligned.
 */
object UserFilterValues {

    // --- Core & Access ---

    /** Filter by one or more specific user IDs. */
    const val USER_ID = UserApiFields.USER_ID

    /** Filter by one or more [UserRole] wire values (OR semantics). */
    const val ROLE = UserApiFields.ROLE

    /** Filter to exclude specific [UserRole] wire values (NOT IN semantics). */
    const val EXCLUDE_ROLE = "exclude_role"

    /** Filter by one or more [UserAccountStatus] wire values (OR semantics). */
    const val ACCOUNT_STATUS = UserApiFields.ACCOUNT_STATUS

    /** Filter to exclude specific [UserAccountStatus] wire values (NOT IN semantics). */
    const val EXCLUDE_ACCOUNT_STATUS = "exclude_account_status"

    /** Filter by target [UserAccountStatus] assigned upon account restoration (OR semantics). */
    const val ACCOUNT_STATUS_ON_RESTORE = UserApiFields.ACCOUNT_STATUS_ON_RESTORE

    /** Filter to exclude specific target [UserAccountStatus] values on restore (NOT IN semantics). */
    const val EXCLUDE_ACCOUNT_STATUS_ON_RESTORE = "exclude_account_status_on_restore"

    /** Filter by boolean flag indicating whether target account status on restore is set. */
    const val IS_ACCOUNT_STATUS_ON_RESTORE_SET = "is_account_status_on_restore_set"

    /** Filter by inclusive lower bound for authority level. */
    const val AUTHORITY_LEVEL_FROM = "authority_level_from"

    /** Filter by inclusive upper bound for authority level. */
    const val AUTHORITY_LEVEL_TO = "authority_level_to"

    /** Filter by one or more explicit [PermissionCode] strings to include (OR semantics). */
    const val PERMISSION_CODES = UserApiFields.PERMISSION_CODES

    /** Filter by one or more explicit [PermissionCode] strings to exclude (NOT IN semantics). */
    const val EXCLUDE_PERMISSION_CODES = "exclude_permission_codes"

    // --- Security & Lockout ---

    /** Filter by boolean flag for TOTP 2FA status. */
    const val IS_TOTP_ENABLED = UserApiFields.IS_TOTP_ENABLED

    /** Filter by one or more [AccountLockoutType] wire values (OR semantics). */
    const val ACCOUNT_LOCKOUT_TYPE = UserApiFields.ACCOUNT_LOCKOUT_TYPE

    /** Filter to exclude specific [AccountLockoutType] wire values (NOT IN semantics). */
    const val EXCLUDE_ACCOUNT_LOCKOUT_TYPE = "exclude_account_lockout_type"

    /** Filter by inclusive lower bound for temporary lockout expiration timestamp. */
    const val TEMPORARY_LOCKOUT_UNTIL_FROM = "temporary_lockout_until_from"

    /** Filter by inclusive upper bound for temporary lockout expiration timestamp. */
    const val TEMPORARY_LOCKOUT_UNTIL_TO = "temporary_lockout_until_to"

    /** Filter by boolean flag indicating whether a temporary lockout is active for the user. */
    const val IS_TEMPORARY_LOCKOUT_UNTIL_SET = "is_temporary_lockout_until_set"

    // --- Timestamps ---

    /** Filter by inclusive lower bound for last login timestamp. */
    const val LAST_LOGIN_AT_FROM = "last_login_at_from"

    /** Filter by inclusive upper bound for last login timestamp. */
    const val LAST_LOGIN_AT_TO = "last_login_at_to"

    /** Filter by boolean flag indicating whether the user has logged in at least once. */
    const val IS_LAST_LOGIN_AT_SET = "is_last_login_at_set"

    /** Filter by inclusive lower bound for last active timestamp. */
    const val LAST_ACTIVE_AT_FROM = "last_active_at_from"

    /** Filter by inclusive upper bound for last active timestamp. */
    const val LAST_ACTIVE_AT_TO = "last_active_at_to"

    /** Filter by boolean flag indicating whether the user has recorded activity. */
    const val IS_LAST_ACTIVE_AT_SET = "is_last_active_at_set"

    /** Inclusive lower bound filter key for account creation timestamp. */
    const val CREATED_AT_FROM = CommonFilterValues.CREATED_AT_FROM

    /** Inclusive upper bound filter key for account creation timestamp. */
    const val CREATED_AT_TO = CommonFilterValues.CREATED_AT_TO

    /** Inclusive lower bound filter key for account modification timestamp. */
    const val UPDATED_AT_FROM = CommonFilterValues.UPDATED_AT_FROM

    /** Inclusive upper bound filter key for account modification timestamp. */
    const val UPDATED_AT_TO = CommonFilterValues.UPDATED_AT_TO

    /** Filter by boolean flag indicating whether the user record has been modified. */
    const val IS_UPDATED_AT_SET = "is_updated_at_set"

    /** Filter by inclusive lower bound for scheduled deletion timestamp. */
    const val SCHEDULED_PERMANENT_DELETION_AT_FROM = "scheduled_permanent_deletion_at_from"

    /** Filter by inclusive upper bound for scheduled deletion timestamp. */
    const val SCHEDULED_PERMANENT_DELETION_AT_TO = "scheduled_permanent_deletion_at_to"

    /** Filter by boolean flag indicating whether permanent deletion is scheduled for the user. */
    const val IS_SCHEDULED_PERMANENT_DELETION_AT_SET = "is_scheduled_permanent_deletion_at_set"

    // --- Storage Limits ---

    /** Filter by inclusive lower bound for custom user storage quota in bytes. */
    const val STORAGE_QUOTA_BYTES_FROM = "storage_quota_bytes_from"

    /** Filter by inclusive upper bound for custom user storage quota in bytes. */
    const val STORAGE_QUOTA_BYTES_TO = "storage_quota_bytes_to"

    /** Filter by boolean flag indicating whether a custom storage quota is assigned to the user. */
    const val IS_STORAGE_QUOTA_BYTES_SET = "is_storage_quota_bytes_set"

    /** Filter by inclusive lower bound for custom max upload size in bytes. */
    const val STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM = "storage_max_upload_size_bytes_from"

    /** Filter by inclusive upper bound for custom max upload size in bytes. */
    const val STORAGE_MAX_UPLOAD_SIZE_BYTES_TO = "storage_max_upload_size_bytes_to"

    /** Filter by boolean flag indicating whether a custom max upload size is assigned to the user. */
    const val IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET = "is_storage_max_upload_size_bytes_set"

    /** Filter by inclusive lower bound for custom max pending uploads. */
    const val STORAGE_MAX_PENDING_UPLOADS_FROM = "storage_max_pending_uploads_from"

    /** Filter by inclusive upper bound for custom max pending uploads. */
    const val STORAGE_MAX_PENDING_UPLOADS_TO = "storage_max_pending_uploads_to"

    /** Filter by boolean flag indicating whether custom max pending uploads count is assigned to the user. */
    const val IS_STORAGE_MAX_PENDING_UPLOADS_SET = "is_storage_max_pending_uploads_set"

    /** Filter by boolean flag indicating whether any custom storage configuration overrides exist for the user. */
    const val IS_STORAGE_CONFIGURATION_SET = "is_storage_configuration_set"

    // --- Profile ---

    /** Filter by exact nickname handle match. */
    const val NICKNAME = UserApiFields.NICKNAME

    /** Filter by boolean flag indicating whether the user has a nickname set. */
    const val IS_NICKNAME_SET = "is_nickname_set"

    /** Filter by exact display name match. */
    const val DISPLAY_NAME = UserApiFields.DISPLAY_NAME

    /** Filter by boolean flag indicating whether the user has a display name set. */
    const val IS_DISPLAY_NAME_SET = "is_display_name_set"

    /** Filter by boolean flag indicating whether the user has an avatar image set in storage. */
    const val IS_AVATAR_SET = "is_avatar_set"

    /** Inclusive lower bound filter key for user birthdate. */
    const val BIRTHDATE_FROM = "birthdate_from"

    /** Inclusive upper bound filter key for user birthdate. */
    const val BIRTHDATE_TO = "birthdate_to"

    /** Filter by boolean flag indicating whether the user has a birthdate set. */
    const val IS_BIRTHDATE_SET = "is_birthdate_set"

    // --- Search ---

    /**
     * Free-text search query string targeting:
     * - Nickname handle ([UserPrivate.nickname]).
     * - Display name ([UserPrivate.displayName]).
     */
    const val SEARCH_QUERY = ListingParamNames.Search.SEARCH_QUERY
}
