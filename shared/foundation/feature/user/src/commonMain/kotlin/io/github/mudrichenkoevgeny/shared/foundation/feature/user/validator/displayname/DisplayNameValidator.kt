package io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.displayname

/**
 * Component for validating user display names against length and character rules.
 */
interface DisplayNameValidator {
    /**
     * Validates display name string against length constraints and control character rules.
     *
     * @param displayName The display name string to validate, or `null`.
     * @param minLength Minimum permitted character length.
     * @param maxLength Maximum permitted character length.
     * @return [DisplayNameValidatorResult.Success] if valid, or [DisplayNameValidatorResult.Fail] with error code.
     */
    fun validate(
        displayName: String?,
        minLength: Int,
        maxLength: Int
    ): DisplayNameValidatorResult
}
