package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.OpenPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.OpenPasswordPolicyPayload

/**
 * Maps between [OpenPasswordPolicyPayload] DTO and domain [OpenPasswordPolicy].
 */

/**
 * Builds domain [OpenPasswordPolicy] from [OpenPasswordPolicyPayload] DTO.
 *
 * @return domain model aligned with [OpenPasswordPolicy] semantics.
 */
fun OpenPasswordPolicyPayload.toOpenPasswordPolicy(): OpenPasswordPolicy = OpenPasswordPolicy(
    minLength = minLength,
    requireLetter = requireLetter,
    requireUpperCase = requireUpperCase,
    requireLowerCase = requireLowerCase,
    requireDigit = requireDigit,
    requireSpecialChar = requireSpecialChar
)

/**
 * Builds network [OpenPasswordPolicyPayload] DTO from domain [OpenPasswordPolicy].
 *
 * @return payload model aligned with [OpenPasswordPolicyPayload] contract.
 */
fun OpenPasswordPolicy.toOpenPasswordPolicyPayload(): OpenPasswordPolicyPayload = OpenPasswordPolicyPayload(
    minLength = minLength,
    requireLetter = requireLetter,
    requireUpperCase = requireUpperCase,
    requireLowerCase = requireLowerCase,
    requireDigit = requireDigit,
    requireSpecialChar = requireSpecialChar
)

/**
 * Converts [OpenPasswordPolicy] to [ManagementPasswordPolicy] with empty common passwords set.
 *
 * @return management policy instance with equivalent complexity rules.
 */
fun OpenPasswordPolicy.toManagementPasswordPolicy(): ManagementPasswordPolicy = ManagementPasswordPolicy(
    minLength = minLength,
    requireLetter = requireLetter,
    requireUpperCase = requireUpperCase,
    requireLowerCase = requireLowerCase,
    requireDigit = requireDigit,
    requireSpecialChar = requireSpecialChar,
    commonPasswords = emptySet()
)
