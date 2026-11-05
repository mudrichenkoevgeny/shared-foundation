package io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.nickname

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes

/**
 * Represents the outcome of a nickname validation check.
 */
public sealed class NicknameValidatorResult {
    /**
     * Indicates the nickname meets all validation requirements.
     */
    public object Success : NicknameValidatorResult()

    /**
     * Indicates validation failure, providing the specific error code.
     *
     * @property errorCode Error code string ([UserErrorCodes]).
     */
    public data class Fail(
        public val errorCode: String
    ) : NicknameValidatorResult()
}
