package io.github.mudrichenkoevgeny.shared.foundation.core.security.localization

import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorArgs
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes

/**
 * Extension functions providing smart, contextual resolution of localized error messages
 * for security domain errors.
 */

/**
 * Resolves localized failure reason messages for [SecurityErrorCodes.PASSWORD_TOO_WEAK].
 *
 * Evaluates the `args` map returned in `ApiErrorResponse` and returns a list of human-readable
 * localized validation hint strings for each violated password rule (e.g. `"Password must be at least 12 characters long."`).
 *
 * @param args Raw error arguments map from `ApiErrorResponse.args`.
 * @return List of localized rule violation strings. If no specific flags match, returns a single list item with `passwordTooWeak`.
 */
fun SecurityModuleStrings.resolvePasswordTooWeakReasons(args: Map<String, Any?>?): List<String> {
    if (args == null) return listOf(errors.passwordTooWeak)

    val minLength = args[SecurityErrorArgs.PASSWORD_MIN_LENGTH]?.toString()?.toIntOrNull()
    val isTooShort = args[SecurityErrorArgs.PASSWORD_FAIL_TOO_SHORT] == true || args[SecurityErrorArgs.PASSWORD_FAIL_TOO_SHORT] == "true"
    val isNoLetter = args[SecurityErrorArgs.PASSWORD_FAIL_NO_LETTER] == true || args[SecurityErrorArgs.PASSWORD_FAIL_NO_LETTER] == "true"
    val isNoUpperCase = args[SecurityErrorArgs.PASSWORD_FAIL_NO_UPPERCASE] == true || args[SecurityErrorArgs.PASSWORD_FAIL_NO_UPPERCASE] == "true"
    val isNoLowerCase = args[SecurityErrorArgs.PASSWORD_FAIL_NO_LOWERCASE] == true || args[SecurityErrorArgs.PASSWORD_FAIL_NO_LOWERCASE] == "true"
    val isNoDigit = args[SecurityErrorArgs.PASSWORD_FAIL_NO_DIGIT] == true || args[SecurityErrorArgs.PASSWORD_FAIL_NO_DIGIT] == "true"
    val isNoSpecialChar = args[SecurityErrorArgs.PASSWORD_FAIL_NO_SPECIAL_CHAR] == true || args[SecurityErrorArgs.PASSWORD_FAIL_NO_SPECIAL_CHAR] == "true"
    val isTooCommon = args[SecurityErrorArgs.PASSWORD_FAIL_TOO_COMMON] == true || args[SecurityErrorArgs.PASSWORD_FAIL_TOO_COMMON] == "true"

    val reasons = mutableListOf<String>()

    if (isTooShort) {
        if (minLength != null) {
            reasons.add(errors.passwordTooShort(minLength))
        } else {
            reasons.add(errors.passwordTooShortWithoutArgs)
        }
    }
    if (isNoLetter) reasons.add(errors.passwordNoLetter)
    if (isNoUpperCase) reasons.add(errors.passwordNoUppercase)
    if (isNoLowerCase) reasons.add(errors.passwordNoLowercase)
    if (isNoDigit) reasons.add(errors.passwordNoDigit)
    if (isNoSpecialChar) reasons.add(errors.passwordNoSpecialChar)
    if (isTooCommon) reasons.add(errors.passwordTooCommon)

    return reasons.ifEmpty { listOf(errors.passwordTooWeak) }
}
