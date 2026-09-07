package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.validator

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.OpenPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicyValidatorResult
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy.toManagementPasswordPolicy

/**
 * Default implementation of [PasswordPolicyValidator] that uses [ManagementPasswordPolicy] for validation.
 */
class PasswordPolicyValidatorImpl : PasswordPolicyValidator {
    override fun validate(managementPasswordPolicy: ManagementPasswordPolicy, password: String): PasswordPolicyValidatorResult {
        val reasons = mutableListOf<PasswordPolicyFailReason>()

        with(managementPasswordPolicy) {
            if (password.length < minLength) {
                reasons.add(PasswordPolicyFailReason.TOO_SHORT)
            }
            if (requireLetter && !password.any { it.isLetter() }) {
                reasons.add(PasswordPolicyFailReason.NO_LETTER)
            }
            if (requireUpperCase && !password.any { it.isUpperCase() }) {
                reasons.add(PasswordPolicyFailReason.NO_UPPERCASE)
            }
            if (requireLowerCase && !password.any { it.isLowerCase() }) {
                reasons.add(PasswordPolicyFailReason.NO_LOWERCASE)
            }
            if (requireDigit && !password.any { it.isDigit() }) {
                reasons.add(PasswordPolicyFailReason.NO_DIGIT)
            }
            if (requireSpecialChar && !password.any { !it.isLetterOrDigit() }) {
                reasons.add(PasswordPolicyFailReason.NO_SPECIAL_CHAR)
            }
            if (commonPasswords.contains(password)) {
                reasons.add(PasswordPolicyFailReason.TOO_COMMON)
            }
        }

        return if (reasons.isEmpty()) {
            PasswordPolicyValidatorResult.Success
        } else {
            PasswordPolicyValidatorResult.Fail(reasons, managementPasswordPolicy)
        }
    }

    override fun validate(openPasswordPolicy: OpenPasswordPolicy, password: String): PasswordPolicyValidatorResult =
        validate(openPasswordPolicy.toManagementPasswordPolicy(), password)
}
