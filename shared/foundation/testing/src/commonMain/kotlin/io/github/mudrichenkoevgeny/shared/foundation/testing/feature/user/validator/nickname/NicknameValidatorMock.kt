package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.validator.nickname

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.nickname.NicknameValidator
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.nickname.NicknameValidatorResult

/**
 * Configurable mock implementation of [NicknameValidator] for testing.
 *
 * @property errorCode Error code to return when validation fails, or `null` for success.
 */
public class NicknameValidatorMock(
    public var errorCode: String? = null
) : NicknameValidator {
    public override fun validate(
        nickname: String?,
        minLength: Int,
        maxLength: Int,
        isNicknameChangeAllowed: Boolean,
        isNicknameChanging: Boolean
    ): NicknameValidatorResult {
        val error = errorCode
        return if (error == null) {
            NicknameValidatorResult.Success
        } else {
            NicknameValidatorResult.Fail(error)
        }
    }
}
