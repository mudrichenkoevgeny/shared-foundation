package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract

/**
 * Security settings specific naming conventions for API request parameters and response fields.
 */
object SecurityApiFields {

    /**
     * JSON field name for how long, in whole minutes, a recent step-up or re-authentication remains valid
     * for sensitive operations.
     */
    const val RECENT_AUTHENTICATION_VALIDITY_IN_MINUTES = "recent_authentication_validity_in_minutes"

    /** JSON field name for the nested password policy object. */
    const val PASSWORD_POLICY = "password_policy"

    /** JSON field name for minimum password length. */
    const val MIN_LENGTH = "min_length"

    /** JSON field name for the “at least one letter” requirement flag. */
    const val REQUIRE_LETTER = "require_letter"

    /** JSON field name for the “at least one uppercase letter” requirement flag. */
    const val REQUIRE_UPPER_CASE = "require_upper_case"

    /** JSON field name for the “at least one lowercase letter” requirement flag. */
    const val REQUIRE_LOWER_CASE = "require_lower_case"

    /** JSON field name for the “at least one digit” requirement flag. */
    const val REQUIRE_DIGIT = "require_digit"

    /** JSON field name for the “at least one special character” requirement flag. */
    const val REQUIRE_SPECIAL_CHAR = "require_special_char"

    /** JSON field name for the list (encoded as JSON array) of forbidden common passwords. */
    const val COMMON_PASSWORDS = "common_passwords"
}
