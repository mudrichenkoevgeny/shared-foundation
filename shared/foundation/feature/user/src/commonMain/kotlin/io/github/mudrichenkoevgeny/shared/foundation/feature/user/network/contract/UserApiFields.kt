package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

/**
 * User-specific naming conventions for API request parameters and response fields.
 */
object UserApiFields {
    // Tokens, Session
    const val TOKEN = "token"
    const val TOKEN_TYPE = "token_type"
    const val ACCESS_TOKEN = "access_token"
    const val REFRESH_TOKEN = "refresh_token"
    const val SESSION_TOKEN = "session_token"
    const val SESSION_ID = "session_id"
    const val EXPIRES_AT = "expires_at"

    // Auth
    const val EMAIL = "email"
    const val PHONE_NUMBER = "phone_number"
    const val PASSWORD = "password"
    const val OLD_PASSWORD = "old_password"
    const val NEW_PASSWORD = "new_password"
    const val CONFIRMATION_CODE = "confirmation_code"
    const val AUTH_PROVIDER = "auth_provider"

    // Rate Limiting
    const val RETRY_AFTER_SECONDS = "retry_after_seconds"

    // OTP
    const val NUMBER_OF_SYMBOLS = "number_of_symbols"

    // User
    const val USER = "user"
    const val USER_ID = "user_id"
    const val ROLE = "role"
    const val ACCOUNT_STATUS = "account_status"
    const val ACCOUNT_STATUS_BEFORE_DELETION = "account_status_before_deletion"
    const val PERMISSIONS = "permissions"
    const val LAST_LOGIN_AT = "last_login_at"
    const val LAST_ACTIVE_AT = "last_active_at"
    const val SCHEDULED_PERMANENT_DELETION_AT = "scheduled_permanent_deletion_at"

    // Identifiers, Providers
    const val IDENTIFIER = "identifier"
    const val MASKED_IDENTIFIER = "masked_identifier"
    const val USER_AUTH_PROVIDER = "user_auth_provider"
    const val USER_IDENTIFIERS = "user_identifiers"

    // Session
    const val IDENTIFIER_ID = "identifier_id"
    const val IDENTIFIER_AUTH_PROVIDER = "identifier_auth_provider"
    const val LAST_ACCESSED_AT = "last_accessed_at"

    /**
     * When `true`, the session is no longer valid for authentication (e.g. soft-revoked but row retained).
     */
    const val REVOKED = "revoked"

    /**
     * Timestamp of the last step-up / re-authentication for the session, in milliseconds.
     */
    const val LAST_REAUTHENTICATED_AT = "last_reauthenticated_at"

    // Settings
    const val AVAILABLE_AUTH_PROVIDERS = "available_auth_providers"

    /**
     * Whole-hour validity for newly issued access tokens (e.g. JWT), management auth-settings contract.
     */
    const val ACCESS_TOKEN_VALIDITY_HOURS = "access_token_validity_hours"

    /**
     * Whole-day validity for newly issued refresh tokens, management auth-settings contract.
     */
    const val REFRESH_TOKEN_VALIDITY_DAYS = "refresh_token_validity_days"

    const val PRIMARY = "primary"
    const val SECONDARY = "secondary"
    const val GLOBAL_SETTINGS = "global_settings"
    const val SECURITY_SETTINGS = "security_settings"
    const val AUTH_SETTINGS = "auth_settings"
}