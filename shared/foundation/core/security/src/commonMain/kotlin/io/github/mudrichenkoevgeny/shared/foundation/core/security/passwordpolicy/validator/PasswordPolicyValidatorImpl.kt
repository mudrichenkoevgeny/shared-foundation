package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.validator

import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model.PasswordPolicyFailReason
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model.PasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model.PasswordPolicyValidatorResult

/**
 * Default implementation of [PasswordPolicyValidator] that uses [PasswordPolicy] for validation.
 */
class PasswordPolicyValidatorImpl: PasswordPolicyValidator {
    override fun validate(passwordPolicy: PasswordPolicy, password: String): PasswordPolicyValidatorResult {
        val reasons = mutableListOf<PasswordPolicyFailReason>()

        with(passwordPolicy) {
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
            PasswordPolicyValidatorResult.Fail(reasons, passwordPolicy)
        }
    }
}