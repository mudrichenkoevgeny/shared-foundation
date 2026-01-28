package io.github.mudrichenkoevgeny.shared.foundation.core.security.passwordpolicychecker.enums

enum class PasswordPolicyFailReason {
    TOO_SHORT,
    NO_LETTER,
    NO_UPPERCASE,
    NO_LOWERCASE,
    NO_DIGIT,
    NO_SPECIAL_CHAR,
    TOO_COMMON
}