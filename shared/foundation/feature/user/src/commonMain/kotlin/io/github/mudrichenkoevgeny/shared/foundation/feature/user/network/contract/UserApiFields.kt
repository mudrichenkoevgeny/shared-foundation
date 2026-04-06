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

    /**
     * JSON field name for the suggested client retry delay after rate limiting, in whole seconds (confirmation /
     * send-code responses).
     */
    const val RETRY_AFTER_SECONDS = "retry_after_seconds"

    /**
     * JSON field name for the OTP length or number of symbols to display (confirmation responses; server-defined).
     */
    const val NUMBER_OF_SYMBOLS = "number_of_symbols"

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

    /** JSON field name for explicit permission grants as a JSON array of permission code strings. */
    const val PERMISSIONS = "permissions"

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
     * JSON field name for the revoked flag (`true` if the session is no longer valid for authentication, for example
     * soft-revoked but the row is retained).
     */
    const val REVOKED = "revoked"

    /**
     * JSON field name for the last step-up or re-authentication timestamp on a session, in epoch milliseconds.
     */
    const val LAST_REAUTHENTICATED_AT = "last_reauthenticated_at"

    /**
     * JSON field name for provider availability lists (primary and secondary); entries use [UserAuthProvider.serialName]
     * on the wire (public and management auth settings).
     */
    const val AVAILABLE_AUTH_PROVIDERS = "available_auth_providers"

    /**
     * JSON field name for whole-hour validity applied to **newly issued** access tokens (management auth-settings body).
     */
    const val ACCESS_TOKEN_VALIDITY_HOURS = "access_token_validity_hours"

    /**
     * JSON field name for whole-day validity applied to **newly issued** refresh tokens (management auth-settings body).
     */
    const val REFRESH_TOKEN_VALIDITY_DAYS = "refresh_token_validity_days"

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
