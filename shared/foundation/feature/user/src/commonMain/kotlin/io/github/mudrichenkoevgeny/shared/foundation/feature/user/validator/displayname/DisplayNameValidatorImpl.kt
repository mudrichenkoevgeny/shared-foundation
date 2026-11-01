package io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.displayname

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes

/**
 * Default implementation of [DisplayNameValidator].
 */
class DisplayNameValidatorImpl : DisplayNameValidator {

    override fun validate(
        displayName: String?,
        minLength: Int,
        maxLength: Int
    ): DisplayNameValidatorResult {
        if (displayName == null) return DisplayNameValidatorResult.Success

        if (displayName.length < minLength) {
            return DisplayNameValidatorResult.Fail(UserErrorCodes.DISPLAY_NAME_TOO_SHORT)
        }
        if (displayName.length > maxLength) {
            return DisplayNameValidatorResult.Fail(UserErrorCodes.DISPLAY_NAME_TOO_LONG)
        }

        if (displayName.any { it.isISOControl() }) {
            return DisplayNameValidatorResult.Fail(UserErrorCodes.INVALID_DISPLAY_NAME_CHARACTERS)
        }

        return DisplayNameValidatorResult.Success
    }
}
