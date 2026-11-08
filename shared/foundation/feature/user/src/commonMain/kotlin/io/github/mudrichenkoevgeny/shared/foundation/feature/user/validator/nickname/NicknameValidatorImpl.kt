package io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.nickname

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.error.naming.UserErrorCodes

/**
 * Default implementation of [NicknameValidator].
 */
public class NicknameValidatorImpl : NicknameValidator {
    override fun validate(
        nickname: String?,
        minLength: Int,
        maxLength: Int,
        isNicknameChangeAllowed: Boolean,
        isNicknameChanging: Boolean
    ): NicknameValidatorResult {
        if (nickname == null) return NicknameValidatorResult.Success

        if (isNicknameChanging && !isNicknameChangeAllowed) {
            return NicknameValidatorResult.Fail(UserErrorCodes.NICKNAME_CHANGE_DISABLED)
        }

        if (nickname.length < minLength) {
            return NicknameValidatorResult.Fail(UserErrorCodes.NICKNAME_TOO_SHORT)
        }
        if (nickname.length > maxLength) {
            return NicknameValidatorResult.Fail(UserErrorCodes.NICKNAME_TOO_LONG)
        }

        if (!NICKNAME_REGEX.matches(nickname)) {
            return NicknameValidatorResult.Fail(UserErrorCodes.INVALID_NICKNAME_CHARACTERS)
        }

        return NicknameValidatorResult.Success
    }

    private companion object {
        val NICKNAME_REGEX = Regex("^[a-zA-Z0-9](?:[._]?[a-zA-Z0-9]+)*$")
    }
}
