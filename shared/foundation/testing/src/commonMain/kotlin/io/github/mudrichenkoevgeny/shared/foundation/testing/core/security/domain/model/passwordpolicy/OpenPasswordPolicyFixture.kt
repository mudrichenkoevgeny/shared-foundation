package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.passwordpolicy

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.OpenPasswordPolicy

/**
 * Creates a test [OpenPasswordPolicy] domain instance.
 *
 * @param minLength Minimum required password length.
 * @param requireLetter Flag requiring at least one letter.
 * @param requireUpperCase Flag requiring at least one uppercase letter.
 * @param requireLowerCase Flag requiring at least one lowercase letter.
 * @param requireDigit Flag requiring at least one digit.
 * @param requireSpecialChar Flag requiring at least one special character.
 */
fun createTestOpenPasswordPolicy(
    minLength: Int = ManagementPasswordPolicy.DEFAULT_MIN_LENGTH,
    requireLetter: Boolean = true,
    requireUpperCase: Boolean = true,
    requireLowerCase: Boolean = true,
    requireDigit: Boolean = true,
    requireSpecialChar: Boolean = false
) = OpenPasswordPolicy(
    minLength = minLength,
    requireLetter = requireLetter,
    requireUpperCase = requireUpperCase,
    requireLowerCase = requireLowerCase,
    requireDigit = requireDigit,
    requireSpecialChar = requireSpecialChar
)
