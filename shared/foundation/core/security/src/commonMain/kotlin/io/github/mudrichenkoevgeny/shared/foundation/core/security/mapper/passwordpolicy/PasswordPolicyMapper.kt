package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.PasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicy

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
fun PasswordPolicy.toPasswordPolicyPayload() = PasswordPolicyPayload(
    minLength = this.minLength,
    requireLetter = this.requireLetter,
    requireUpperCase = this.requireUpperCase,
    requireLowerCase = this.requireLowerCase,
    requireDigit = this.requireDigit,
    requireSpecialChar = this.requireSpecialChar,
    commonPasswords = this.commonPasswords
)