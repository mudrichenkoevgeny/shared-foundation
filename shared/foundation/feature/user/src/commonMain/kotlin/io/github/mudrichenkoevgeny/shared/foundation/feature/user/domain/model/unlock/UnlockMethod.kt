package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.unlock

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val METHOD_PHONE = "phone"
private const val METHOD_EMAIL = "email"

/**
 * Addressable unlock methods for account unlock navigation screens.
 *
 * @property value String representation of the [UnlockMethod].
 */
@Serializable
enum class UnlockMethod(val value: String) {
    /** Unlock via SMS OTP ([PHONE]). */
    @SerialName(METHOD_PHONE)
    PHONE(METHOD_PHONE),

    /** Unlock via Email OTP ([EMAIL]). */
    @SerialName(METHOD_EMAIL)
    EMAIL(METHOD_EMAIL);

    companion object {
        /** Query parameter key for navigation URL maps. */
        const val PARAM_NAME = "unlock_method"

        /**
         * Returns [UnlockMethod] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): UnlockMethod? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [UnlockMethod] for the given string value.
         *
         * @throws IllegalArgumentException if [value] does not match any [UnlockMethod].
         */
        fun fromValueOrThrow(value: String): UnlockMethod =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for UnlockMethod: '$value'")
    }
}