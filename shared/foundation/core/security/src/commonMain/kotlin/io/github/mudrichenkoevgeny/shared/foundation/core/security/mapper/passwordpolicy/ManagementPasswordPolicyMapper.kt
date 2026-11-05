package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.ManagementPasswordPolicyPayload

/**
 * Builds domain [ManagementPasswordPolicy] from [ManagementPasswordPolicyPayload] DTO.
 *
 * @return domain model aligned with [ManagementPasswordPolicy] semantics.
 */
public fun ManagementPasswordPolicyPayload.toManagementPasswordPolicy(): ManagementPasswordPolicy =
    ManagementPasswordPolicy(
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
public fun ManagementPasswordPolicy.toManagementPasswordPolicyPayload(): ManagementPasswordPolicyPayload =
    ManagementPasswordPolicyPayload(
        minLength = minLength,
        requireLetter = requireLetter,
        requireUpperCase = requireUpperCase,
        requireLowerCase = requireLowerCase,
        requireDigit = requireDigit,
        requireSpecialChar = requireSpecialChar,
        commonPasswords = commonPasswords
    )
