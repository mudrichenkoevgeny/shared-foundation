package io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.constants

/**
 * User-specific keys for error arguments used in API responses and backend logging.
 */
object UserErrorArgs {
    const val USER_ID = "userId"
    const val PASSWORD_MIN_LENGTH = "passwordMinLength"
    const val PASSWORD_FAIL_TOO_SHORT = "passwordTooShort"
    const val PASSWORD_FAIL_NO_LETTER = "passwordNoLetter"
    const val PASSWORD_FAIL_NO_UPPERCASE = "passwordNoUpperCase"
    const val PASSWORD_FAIL_NO_LOWERCASE = "passwordNoLowerCase"
    const val PASSWORD_FAIL_NO_DIGIT = "passwordNoDigit"
    const val PASSWORD_FAIL_NO_SPECIAL_CHAR = "passwordNoSpecialChar"
    const val PASSWORD_FAIL_TOO_COMMON = "passwordTooCommon"
}