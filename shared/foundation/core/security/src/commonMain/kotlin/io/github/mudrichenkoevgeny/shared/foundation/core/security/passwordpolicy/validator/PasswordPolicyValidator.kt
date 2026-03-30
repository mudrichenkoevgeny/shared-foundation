package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.validator

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyValidatorResult

/**
 * Component for validating passwords against security policies.
 */
interface PasswordPolicyValidator {
    /**
     * Validates the [password] and returns a [PasswordPolicyValidatorResult].
     *
     * @param passwordPolicy The policy containing security rules for validation.
     * @param password The raw password string to be validated.
     * @return [PasswordPolicyValidatorResult.Success] if the password is valid,
     * or [PasswordPolicyValidatorResult.Fail] with a list of reasons.
     */
    fun validate(passwordPolicy: PasswordPolicy, password: String): PasswordPolicyValidatorResult
}