package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider

/**
 * Represents the authentication provider used for user login or registration.
 */
enum class UserAuthProvider {
    /**
     * Authentication using email and password.
     */
    EMAIL,

    /**
     * Authentication via SMS code or phone-based services.
     */
    PHONE,

    /**
     * Social authentication via Google OAuth.
     */
    GOOGLE,

    /**
     * Social authentication via Apple ID.
     */
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
        private const val PROVIDER_EMAIL = "email"
        private const val PROVIDER_PHONE = "phone"
        private const val PROVIDER_GOOGLE = "google"
        private const val PROVIDER_APPLE = "apple"

        /**
         * Returns [UserAuthProvider] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): UserAuthProvider? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [UserAuthProvider] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [UserAuthProvider].
         */
        fun fromValueOrThrow(value: String): UserAuthProvider = valueOf(value.uppercase())
    }
}
