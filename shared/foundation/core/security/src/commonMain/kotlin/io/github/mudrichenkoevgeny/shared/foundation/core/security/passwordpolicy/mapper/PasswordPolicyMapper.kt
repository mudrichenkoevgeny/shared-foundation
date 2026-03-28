package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.mapper

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.PasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model.PasswordPolicy

/**
 * Maps [PasswordPolicyPayload] DTO to domain model [PasswordPolicy].
 */
fun PasswordPolicyPayload.toPasswordPolicy() = PasswordPolicy(
    minLength = this.minLength,
    requireLetter = this.requireLetter,
    requireUpperCase = this.requireUpperCase,
    requireLowerCase = this.requireLowerCase,
    requireDigit = this.requireDigit,
    requireSpecialChar = this.requireSpecialChar,
    commonPasswords = this.commonPasswords
)

/**
 * Maps domain model [PasswordPolicy] to [PasswordPolicyPayload] DTO.
 */
fun PasswordPolicy.toPasswordPolicyResponse() = PasswordPolicyPayload(
    minLength = this.minLength,
    requireLetter = this.requireLetter,
    requireUpperCase = this.requireUpperCase,
    requireLowerCase = this.requireLowerCase,
    requireDigit = this.requireDigit,
    requireSpecialChar = this.requireSpecialChar,
    commonPasswords = this.commonPasswords
)