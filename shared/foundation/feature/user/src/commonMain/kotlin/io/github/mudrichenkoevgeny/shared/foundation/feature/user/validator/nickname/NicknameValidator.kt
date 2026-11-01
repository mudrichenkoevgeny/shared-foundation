package io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.nickname

/**
 * Component for validating user nickname handles against length, character, and change permission rules.
 */
interface NicknameValidator {
    /**
     * Validates nickname string against length constraints, character pattern, and permission settings.
     *
     * @param nickname The nickname handle to validate, or `null`.
     * @param minLength Minimum permitted character length.
     * @param maxLength Maximum permitted character length.
     * @param isNicknameChangeAllowed Flag indicating whether nickname modification is permitted.
     * @param isNicknameChanging Whether this operation modifies an existing nickname.
     * @return [NicknameValidatorResult.Success] if valid, or [NicknameValidatorResult.Fail] with error code.
     */
    fun validate(
        nickname: String?,
        minLength: Int,
        maxLength: Int,
        isNicknameChangeAllowed: Boolean = true,
        isNicknameChanging: Boolean = false
    ): NicknameValidatorResult
}
