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
public object UserFilterValues {
    // --- Core & Access ---

    /** Filter by one or more specific user IDs. */
    public const val USER_ID: String = UserApiFields.USER_ID

    /** Filter by one or more [UserRole] wire values (OR semantics). */
    public const val ROLE: String = UserApiFields.ROLE

    /** Filter to exclude specific [UserRole] wire values (NOT IN semantics). */
    public const val EXCLUDE_ROLE: String = "exclude_role"

    /** Filter by one or more [UserAccountStatus] wire values (OR semantics). */
    public const val ACCOUNT_STATUS: String = UserApiFields.ACCOUNT_STATUS

    /** Filter to exclude specific [UserAccountStatus] wire values (NOT IN semantics). */
    public const val EXCLUDE_ACCOUNT_STATUS: String = "exclude_account_status"

    /** Filter by target [UserAccountStatus] assigned upon account restoration (OR semantics). */
    public const val ACCOUNT_STATUS_ON_RESTORE: String = UserApiFields.ACCOUNT_STATUS_ON_RESTORE

    /** Filter to exclude specific target [UserAccountStatus] values on restore (NOT IN semantics). */
    public const val EXCLUDE_ACCOUNT_STATUS_ON_RESTORE: String = "exclude_account_status_on_restore"

    /** Filter by boolean flag indicating whether target account status on restore is set. */
    public const val IS_ACCOUNT_STATUS_ON_RESTORE_SET: String = "is_account_status_on_restore_set"

    /** Filter by inclusive lower bound for authority level. */
    public const val AUTHORITY_LEVEL_FROM: String = "authority_level_from"

    /** Filter by inclusive upper bound for authority level. */
    public const val AUTHORITY_LEVEL_TO: String = "authority_level_to"

    /** Filter by one or more explicit [PermissionCode] strings to include (OR semantics). */
    public const val PERMISSION_CODES: String = UserApiFields.PERMISSION_CODES

    /** Filter by one or more explicit [PermissionCode] strings to exclude (NOT IN semantics). */
    public const val EXCLUDE_PERMISSION_CODES: String = "exclude_permission_codes"

    // --- Security & Lockout ---

    /** Filter by boolean flag for TOTP 2FA status. */
    public const val IS_TOTP_ENABLED: String = UserApiFields.IS_TOTP_ENABLED

    /** Filter by one or more [AccountLockoutType] wire values (OR semantics). */
    public const val ACCOUNT_LOCKOUT_TYPE: String = UserApiFields.ACCOUNT_LOCKOUT_TYPE

    /** Filter to exclude specific [AccountLockoutType] wire values (NOT IN semantics). */
    public const val EXCLUDE_ACCOUNT_LOCKOUT_TYPE: String = "exclude_account_lockout_type"

    /** Filter by inclusive lower bound for temporary lockout expiration timestamp. */
    public const val TEMPORARY_LOCKOUT_UNTIL_FROM: String = "temporary_lockout_until_from"

    /** Filter by inclusive upper bound for temporary lockout expiration timestamp. */
    public const val TEMPORARY_LOCKOUT_UNTIL_TO: String = "temporary_lockout_until_to"

    /** Filter by boolean flag indicating whether a temporary lockout is active for the user. */
    public const val IS_TEMPORARY_LOCKOUT_UNTIL_SET: String = "is_temporary_lockout_until_set"

    // --- Timestamps ---

    /** Filter by inclusive lower bound for last login timestamp. */
    public const val LAST_LOGIN_AT_FROM: String = "last_login_at_from"

    /** Filter by inclusive upper bound for last login timestamp. */
    public const val LAST_LOGIN_AT_TO: String = "last_login_at_to"

    /** Filter by boolean flag indicating whether the user has logged in at least once. */
    public const val IS_LAST_LOGIN_AT_SET: String = "is_last_login_at_set"

    /** Filter by inclusive lower bound for last active timestamp. */
    public const val LAST_ACTIVE_AT_FROM: String = "last_active_at_from"

    /** Filter by inclusive upper bound for last active timestamp. */
    public const val LAST_ACTIVE_AT_TO: String = "last_active_at_to"

    /** Filter by boolean flag indicating whether the user has recorded activity. */
    public const val IS_LAST_ACTIVE_AT_SET: String = "is_last_active_at_set"

    /** Inclusive lower bound filter key for account creation timestamp. */
    public const val CREATED_AT_FROM: String = CommonFilterValues.CREATED_AT_FROM

    /** Inclusive upper bound filter key for account creation timestamp. */
    public const val CREATED_AT_TO: String = CommonFilterValues.CREATED_AT_TO

    /** Inclusive lower bound filter key for account modification timestamp. */
    public const val UPDATED_AT_FROM: String = CommonFilterValues.UPDATED_AT_FROM

    /** Inclusive upper bound filter key for account modification timestamp. */
    public const val UPDATED_AT_TO: String = CommonFilterValues.UPDATED_AT_TO

    /** Filter by boolean flag indicating whether the user record has been modified. */
    public const val IS_UPDATED_AT_SET: String = "is_updated_at_set"

    /** Filter by inclusive lower bound for scheduled deletion timestamp. */
    public const val SCHEDULED_PERMANENT_DELETION_AT_FROM: String = "scheduled_permanent_deletion_at_from"

    /** Filter by inclusive upper bound for scheduled deletion timestamp. */
    public const val SCHEDULED_PERMANENT_DELETION_AT_TO: String = "scheduled_permanent_deletion_at_to"

    /** Filter by boolean flag indicating whether permanent deletion is scheduled for the user. */
    public const val IS_SCHEDULED_PERMANENT_DELETION_AT_SET: String = "is_scheduled_permanent_deletion_at_set"

    // --- Storage Limits ---

    /** Filter by inclusive lower bound for custom user storage quota in bytes. */
    public const val STORAGE_QUOTA_BYTES_FROM: String = "storage_quota_bytes_from"

    /** Filter by inclusive upper bound for custom user storage quota in bytes. */
    public const val STORAGE_QUOTA_BYTES_TO: String = "storage_quota_bytes_to"

    /** Filter by boolean flag indicating whether a custom storage quota is assigned to the user. */
    public const val IS_STORAGE_QUOTA_BYTES_SET: String = "is_storage_quota_bytes_set"

    /** Filter by inclusive lower bound for custom max upload size in bytes. */
    public const val STORAGE_MAX_UPLOAD_SIZE_BYTES_FROM: String = "storage_max_upload_size_bytes_from"

    /** Filter by inclusive upper bound for custom max upload size in bytes. */
    public const val STORAGE_MAX_UPLOAD_SIZE_BYTES_TO: String = "storage_max_upload_size_bytes_to"

    /** Filter by boolean flag indicating whether a custom max upload size is assigned to the user. */
    public const val IS_STORAGE_MAX_UPLOAD_SIZE_BYTES_SET: String = "is_storage_max_upload_size_bytes_set"

    /** Filter by inclusive lower bound for custom max pending uploads. */
    public const val STORAGE_MAX_PENDING_UPLOADS_FROM: String = "storage_max_pending_uploads_from"

    /** Filter by inclusive upper bound for custom max pending uploads. */
    public const val STORAGE_MAX_PENDING_UPLOADS_TO: String = "storage_max_pending_uploads_to"

    /** Filter by boolean flag indicating whether custom max pending uploads count is assigned to the user. */
    public const val IS_STORAGE_MAX_PENDING_UPLOADS_SET: String = "is_storage_max_pending_uploads_set"

    /** Filter by boolean flag indicating whether any custom storage configuration overrides exist for the user. */
    public const val IS_STORAGE_CONFIGURATION_SET: String = "is_storage_configuration_set"

    // --- Profile ---

    /** Filter by exact nickname handle match. */
    public const val NICKNAME: String = UserApiFields.NICKNAME

    /** Filter by boolean flag indicating whether the user has a nickname set. */
    public const val IS_NICKNAME_SET: String = "is_nickname_set"

    /** Filter by exact display name match. */
    public const val DISPLAY_NAME: String = UserApiFields.DISPLAY_NAME

    /** Filter by boolean flag indicating whether the user has a display name set. */
    public const val IS_DISPLAY_NAME_SET: String = "is_display_name_set"

    /** Filter by boolean flag indicating whether the user has an avatar image set in storage. */
    public const val IS_AVATAR_SET: String = "is_avatar_set"

    /** Inclusive lower bound filter key for user birthdate. */
    public const val BIRTHDATE_FROM: String = "birthdate_from"

    /** Inclusive upper bound filter key for user birthdate. */
    public const val BIRTHDATE_TO: String = "birthdate_to"

    /** Filter by boolean flag indicating whether the user has a birthdate set. */
    public const val IS_BIRTHDATE_SET: String = "is_birthdate_set"

    // --- Search ---

    /**
     * Free-text search query string targeting:
     * - Nickname handle ([UserPrivate.nickname]).
     * - Display name ([UserPrivate.displayName]).
     */
    public const val SEARCH_QUERY: String = ListingParamNames.Search.SEARCH_QUERY
}
