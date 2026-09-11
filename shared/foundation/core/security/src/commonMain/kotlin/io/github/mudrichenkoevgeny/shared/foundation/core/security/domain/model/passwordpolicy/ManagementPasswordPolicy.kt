package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy

/**
 * Management configuration of rules used to validate password strength and security compliance including common passwords list.
 *
 * @property minLength Minimum number of characters required.
 * @property requireLetter If true, password must contain at least one alphabetic character.
 * @property requireUpperCase If true, password must contain at least one uppercase letter.
 * @property requireLowerCase If true, password must contain at least one lowercase letter.
 * @property requireDigit If true, password must contain at least one numerical digit.
 * @property requireSpecialChar If true, password must contain at least one special character.
 * @property commonPasswords A set of forbidden passwords that are considered too easy to guess.
 */
data class ManagementPasswordPolicy(
    val minLength: Int,
    val requireLetter: Boolean,
    val requireUpperCase: Boolean,
    val requireLowerCase: Boolean,
    val requireDigit: Boolean,
    val requireSpecialChar: Boolean,
    val commonPasswords: Set<String>
) {
    companion object {
        /** Default [minLength] value for password policies. */
        const val DEFAULT_MIN_LENGTH = 8

        /** Default banned-password list. */
        val DEFAULT_COMMON_PASSWORDS = setOf(
            "12345678",
            "password",
            "123456789",
            "qwertyui",
            "abc12345",
            "11111111",
            "12312312",
            "password1",
            "iloveyou",
            "letmein1"
        )
    }
}
