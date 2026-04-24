package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract

/**
 * Security settings specific naming conventions for API request parameters and response fields.
 */
object SecurityApiFields {

    /**
     * JSON field name for how long, in whole minutes, a recent step-up or re-authentication remains valid
     * for sensitive operations.
     */
    const val RECENT_AUTHENTICATION_VALIDITY_IN_MINUTES = "recent_authentication_validity_in_minutes"

    /** JSON field name for the temporary MFA challenge token used during multifactor authentication flows. */
    const val MFA_TOKEN = "mfa_token"

    /** JSON field name for the Time-based One-Time Password (TOTP) code. */
    const val TOTP_CODE = "totp_code"

    /** JSON field name for the shared secret key (Base32) used for TOTP generation. */
    const val TOTP_SECRET_KEY = "totp_secret_key"

    /** JSON field name for the standard `otpauth://` URI for provisioning TOTP. */
    const val TOTP_OTP_AUTH_URL = "totp_otp_auth_url"

    /** JSON field name for the list of backup recovery codes. */
    const val TOTP_RECOVERY_CODES = "totp_recovery_codes"

    /** JSON field name for the nested password policy object. */
    const val PASSWORD_POLICY = "password_policy"

    /** JSON field name for minimum password length. */
    const val MIN_LENGTH = "min_length"

    /** JSON field name for the “at least one letter” requirement flag. */
    const val REQUIRE_LETTER = "require_letter"

    /** JSON field name for the “at least one uppercase letter” requirement flag. */
    const val REQUIRE_UPPER_CASE = "require_upper_case"

    /** JSON field name for the “at least one lowercase letter” requirement flag. */
    const val REQUIRE_LOWER_CASE = "require_lower_case"

    /** JSON field name for the “at least one digit” requirement flag. */
    const val REQUIRE_DIGIT = "require_digit"

    /** JSON field name for the “at least one special character” requirement flag. */
    const val REQUIRE_SPECIAL_CHAR = "require_special_char"

    /** JSON field name for the list (encoded as JSON array) of forbidden common passwords. */
    const val COMMON_PASSWORDS = "common_passwords"

    /** JSON field name for the nested OTP confirmation object. */
    const val OTP_CONFIRMATION = "otp_confirmation"

    /** JSON field name for the cooling-off period in seconds before a new OTP can be requested. */
    const val RETRY_AFTER_SECONDS = "retry_after_seconds"

    /** JSON field name for the required length of the generated OTP code. */
    const val NUMBER_OF_SYMBOLS = "number_of_symbols"

    /** JSON field name for the time-to-live (TTL) in seconds for the generated OTP. */
    const val EXPIRATION_SECONDS = "expiration_seconds"
}
