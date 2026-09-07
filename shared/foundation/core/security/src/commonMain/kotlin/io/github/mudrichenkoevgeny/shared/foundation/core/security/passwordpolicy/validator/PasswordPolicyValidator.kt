package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.validator

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.OpenPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyValidatorResult

/**
 * Component for validating passwords against security policies.
 */
interface PasswordPolicyValidator {
    /**
     * Validates the [password] against [ManagementPasswordPolicy] and returns a [PasswordPolicyValidatorResult].
     *
     * @param managementPasswordPolicy The management policy containing security rules for validation.
     * @param password The raw password string to be validated.
     * @return [PasswordPolicyValidatorResult.Success] if the password is valid,
     * or [PasswordPolicyValidatorResult.Fail] with a list of reasons.
     */
    fun validate(managementPasswordPolicy: ManagementPasswordPolicy, password: String): PasswordPolicyValidatorResult

    /**
     * Validates the [password] against [OpenPasswordPolicy] and returns a [PasswordPolicyValidatorResult].
     *
     * @param openPasswordPolicy The open policy containing public security rules for validation.
     * @param password The raw password string to be validated.
     * @return [PasswordPolicyValidatorResult.Success] if the password is valid,
     * or [PasswordPolicyValidatorResult.Fail] with a list of reasons.
     */
    fun validate(openPasswordPolicy: OpenPasswordPolicy, password: String): PasswordPolicyValidatorResult
}
