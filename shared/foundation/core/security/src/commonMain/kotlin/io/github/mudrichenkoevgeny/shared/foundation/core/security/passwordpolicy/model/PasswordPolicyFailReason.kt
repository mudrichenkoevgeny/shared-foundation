package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicy.model

/**
 * Reasons for password validation failure based on security policy requirements.
 */
enum class PasswordPolicyFailReason {
    /** Password length is below [PasswordPolicy.minLength]. */
    TOO_SHORT,

    /** [PasswordPolicy.requireLetter] is true but no alphabetic character is present. */
    NO_LETTER,

    /** [PasswordPolicy.requireUpperCase] is true but no uppercase letter is present. */
    NO_UPPERCASE,

    /** [PasswordPolicy.requireLowerCase] is true but no lowercase letter is present. */
    NO_LOWERCASE,

    /** [PasswordPolicy.requireDigit] is true but no digit is present. */
    NO_DIGIT,

    /** [PasswordPolicy.requireSpecialChar] is true but no non-alphanumeric character is present. */
    NO_SPECIAL_CHAR,

    /** Password equals an entry in [PasswordPolicy.commonPasswords]. */
    TOO_COMMON
}
