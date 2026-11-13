package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider

/**
 * User-feature naming conventions for JSON request bodies, response fields, and filter/sort query keys that belong to
 * this module’s HTTP contracts (authentication, users, identifiers, sessions, and related configuration).
 *
 * Values are stable wire strings shared by server and clients; use these constants with `@SerialName` on DTOs and when
 * documenting endpoints.
 */
public object UserApiFields {
    /** JSON field name for an opaque token from an external identity provider (link/login-by-provider requests). */
    public const val EXTERNAL_PROVIDER_TOKEN: String = "external_provider_token"

    /** JSON field name for the token type of the issued access token (for example `Bearer`). */
    public const val TOKEN_TYPE: String = "token_type"

    /** JSON field name for the session id (in session token payloads and path parameters). */
    public const val SESSION_ID: String = "session_id"

    /** JSON field name for the access token issued for the session. */
    public const val ACCESS_TOKEN: String = "access_token"

    /** JSON field name for the refresh token issued for the session. */
    public const val REFRESH_TOKEN: String = "refresh_token"

    /**
     * JSON field name for the nested session token object on authentication responses (`access_token`, `refresh_token`,
     * expiry, `token_type`).
     */
    public const val SESSION_TOKEN: String = "session_token"

    /**
     * JSON field name for access-token expiry as a millisecond epoch timestamp (session token payload and session list
     * sort keys).
     */
    public const val EXPIRES_AT: String = "expires_at"

    /** JSON field name for an email address (registration, login, identifier flows). */
    public const val EMAIL: String = "email"

    /** JSON field name for a phone number (login, identifier flows). */
    public const val PHONE_NUMBER: String = "phone_number"

    /** JSON field name for a password (login, registration, reset). */
    public const val PASSWORD: String = "password"

    /** JSON field name for the current password when changing credentials. */
    public const val OLD_PASSWORD: String = "old_password"

    /** JSON field name for the new password when changing or resetting credentials. */
    public const val NEW_PASSWORD: String = "new_password"

    /** JSON field name for a one-time or confirmation code (OTP / email confirmation flows). */
    public const val CONFIRMATION_CODE: String = "confirmation_code"

    /** JSON field name for an external authentication provider id; wire value follows [UserAuthProvider.value]. */
    public const val AUTH_PROVIDER: String = "auth_provider"

    /** JSON field name for the nested user object on authentication responses. */
    public const val USER: String = "user"

    /** JSON field name for the user id (path parameter name alignment, payloads, filters). */
    public const val USER_ID: String = "user_id"

    /** JSON field name for the account role; wire value follows the role type’s serial name. */
    public const val ROLE: String = "role"

    /** JSON field name for the account role; wire value follows the role type’s serial name. */
    public const val USER_ROLE: String = "user_role"

    /** JSON field name for the account status; wire value follows the status enum’s serial name. */
    public const val ACCOUNT_STATUS: String = "account_status"

    /**
     * JSON field name for the target account status assigned to the user upon restoring the account / canceling deletion procedure.
     */
    public const val ACCOUNT_STATUS_ON_RESTORE: String = "account_status_on_restore"

    /**
     * JSON field name for the hierarchical weight of the user; used for access control logic and
     * prevention of privilege escalation.
     */
    public const val AUTHORITY_LEVEL: String = "authority_level"

    /** JSON field name for the list of explicit permission codes represented as strings. */
    public const val PERMISSION_CODES: String = "permission_codes"

    /**
     * JSON field name indicating whether Time-based One-Time Password (TOTP) 2FA is enabled for the account.
     */
    public const val IS_TOTP_ENABLED: String = "is_totp_enabled"

    /** JSON field name for last login timestamp (epoch milliseconds; user listings and sort keys). */
    public const val LAST_LOGIN_AT: String = "last_login_at"

    /** JSON field name for last activity timestamp (epoch milliseconds; user listings and sort keys). */
    public const val LAST_ACTIVE_AT: String = "last_active_at"

    /**
     * JSON field name for scheduled permanent-deletion timestamp (epoch milliseconds; user listings and sort keys).
     */
    public const val SCHEDULED_PERMANENT_DELETION_AT: String = "scheduled_permanent_deletion_at"

    /** JSON field name for the account lockout state type. */
    public const val ACCOUNT_LOCKOUT_TYPE: String = "account_lockout_type"

    /**
     * JSON field name for temporary lockout expiration timestamp (epoch milliseconds).
     */
    public const val TEMPORARY_LOCKOUT_UNTIL: String = "temporary_lockout_until"

    /**
     * JSON field name for the credential value or free-text identifier match (identifier payloads, session/identifier
     * filters).
     */
    public const val IDENTIFIER: String = "identifier"

    /**
     * JSON field name for the email address linked to an external authentication provider.
     */
    public const val EXTERNAL_PROVIDER_EMAIL: String = "external_provider_email"

    /**
     * JSON field name for the authentication provider dimension on identifiers or sessions; wire value follows
     * [UserAuthProvider.value].
     */
    public const val USER_AUTH_PROVIDER: String = "user_auth_provider"

    /** JSON field name for the credential record id tied to a session row. */
    public const val IDENTIFIER_ID: String = "identifier_id"

    /** JSON field name for the user's unique nickname or handle. */
    public const val NICKNAME: String = "nickname"

    /** JSON field name for the display name of an identifier or user profile. */
    public const val DISPLAY_NAME: String = "display_name"

    /** JSON field name for the avatar file identifier in core/storage. */
    public const val AVATAR_FILE_ID: String = "avatar_file_id"

    /** JSON field name for structured avatar image URL variants. */
    public const val AVATAR_URLS: String = "avatar_urls"

    /** JSON field name for user birthdate in Unix epoch milliseconds. */
    public const val BIRTHDATE: String = "birthdate"

    /** JSON field name for minimum allowed user nickname character length. */
    public const val USER_NICKNAME_MIN_LENGTH: String = "user_nickname_min_length"

    /** JSON field name for maximum allowed user nickname character length. */
    public const val USER_NICKNAME_MAX_LENGTH: String = "user_nickname_max_length"

    /** JSON field name for minimum allowed user display name character length. */
    public const val USER_DISPLAY_NAME_MIN_LENGTH: String = "user_display_name_min_length"

    /** JSON field name for maximum allowed user display name character length. */
    public const val USER_DISPLAY_NAME_MAX_LENGTH: String = "user_display_name_max_length"

    /** JSON field name for the flag indicating whether users are permitted to change their nickname. */
    public const val IS_USER_NICKNAME_CHANGE_ALLOWED: String = "is_user_nickname_change_allowed"

    /** JSON field name for the display name of the identifier associated with a session. */
    public const val IDENTIFIER_DISPLAY_NAME: String = "identifier_display_name"

    /**
     * JSON field name for the provider of the session’s credential; wire value follows [UserAuthProvider.value]
     * (session payloads).
     */
    public const val IDENTIFIER_AUTH_PROVIDER: String = "identifier_auth_provider"

    /** JSON field name for last access timestamp on a session (epoch milliseconds; payloads and sort keys). */
    public const val LAST_ACCESSED_AT: String = "last_accessed_at"

    /**
     * JSON field name for the last step-up or re-authentication timestamp on a session, in epoch milliseconds.
     */
    public const val LAST_REAUTHENTICATED_AT: String = "last_reauthenticated_at"

    /** JSON field name for the list of deleted session ids. */
    public const val DELETED_SESSION_IDS: String = "deleted_session_ids"

    /** JSON field name for the maximum total identifiers allowed per account. */
    public const val MAX_TOTAL_IDENTIFIERS: String = "max_total_identifiers"

    /** JSON field name for the maximum email identifiers allowed per account. */
    public const val MAX_EMAIL_IDENTIFIERS: String = "max_email_identifiers"

    /** JSON field name for the maximum phone identifiers allowed per account. */
    public const val MAX_PHONE_IDENTIFIERS: String = "max_phone_identifiers"

    /** JSON field name for the maximum identifiers allowed per specific external provider. */
    public const val MAX_IDENTIFIERS_PER_EXTERNAL_PROVIDER: String = "max_identifiers_per_external_provider"

    /** JSON field name for the maximum concurrent active sessions allowed per open user account. */
    public const val MAX_ACTIVE_SESSIONS_FOR_OPEN_USER: String = "max_active_sessions_for_open_user"

    /** JSON field name for the maximum concurrent active sessions allowed per management user account. */
    public const val MAX_ACTIVE_SESSIONS_FOR_MANAGEMENT_USER: String = "max_active_sessions_for_management_user"

    /**
     * JSON field name for provider availability lists (primary and secondary); entries use [UserAuthProvider.value]
     * on the wire (open and management auth settings).
     */
    public const val AVAILABLE_AUTH_PROVIDERS: String = "available_auth_providers"

    /**
     * JSON field name for the validity window (in seconds) applied to **newly issued** access tokens.
     */
    public const val ACCESS_TOKEN_VALIDITY_SECONDS: String = "access_token_validity_seconds"

    /**
     * JSON field name for the validity window (in seconds) applied to **newly issued** refresh tokens.
     */
    public const val REFRESH_TOKEN_VALIDITY_SECONDS: String = "refresh_token_validity_seconds"

    /**
     * JSON field name for the grace period (in seconds) between scheduling account deletion and its execution.
     */
    public const val ACCOUNT_DELETION_GRACE_PERIOD_SECONDS: String = "account_deletion_grace_period_seconds"

    /**
     * JSON field name for the background worker check interval (in seconds) for accounts eligible for permanent deletion.
     */
    public const val ACCOUNT_DELETION_CHECK_INTERVAL_SECONDS: String = "account_deletion_check_interval_seconds"

    /**
     * JSON field name for the global flag indicating whether new user registrations are permitted.
     */
    public const val IS_REGISTRATION_ENABLED: String = "is_registration_enabled"

    /** JSON field name for open API email restriction policy object. */
    public const val OPEN_EMAIL_RESTRICTION_POLICY: String = "open_email_restriction_policy"

    /** JSON field name for management API email restriction policy object. */
    public const val MANAGEMENT_EMAIL_RESTRICTION_POLICY: String = "management_email_restriction_policy"

    /**
     * JSON field name for the list of primary (main) authentication methods under [AVAILABLE_AUTH_PROVIDERS]; values use
     * [UserAuthProvider.value].
     */
    public const val PRIMARY: String = "primary"

    /**
     * JSON field name for the list of secondary (alternative) authentication methods under [AVAILABLE_AUTH_PROVIDERS];
     * values use [UserAuthProvider.value].
     */
    public const val SECONDARY: String = "secondary"

    /** JSON field name for the nested open global settings object on user configuration responses. */
    public const val OPEN_GLOBAL_SETTINGS: String = "open_global_settings"

    /** JSON field name for the nested management global settings object on user configuration responses. */
    public const val MANAGEMENT_GLOBAL_SETTINGS: String = "management_global_settings"

    /** JSON field name for the nested open security settings object on user configuration responses. */
    public const val OPEN_SECURITY_SETTINGS: String = "open_security_settings"

    /** JSON field name for the nested management security settings object on user configuration responses. */
    public const val MANAGEMENT_SECURITY_SETTINGS: String = "management_security_settings"

    /** JSON field name for the nested open authentication settings object on user configuration responses. */
    public const val OPEN_AUTH_SETTINGS: String = "open_auth_settings"

    /** JSON field name for the nested management authentication settings object on user configuration responses. */
    public const val MANAGEMENT_AUTH_SETTINGS: String = "management_auth_settings"

    /** JSON field name for the nested open storage settings object on user configuration responses. */
    public const val OPEN_STORAGE_SETTINGS: String = "open_storage_settings"

    /** JSON field name for the nested management storage settings object on user configuration responses. */
    public const val MANAGEMENT_STORAGE_SETTINGS: String = "management_storage_settings"

    /** JSON field name for the user storage configuration object or property. */
    public const val STORAGE_CONFIGURATION: String = "storage_configuration"

    /** JSON field name for custom storage quota in bytes. */
    public const val QUOTA_BYTES: String = "quota_bytes"

    /** JSON field name for custom maximum upload size in bytes. */
    public const val MAX_UPLOAD_SIZE_BYTES: String = "max_upload_size_bytes"

    /** JSON field name for custom maximum pending uploads count. */
    public const val MAX_PENDING_UPLOADS: String = "max_pending_uploads"
}
