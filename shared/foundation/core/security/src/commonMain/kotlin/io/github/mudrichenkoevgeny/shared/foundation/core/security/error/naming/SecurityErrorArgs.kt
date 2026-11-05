package io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming

/**
 * Security-specific keys for error arguments used in API responses and backend logging.
 */
public object SecurityErrorArgs {
    /**
     * Minimum password length required by policy.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    public const val PASSWORD_MIN_LENGTH: String = "passwordMinLength"

    /**
     * Set when the password is shorter than the configured minimum length.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    public const val PASSWORD_FAIL_TOO_SHORT: String = "passwordTooShort"

    /**
     * Set when a letter was required but missing.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    public const val PASSWORD_FAIL_NO_LETTER: String = "passwordNoLetter"

    /**
     * Set when an uppercase letter was required but missing.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    public const val PASSWORD_FAIL_NO_UPPERCASE: String = "passwordNoUpperCase"

    /**
     * Set when a lowercase letter was required but missing.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    public const val PASSWORD_FAIL_NO_LOWERCASE: String = "passwordNoLowerCase"

    /**
     * Set when a digit was required but missing.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    public const val PASSWORD_FAIL_NO_DIGIT: String = "passwordNoDigit"

    /**
     * Set when a special character was required but missing.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    public const val PASSWORD_FAIL_NO_SPECIAL_CHAR: String = "passwordNoSpecialChar"

    /**
     * Set when the password matches a forbidden common password.
     * Associated with [SecurityErrorCodes.PASSWORD_TOO_WEAK].
     */
    public const val PASSWORD_FAIL_TOO_COMMON: String = "passwordTooCommon"

    /**
     * Temporary challenge token required to proceed with Multi-Factor Authentication.
     * Associated with [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     */
    public const val MFA_TOKEN: String = "mfaToken"
}
