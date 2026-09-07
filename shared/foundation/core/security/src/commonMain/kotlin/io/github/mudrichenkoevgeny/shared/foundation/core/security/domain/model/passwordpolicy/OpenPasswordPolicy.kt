package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy

/**
 * Public password validation rules excluding sensitive common passwords lists.
 *
 * @property minLength Minimum number of characters required.
 * @property requireLetter If true, password must contain at least one alphabetic character.
 * @property requireUpperCase If true, password must contain at least one uppercase letter.
 * @property requireLowerCase If true, password must contain at least one lowercase letter.
 * @property requireDigit If true, password must contain at least one numerical digit.
 * @property requireSpecialChar If true, password must contain at least one special character.
 */
data class OpenPasswordPolicy(
    val minLength: Int = ManagementPasswordPolicy.DEFAULT_MIN_LENGTH,
    val requireLetter: Boolean = true,
    val requireUpperCase: Boolean = false,
    val requireLowerCase: Boolean = false,
    val requireDigit: Boolean = false,
    val requireSpecialChar: Boolean = false
)
