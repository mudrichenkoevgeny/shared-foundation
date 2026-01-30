package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.model

/**
 * Configuration of rules used to validate password strength and security compliance.
 *
 * @property minLength Minimum number of characters required.
 * @property requireLetter If true, password must contain at least one alphabetic character.
 * @property requireUpperCase If true, password must contain at least one uppercase letter.
 * @property requireLowerCase If true, password must contain at least one lowercase letter.
 * @property requireDigit If true, password must contain at least one numerical digit.
 * @property requireSpecialChar If true, password must contain at least one special character (e.g. {!, @, #}).
 * @property commonPasswords A set of forbidden passwords that are considered too easy to guess.
 */
data class PasswordPolicy(
    val minLength: Int = DEFAULT_MIN_LENGTH,
    val requireLetter: Boolean = true,
    val requireUpperCase: Boolean = false,
    val requireLowerCase: Boolean = false,
    val requireDigit: Boolean = false,
    val requireSpecialChar: Boolean = false,
    val commonPasswords: Set<String> = DEFAULT_COMMON_PASSWORDS
) {
    companion object {
        const val DEFAULT_MIN_LENGTH = 8

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