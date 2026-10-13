package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val PROVIDER_EMAIL = "email"
private const val PROVIDER_PHONE = "phone"
private const val PROVIDER_GOOGLE = "google"
private const val PROVIDER_APPLE = "apple"

/**
 * Represents the authentication provider used for user login or registration.
 *
 * @property value String representation of the [UserAuthProvider].
 */
@Serializable
enum class UserAuthProvider(val value: String) {
    /**
     * Authentication using email and password.
     */
    @SerialName(PROVIDER_EMAIL)
    EMAIL(PROVIDER_EMAIL),

    /**
     * Authentication via SMS code or phone-based services.
     */
    @SerialName(PROVIDER_PHONE)
    PHONE(PROVIDER_PHONE),

    /**
     * Social authentication via Google OAuth.
     */
    @SerialName(PROVIDER_GOOGLE)
    GOOGLE(PROVIDER_GOOGLE),

    /**
     * Social authentication via Apple ID.
     */
    @SerialName(PROVIDER_APPLE)
    APPLE(PROVIDER_APPLE);

    companion object {
        /**
         * Returns [UserAuthProvider] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): UserAuthProvider? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [UserAuthProvider] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [UserAuthProvider].
         */
        fun fromValueOrThrow(value: String): UserAuthProvider =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for UserAuthProvider: '$value'")
    }
}