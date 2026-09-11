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
    val minLength: Int,
    val requireLetter: Boolean,
    val requireUpperCase: Boolean,
    val requireLowerCase: Boolean,
    val requireDigit: Boolean,
    val requireSpecialChar: Boolean
)
