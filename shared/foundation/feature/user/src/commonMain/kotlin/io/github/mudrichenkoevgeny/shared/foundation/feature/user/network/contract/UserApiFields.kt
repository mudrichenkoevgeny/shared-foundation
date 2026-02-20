package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

/**
 * User-specific naming conventions for API request parameters and response fields.
 */
object UserApiFields {
    // Client
    const val CLIENT_TYPE = "client_type"
    const val USER_AGENT = "user_agent"
    const val IP_ADDRESS = "ip_address"
    const val DEVICE_NAME = "device_name"

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

    // User
    const val USER = "user"
    const val USER_ID = "user_id"
    const val ROLE = "role"
    const val ACCOUNT_STATUS = "account_status"
    const val LAST_LOGIN_AT = "last_login_at"
    const val LAST_ACTIVE_AT = "last_active_at"
    const val LAST_ACCESSED_AT = "last_accessed_at"

    // Identifiers, Providers
    const val IDENTIFIER = "identifier"
    const val IDENTIFIER_ID = "identifier_id"
    const val IDENTIFIER_AUTH_PROVIDER = "identifier_auth_provider"
    const val USER_AUTH_PROVIDER = "user_auth_provider"
    const val USER_IDENTIFIERS = "user_identifiers"

    // Settings
    const val AVAILABLE_AUTH_PROVIDERS = "available_auth_providers"
    const val PRIMARY = "primary"
    const val SECONDARY = "secondary"
    const val GLOBAL_SETTINGS = "global_settings"
    const val SECURITY_SETTINGS = "security_settings"
    const val AUTH_SETTINGS = "auth_settings"
}