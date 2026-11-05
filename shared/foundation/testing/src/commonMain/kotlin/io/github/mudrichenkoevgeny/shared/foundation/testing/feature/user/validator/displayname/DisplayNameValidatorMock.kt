package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.validator.displayname

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.displayname.DisplayNameValidator
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.validator.displayname.DisplayNameValidatorResult

/**
* Configurable mock implementation of [DisplayNameValidator] for testing.
*
* @property errorCode Error code to return when validation fails, or `null` for success.
*/
public class DisplayNameValidatorMock(
    public var errorCode: String? = null
) : DisplayNameValidator {
    public override fun validate(
        displayName: String?,
        minLength: Int,
        maxLength: Int
    ): DisplayNameValidatorResult {
        val error = errorCode
        return if (error == null) {
            DisplayNameValidatorResult.Success
        } else {
            DisplayNameValidatorResult.Fail(error)
        }
    }
}
