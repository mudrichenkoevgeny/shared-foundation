package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.model

/**
 * Reasons for password validation failure based on security policy requirements.
 */
enum class PasswordPolicyFailReason {
    TOO_SHORT,
    NO_LETTER,
    NO_UPPERCASE,
    NO_LOWERCASE,
    NO_DIGIT,
    NO_SPECIAL_CHAR,
    TOO_COMMON
}