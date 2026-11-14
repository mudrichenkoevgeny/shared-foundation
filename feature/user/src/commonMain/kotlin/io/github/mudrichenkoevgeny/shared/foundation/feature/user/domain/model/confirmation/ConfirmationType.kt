package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.confirmation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val CONFIRMATION_TYPE_REGISTRATION_EMAIL = "registration_email"
private const val CONFIRMATION_TYPE_ADD_EMAIL = "add_email"
private const val CONFIRMATION_TYPE_PASSWORD_RESET_EMAIL = "password_reset_email"
private const val CONFIRMATION_TYPE_LOGIN_PHONE = "login_phone"
private const val CONFIRMATION_TYPE_ADD_PHONE = "add_phone"
private const val CONFIRMATION_TYPE_UNLOCK_EMAIL = "unlock_email"
private const val CONFIRMATION_TYPE_UNLOCK_PHONE = "unlock_phone"

/**
 * Which confirmation flow a [ConfirmationKey] refers to (registration, password reset, identifier add, etc.).
 *
 * @property value String representation of the [ConfirmationType].
 */
@Serializable
public enum class ConfirmationType(
    public val value: String
) {
    /** Confirmation for a new user registration via email. */
    @SerialName(CONFIRMATION_TYPE_REGISTRATION_EMAIL)
    REGISTRATION_EMAIL(CONFIRMATION_TYPE_REGISTRATION_EMAIL),

    /** Confirmation for adding a new email address to an existing account. */
    @SerialName(CONFIRMATION_TYPE_ADD_EMAIL)
    ADD_EMAIL(CONFIRMATION_TYPE_ADD_EMAIL),

    /** Confirmation for a password reset request via email. */
    @SerialName(CONFIRMATION_TYPE_PASSWORD_RESET_EMAIL)
    PASSWORD_RESET_EMAIL(CONFIRMATION_TYPE_PASSWORD_RESET_EMAIL),

    /** Confirmation for a login attempt via phone (OTP). */
    @SerialName(CONFIRMATION_TYPE_LOGIN_PHONE)
    LOGIN_PHONE(CONFIRMATION_TYPE_LOGIN_PHONE),

    /** Confirmation for adding a new phone number to an existing account. */
    @SerialName(CONFIRMATION_TYPE_ADD_PHONE)
    ADD_PHONE(CONFIRMATION_TYPE_ADD_PHONE),

    /** Confirmation for unlocking an account via email. */
    @SerialName(CONFIRMATION_TYPE_UNLOCK_EMAIL)
    UNLOCK_EMAIL(CONFIRMATION_TYPE_UNLOCK_EMAIL),

    /** Confirmation for unlocking an account via phone. */
    @SerialName(CONFIRMATION_TYPE_UNLOCK_PHONE)
    UNLOCK_PHONE(CONFIRMATION_TYPE_UNLOCK_PHONE);

    public companion object {
        /**
         * Returns [ConfirmationType] based on the provided string value, or null if the value is invalid.
         *
         * @param value Case-insensitive enum name or a wire string.
         */
        public fun fromValueOrNull(value: String?): ConfirmationType? {
            if (value.isNullOrBlank()) return null
            return entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            }
        }

        /**
         * Returns [ConfirmationType] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [ConfirmationType].
         */
        public fun fromValueOrThrow(value: String): ConfirmationType =
            fromValueOrNull(value) ?: throw IllegalArgumentException(
                "Unknown value for ${ConfirmationType::class.simpleName}: '$value'"
            )
    }
}
