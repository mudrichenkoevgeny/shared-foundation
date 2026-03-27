package io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming

/**
 * Security-specific keys for error arguments used in API responses and backend logging.
 */
object SecurityErrorArgs {
    /** Minimum password length required by policy (typically a numeric string in the API error `args` map). */
    const val PASSWORD_MIN_LENGTH = "passwordMinLength"

    /** Set when the password is shorter than the configured minimum length. */
    const val PASSWORD_FAIL_TOO_SHORT = "passwordTooShort"

    /** Set when a letter was required but missing. */
    const val PASSWORD_FAIL_NO_LETTER = "passwordNoLetter"

    /** Set when an uppercase letter was required but missing. */
    const val PASSWORD_FAIL_NO_UPPERCASE = "passwordNoUpperCase"

    /** Set when a lowercase letter was required but missing. */
    const val PASSWORD_FAIL_NO_LOWERCASE = "passwordNoLowerCase"

    /** Set when a digit was required but missing. */
    const val PASSWORD_FAIL_NO_DIGIT = "passwordNoDigit"

    /** Set when a special character was required but missing. */
    const val PASSWORD_FAIL_NO_SPECIAL_CHAR = "passwordNoSpecialChar"

    /** Set when the password matches a forbidden common password. */
    const val PASSWORD_FAIL_TOO_COMMON = "passwordTooCommon"
}
