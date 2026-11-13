package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val REASON_TOO_SHORT = "too_short"
private const val REASON_NO_LETTER = "no_letter"
private const val REASON_NO_UPPERCASE = "no_uppercase"
private const val REASON_NO_LOWERCASE = "no_lowercase"
private const val REASON_NO_DIGIT = "no_digit"
private const val REASON_NO_SPECIAL_CHAR = "no_special_char"
private const val REASON_TOO_COMMON = "too_common"

/**
 * Reasons for password validation failure based on security policy requirements.
 *
 * @property value String representation of the [PasswordPolicyFailReason].
 */
@Serializable
public enum class PasswordPolicyFailReason(
    public val value: String
) {
    /** Password length is below [ManagementPasswordPolicy.minLength]. */
    @SerialName(REASON_TOO_SHORT)
    TOO_SHORT(REASON_TOO_SHORT),

    /** [ManagementPasswordPolicy.requireLetter] is true but no alphabetic character is present. */
    @SerialName(REASON_NO_LETTER)
    NO_LETTER(REASON_NO_LETTER),

    /** [ManagementPasswordPolicy.requireUpperCase] is true but no uppercase letter is present. */
    @SerialName(REASON_NO_UPPERCASE)
    NO_UPPERCASE(REASON_NO_UPPERCASE),

    /** [ManagementPasswordPolicy.requireLowerCase] is true but no lowercase letter is present. */
    @SerialName(REASON_NO_LOWERCASE)
    NO_LOWERCASE(REASON_NO_LOWERCASE),

    /** [ManagementPasswordPolicy.requireDigit] is true but no digit is present. */
    @SerialName(REASON_NO_DIGIT)
    NO_DIGIT(REASON_NO_DIGIT),

    /** [ManagementPasswordPolicy.requireSpecialChar] is true but no non-alphanumeric character is present. */
    @SerialName(REASON_NO_SPECIAL_CHAR)
    NO_SPECIAL_CHAR(REASON_NO_SPECIAL_CHAR),

    /** Password equals an entry in [ManagementPasswordPolicy.commonPasswords]. */
    @SerialName(REASON_TOO_COMMON)
    TOO_COMMON(REASON_TOO_COMMON);

    public companion object {
        /**
         * Returns [PasswordPolicyFailReason] based on the provided string value, or null if the value is invalid.
         */
        public fun fromValueOrNull(value: String): PasswordPolicyFailReason? = entries.firstOrNull {
            it.value.equals(value, ignoreCase = true) ||
                it.name.equals(value, ignoreCase = true)
        }

        /**
         * Returns [PasswordPolicyFailReason] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [PasswordPolicyFailReason].
         */
        public fun fromValueOrThrow(value: String): PasswordPolicyFailReason =
            fromValueOrNull(value) ?: throw IllegalArgumentException(
                "Unknown value for ${PasswordPolicyFailReason::class.simpleName}: '$value'"
            )
    }
}
