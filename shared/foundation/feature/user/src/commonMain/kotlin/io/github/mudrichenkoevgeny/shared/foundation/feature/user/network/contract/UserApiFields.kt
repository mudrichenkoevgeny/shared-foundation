package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider

/**
 * User-feature naming conventions for JSON request bodies, response fields, and filter/sort query keys that belong to
 * this module’s HTTP contracts (authentication, users, identifiers, sessions, and related configuration).
 *
 * Values are stable wire strings shared by server and clients; use these constants with `@SerialName` on DTOs and when
 * documenting endpoints.
 */
object UserApiFields {

    /** JSON field name for an opaque token from an external identity provider (link/login-by-provider requests). */
    const val TOKEN = "token"

    /** JSON field name for the token type of the issued access token (for example `Bearer`). */
    const val TOKEN_TYPE = "token_type"

    /** JSON field name for the access token issued for the session. */
    const val ACCESS_TOKEN = "access_token"

    /** JSON field name for the refresh token issued for the session. */
    const val REFRESH_TOKEN = "refresh_token"

    /**
     * JSON field name for the nested session token object on authentication responses (`access_token`, `refresh_token`,
     * expiry, `token_type`).
     */
    const val SESSION_TOKEN = "session_token"

    /**
     * JSON field name for access-token expiry as a millisecond epoch timestamp (session token payload and session list
     * sort keys).
     */
    const val EXPIRES_AT = "expires_at"

    /** JSON field name for an email address (registration, login, identifier flows). */
    const val EMAIL = "email"

    /** JSON field name for a phone number (login, identifier flows). */
    const val PHONE_NUMBER = "phone_number"

    /** JSON field name for a password (login, registration, reset). */
    const val PASSWORD = "password"

    /** JSON field name for the current password when changing credentials. */
    const val OLD_PASSWORD = "old_password"

    /** JSON field name for the new password when changing or resetting credentials. */
    const val NEW_PASSWORD = "new_password"

    /** JSON field name for a one-time or confirmation code (OTP / email confirmation flows). */
    const val CONFIRMATION_CODE = "confirmation_code"

    /** JSON field name for an external authentication provider id; wire value follows [UserAuthProvider.serialName]. */
    const val AUTH_PROVIDER = "auth_provider"

    /** JSON field name for the nested user profile object on authentication responses. */
    const val USER = "user"

    /** JSON field name for the user id (path parameter name alignment, payloads, filters). */
    const val USER_ID = "user_id"

    /** JSON field name for the account role; wire value follows the role type’s serial name. */
    const val ROLE = "role"

    /** JSON field name for the account status; wire value follows the status enum’s serial name. */
    const val ACCOUNT_STATUS = "account_status"

    /**
     * JSON field name for the account status snapshot before deletion was scheduled; wire value follows the status enum’s
     * serial name (user details, filters).
     */
    const val ACCOUNT_STATUS_BEFORE_DELETION = "account_status_before_deletion"

    /**
     * JSON field name for the hierarchical weight of the user; used for access control logic and
     * prevention of privilege escalation.
     */
    const val AUTHORITY_LEVEL = "authority_level"

    /** JSON field name for the list of explicit permission codes represented as strings. */
    const val PERMISSION_CODES = "permission_codes"

    /**
     * JSON field name indicating whether Time-based One-Time Password (TOTP) 2FA is enabled for the account.
     */
    const val IS_TOTP_ENABLED = "is_totp_enabled"

    /** JSON field name for last login timestamp (epoch milliseconds; user listings and sort keys). */
    const val LAST_LOGIN_AT = "last_login_at"

    /** JSON field name for last activity timestamp (epoch milliseconds; user listings and sort keys). */
    const val LAST_ACTIVE_AT = "last_active_at"

    /**
     * JSON field name for scheduled permanent-deletion timestamp (epoch milliseconds; user listings and sort keys).
     */
    const val SCHEDULED_PERMANENT_DELETION_AT = "scheduled_permanent_deletion_at"

    /**
     * JSON field name for the credential value or free-text identifier match (identifier payloads, session/identifier
     * filters).
     */
    const val IDENTIFIER = "identifier"

    /**
     * JSON field name for the email address linked to an external authentication provider.
     */
    const val EXTERNAL_PROVIDER_EMAIL = "external_provider_email"

    /**
     * JSON field name for the authentication provider dimension on identifiers or sessions; wire value follows
     * [UserAuthProvider.serialName].
     */
    const val USER_AUTH_PROVIDER = "user_auth_provider"

    /** JSON field name for the credential record id tied to a session row. */
    const val IDENTIFIER_ID = "identifier_id"

    /**
     * JSON field name for the provider of the session’s credential; wire value follows [UserAuthProvider.serialName]
     * (session payloads).
     */
    const val IDENTIFIER_AUTH_PROVIDER = "identifier_auth_provider"

    /** JSON field name for last access timestamp on a session (epoch milliseconds; payloads and sort keys). */
    const val LAST_ACCESSED_AT = "last_accessed_at"

    /**
     * JSON field name for the last step-up or re-authentication timestamp on a session, in epoch milliseconds.
     */
    const val LAST_REAUTHENTICATED_AT = "last_reauthenticated_at"

    /** JSON field name for the list of deleted session ids. */
    const val DELETED_SESSION_IDS = "deleted_session_ids"

    /** JSON field name for the list of deleted credential identifiers. */
    const val DELETED_IDENTIFIER_IDS = "deleted_identifier_ids"

    /** JSON field name for the maximum total identifiers allowed per account. */
    const val MAX_TOTAL_IDENTIFIERS = "max_total_identifiers"

    /** JSON field name for the maximum email identifiers allowed per account. */
    const val MAX_EMAIL_IDENTIFIERS = "max_email_identifiers"

    /** JSON field name for the maximum phone identifiers allowed per account. */
    const val MAX_PHONE_IDENTIFIERS = "max_phone_identifiers"

    /** JSON field name for the maximum identifiers allowed per specific external provider. */
    const val MAX_IDENTIFIERS_PER_EXTERNAL_PROVIDER = "max_identifiers_per_external_provider"

    /** JSON field name for the maximum concurrent active sessions allowed per account. */
    const val MAX_ACTIVE_SESSIONS = "max_active_sessions"

    /**
     * JSON field name for provider availability lists (primary and secondary); entries use [UserAuthProvider.serialName]
     * on the wire (public and management auth settings).
     */
    const val AVAILABLE_AUTH_PROVIDERS = "available_auth_providers"

    /**
     * JSON field name for the validity window (in seconds) applied to **newly issued** access tokens.
     */
    const val ACCESS_TOKEN_VALIDITY_SECONDS = "access_token_validity_seconds"

    /**
     * JSON field name for the validity window (in seconds) applied to **newly issued** refresh tokens.
     */
    const val REFRESH_TOKEN_VALIDITY_SECONDS = "refresh_token_validity_seconds"

    /**
     * JSON field name for the delay (in seconds) between scheduling account deletion and its execution.
     */
    const val ACCOUNT_DELETION_DELAY_SECONDS = "account_deletion_delay_seconds"

    /**
     * JSON field name for the list of primary (main) authentication methods under [AVAILABLE_AUTH_PROVIDERS]; values use
     * [UserAuthProvider.serialName].
     */
    const val PRIMARY = "primary"

    /**
     * JSON field name for the list of secondary (alternative) authentication methods under [AVAILABLE_AUTH_PROVIDERS];
     * values use [UserAuthProvider.serialName].
     */
    const val SECONDARY = "secondary"

    /** JSON field name for the nested global settings object on user configuration responses. */
    const val GLOBAL_SETTINGS = "global_settings"

    /** JSON field name for the nested security settings object on user configuration responses. */
    const val SECURITY_SETTINGS = "security_settings"

    /** JSON field name for the nested public authentication settings object on user configuration responses. */
    const val AUTH_SETTINGS = "auth_settings"
}
