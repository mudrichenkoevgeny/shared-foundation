package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.ManagementPasswordPolicyPayload

/**
 * Maps between [ManagementPasswordPolicyPayload] DTO and domain [ManagementPasswordPolicy].
 */

/**
 * Builds domain [ManagementPasswordPolicy] from [ManagementPasswordPolicyPayload] DTO.
 *
 * @return domain model aligned with [ManagementPasswordPolicy] semantics.
 */
fun ManagementPasswordPolicyPayload.toManagementPasswordPolicy() = ManagementPasswordPolicy(
    minLength = minLength,
    requireLetter = requireLetter,
    requireUpperCase = requireUpperCase,
    requireLowerCase = requireLowerCase,
    requireDigit = requireDigit,
    requireSpecialChar = requireSpecialChar,
    commonPasswords = commonPasswords
)

/**
 * Builds network [ManagementPasswordPolicyPayload] DTO from domain [ManagementPasswordPolicy].
 *
 * @return payload model aligned with [ManagementPasswordPolicyPayload] contract.
 */
fun ManagementPasswordPolicy.toManagementPasswordPolicyPayload() = ManagementPasswordPolicyPayload(
    minLength = minLength,
    requireLetter = requireLetter,
    requireUpperCase = requireUpperCase,
    requireLowerCase = requireLowerCase,
    requireDigit = requireDigit,
    requireSpecialChar = requireSpecialChar,
    commonPasswords = commonPasswords
)
