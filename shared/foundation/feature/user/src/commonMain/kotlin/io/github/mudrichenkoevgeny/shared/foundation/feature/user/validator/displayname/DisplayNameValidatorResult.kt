package io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.displayname

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes

/**
 * Represents the outcome of a display name validation check.
 */
sealed class DisplayNameValidatorResult {
    /**
     * Indicates the display name meets all validation requirements.
     */
    object Success : DisplayNameValidatorResult()

    /**
     * Indicates validation failure, providing the specific error code.
     *
     * @property errorCode Error code string ([UserErrorCodes]).
     */
    data class Fail(
        val errorCode: String
    ) : DisplayNameValidatorResult()
}
