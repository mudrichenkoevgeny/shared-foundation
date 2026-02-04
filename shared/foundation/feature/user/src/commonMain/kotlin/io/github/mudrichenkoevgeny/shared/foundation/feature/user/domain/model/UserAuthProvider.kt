package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the authentication provider used for user login or registration.
 */
@Serializable
enum class UserAuthProvider {
    /**
     * Authentication using email and password.
     */
    @SerialName(PROVIDER_EMAIL)
    EMAIL,

    /**
     * Authentication via SMS code or phone-based services.
     */
    @SerialName(PROVIDER_PHONE)
    PHONE,

    /**
     * Social authentication via Google OAuth.
     */
    @SerialName(PROVIDER_GOOGLE)
    GOOGLE,

    /**
     * Social authentication via Apple ID.
     */
    @SerialName(PROVIDER_APPLE)
    APPLE;

    /**
     * String representation of the [UserAuthProvider].
     */
    val serialName: String
        get() = when (this) {
            EMAIL -> PROVIDER_EMAIL
            PHONE -> PROVIDER_PHONE
            GOOGLE -> PROVIDER_GOOGLE
            APPLE -> PROVIDER_APPLE
        }

    companion object {
        const val PROVIDER_EMAIL = "email"
        const val PROVIDER_PHONE = "phone"
        const val PROVIDER_GOOGLE = "google"
        const val PROVIDER_APPLE = "apple"

        /**
         * Returns [UserAuthProvider] based on the provided string value, or null if the value is invalid.
         */
        fun fromValue(value: String): UserAuthProvider? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()
    }
}