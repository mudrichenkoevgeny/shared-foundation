package io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming

/**
 * Security-specific keys for error arguments used in API responses and backend logging.
 */
object SecurityErrorArgs {
    /**
     * Temporary challenge token required to proceed with Multi-Factor Authentication.
     * Associated with [SecurityErrorCodes.TOTP_CONFIRMATION_REQUIRED].
     */
    const val MFA_TOKEN = "mfaToken"

    /**
     * Minimum password length required by policy.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    const val PASSWORD_MIN_LENGTH = "passwordMinLength"

    /**
     * Set when the password is shorter than the configured minimum length.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    const val PASSWORD_FAIL_TOO_SHORT = "passwordTooShort"

    /**
     * Set when a letter was required but missing.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    const val PASSWORD_FAIL_NO_LETTER = "passwordNoLetter"

    /**
     * Set when an uppercase letter was required but missing.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    const val PASSWORD_FAIL_NO_UPPERCASE = "passwordNoUpperCase"

    /**
     * Set when a lowercase letter was required but missing.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    const val PASSWORD_FAIL_NO_LOWERCASE = "passwordNoLowerCase"

    /**
     * Set when a digit was required but missing.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    const val PASSWORD_FAIL_NO_DIGIT = "passwordNoDigit"

    /**
     * Set when a special character was required but missing.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    const val PASSWORD_FAIL_NO_SPECIAL_CHAR = "passwordNoSpecialChar"

    /**
     * Set when the password matches a forbidden common password.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    const val PASSWORD_FAIL_TOO_COMMON = "passwordTooCommon"
}