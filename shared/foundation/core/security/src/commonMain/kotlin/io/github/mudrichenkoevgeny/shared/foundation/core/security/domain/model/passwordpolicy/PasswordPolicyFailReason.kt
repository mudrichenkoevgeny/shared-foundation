package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy

/**
 * Reasons for password validation failure based on security policy requirements.
 */
enum class PasswordPolicyFailReason {
    /** Password length is below [ManagementPasswordPolicy.minLength]. */
    TOO_SHORT,

    /** [ManagementPasswordPolicy.requireLetter] is true but no alphabetic character is present. */
    NO_LETTER,

    /** [ManagementPasswordPolicy.requireUpperCase] is true but no uppercase letter is present. */
    NO_UPPERCASE,

    /** [ManagementPasswordPolicy.requireLowerCase] is true but no lowercase letter is present. */
    NO_LOWERCASE,

    /** [ManagementPasswordPolicy.requireDigit] is true but no digit is present. */
    NO_DIGIT,

    /** [ManagementPasswordPolicy.requireSpecialChar] is true but no non-alphanumeric character is present. */
    NO_SPECIAL_CHAR,

    /** Password equals an entry in [ManagementPasswordPolicy.commonPasswords]. */
    TOO_COMMON
}
