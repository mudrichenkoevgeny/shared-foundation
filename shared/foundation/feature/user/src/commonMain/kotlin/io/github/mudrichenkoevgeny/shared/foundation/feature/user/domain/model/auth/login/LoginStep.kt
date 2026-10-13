package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val STEP_WELCOME = "welcome"
private const val STEP_EMAIL = "email"
private const val STEP_PHONE = "phone"

/**
 * Addressable login steps for authentication navigation screens.
 *
 * @property value String representation of the [LoginStep].
 */
@Serializable
enum class LoginStep(val value: String) {
    /** Entry welcome step ([WELCOME]). */
    @SerialName(STEP_WELCOME)
    WELCOME(STEP_WELCOME),

    /** Email authentication step ([EMAIL]). */
    @SerialName(STEP_EMAIL)
    EMAIL(STEP_EMAIL),

    /** Phone authentication step ([PHONE]). */
    @SerialName(STEP_PHONE)
    PHONE(STEP_PHONE);

    companion object {
        /** Query parameter key for navigation URL maps. */
        const val PARAM_NAME = "login_step"

        /** Default entry login step ([WELCOME]). */
        val DEFAULT = WELCOME

        /**
         * Returns [LoginStep] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): LoginStep? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [LoginStep] for the given string value.
         *
         * @throws IllegalArgumentException if [value] does not match any [LoginStep].
         */
        fun fromValueOrThrow(value: String): LoginStep =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for LoginStep: '$value'")
    }
}