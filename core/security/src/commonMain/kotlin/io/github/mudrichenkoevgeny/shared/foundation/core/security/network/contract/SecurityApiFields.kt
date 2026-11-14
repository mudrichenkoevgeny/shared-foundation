package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract

/**
 * Security settings specific naming conventions for API request parameters and response fields.
 */
public object SecurityApiFields {
    /**
     * JSON field name for how long a recent step-up or re-authentication remains valid
     * for open user actions, in seconds.
     */
    public const val RECENT_AUTHENTICATION_VALIDITY_SECONDS_FOR_OPEN_USER: String =
        "recent_authentication_validity_seconds_for_open_user"

    /**
     * JSON field name for how long a recent re-authentication remains valid
     * for management user actions, in seconds.
     */
    public const val RECENT_AUTHENTICATION_VALIDITY_SECONDS_FOR_MANAGEMENT_USER: String =
        "recent_authentication_validity_seconds_for_management_user"

    /** JSON field name for the temporary MFA challenge token used during multifactor authentication flows. */
    public const val MFA_TOKEN: String = "mfa_token"

    /** JSON field name for the Time-based One-Time Password (TOTP) code. */
    public const val TOTP_CODE: String = "totp_code"

    /** JSON field name for the shared secret key (Base32) used for TOTP generation. */
    public const val TOTP_SECRET_KEY: String = "totp_secret_key"

    /** JSON field name for the standard `otpauth://` URI for provisioning TOTP. */
    public const val TOTP_OTP_AUTH_URL: String = "totp_otp_auth_url"

    /** JSON field name for the list of backup recovery codes. */
    public const val TOTP_RECOVERY_CODES: String = "totp_recovery_codes"

    /** JSON field name for the open password policy object. */
    public const val OPEN_PASSWORD_POLICY: String = "open_password_policy"

    /** JSON field name for the management password policy object. */
    public const val MANAGEMENT_PASSWORD_POLICY: String = "management_password_policy"

    /** JSON field name for minimum password length. */
    public const val MIN_LENGTH: String = "min_length"

    /** JSON field name for the “at least one letter” requirement flag. */
    public const val REQUIRE_LETTER: String = "require_letter"

    /** JSON field name for the “at least one uppercase letter” requirement flag. */
    public const val REQUIRE_UPPER_CASE: String = "require_upper_case"

    /** JSON field name for the “at least one lowercase letter” requirement flag. */
    public const val REQUIRE_LOWER_CASE: String = "require_lower_case"

    /** JSON field name for the “at least one digit” requirement flag. */
    public const val REQUIRE_DIGIT: String = "require_digit"

    /** JSON field name for the “at least one special character” requirement flag. */
    public const val REQUIRE_SPECIAL_CHAR: String = "require_special_char"

    /** JSON field name for the list (encoded as JSON array) of forbidden common passwords. */
    public const val COMMON_PASSWORDS: String = "common_passwords"

    /** JSON field name for the nested OTP confirmation object. */
    public const val OTP_CONFIRMATION: String = "otp_confirmation"

    /** JSON field name for the cooling-off period in seconds before a new OTP can be requested. */
    public const val RETRY_AFTER_SECONDS: String = "retry_after_seconds"

    /** JSON field name for the required length of the generated OTP code. */
    public const val NUMBER_OF_SYMBOLS: String = "number_of_symbols"

    /** JSON field name for the time-to-live (TTL) in seconds for the generated OTP. */
    public const val EXPIRATION_SECONDS: String = "expiration_seconds"

    /** JSON field name for the maximum number of requests allowed per time unit. */
    public const val MAX_REQUESTS_PER_PERIOD: String = "max_requests_per_period"

    /** JSON field name for the rate limiting time window in seconds. */
    public const val RATE_LIMIT_PERIOD_SECONDS: String = "rate_limit_period_seconds"

    /** JSON field name for the refresh token rotation grace period in seconds (used for replay attack detection). */
    public const val REFRESH_TOKEN_ROTATION_GRACE_PERIOD_SECONDS: String = "refresh_token_rotation_grace_period_seconds"

    /** JSON field name for the nested account lockout policy object. */
    public const val ACCOUNT_LOCKOUT_POLICY: String = "account_lockout_policy"

    /** JSON field name for the background worker check interval (in seconds) for expiring account lockouts. */
    public const val ACCOUNT_LOCKOUT_CHECK_INTERVAL_SECONDS: String = "account_lockout_check_interval_seconds"

    /** JSON field name for maximum consecutive wrong password attempts. */
    public const val MAX_FAILED_PASSWORD_ATTEMPTS: String = "max_failed_password_attempts"

    /** JSON field name for maximum consecutive wrong OTP code attempts. */
    public const val MAX_FAILED_OTP_ATTEMPTS: String = "max_failed_otp_attempts"

    /** JSON field name for maximum consecutive wrong TOTP attempts. */
    public const val MAX_FAILED_TOTP_ATTEMPTS: String = "max_failed_totp_attempts"

    /** JSON field name for the failed attempts accumulation time window in seconds. */
    public const val FAILED_ATTEMPTS_WINDOW_SECONDS: String = "failed_attempts_window_seconds"

    /** JSON field name for temporary lockout duration in seconds. */
    public const val LOCKOUT_DURATION_SECONDS: String = "lockout_duration_seconds"

    /** JSON field name for the indefinite lockout threshold count. */
    public const val INDEFINITE_LOCKOUT_THRESHOLD: String = "indefinite_lockout_threshold"

    /** JSON field name for the self-service unlock enablement flag. */
    public const val IS_SELF_SERVICE_UNLOCK_ENABLED: String = "is_self_service_unlock_enabled"

    /** JSON field name for open API IP restriction policy object. */
    public const val OPEN_IP_RESTRICTION_POLICY: String = "open_ip_restriction_policy"

    /** JSON field name for management API IP restriction policy object. */
    public const val MANAGEMENT_IP_RESTRICTION_POLICY: String = "management_ip_restriction_policy"

    /** JSON field name for the blacklist enablement flag. */
    public const val IS_BLACKLIST_ENABLED: String = "is_blacklist_enabled"

    /** JSON field name for the blacklist items array. */
    public const val BLACKLIST: String = "blacklist"

    /** JSON field name for the whitelist enablement flag. */
    public const val IS_WHITELIST_ENABLED: String = "is_whitelist_enabled"

    /** JSON field name for the whitelist items array. */
    public const val WHITELIST: String = "whitelist"
}
