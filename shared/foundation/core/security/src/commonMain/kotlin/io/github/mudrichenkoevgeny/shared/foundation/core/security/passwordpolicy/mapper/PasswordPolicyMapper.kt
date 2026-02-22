package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.mapper

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.response.settings.PasswordPolicyResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model.PasswordPolicy

/**
 * Maps [PasswordPolicyResponse] DTO to domain model [PasswordPolicy].
 */
fun PasswordPolicyResponse.toPasswordPolicy() = PasswordPolicy(
    minLength = this.minLength,
    requireLetter = this.requireLetter,
    requireUpperCase = this.requireUpperCase,
    requireLowerCase = this.requireLowerCase,
    requireDigit = this.requireDigit,
    requireSpecialChar = this.requireSpecialChar,
    commonPasswords = this.commonPasswords
)

/**
 * Maps domain model [PasswordPolicy] to [PasswordPolicyResponse] DTO.
 */
fun PasswordPolicy.toPasswordPolicyResponse() = PasswordPolicyResponse(
    minLength = this.minLength,
    requireLetter = this.requireLetter,
    requireUpperCase = this.requireUpperCase,
    requireLowerCase = this.requireLowerCase,
    requireDigit = this.requireDigit,
    requireSpecialChar = this.requireSpecialChar,
    commonPasswords = this.commonPasswords
)