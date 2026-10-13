package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val MODE_TOTP = "totp"
private const val MODE_RECOVERY_CODE = "recovery_code"

/**
 * Verification input mode for TOTP authentication screens ([TOTP] or [RECOVERY_CODE]).
 *
 * @property value String representation of the [LoginByTotpMode].
 */
@Serializable
enum class LoginByTotpMode(val value: String) {
    /** Standard authenticator application TOTP code entry mode ([TOTP]). */
    @SerialName(MODE_TOTP)
    TOTP(MODE_TOTP),

    /** Backup 8-character recovery code entry mode ([RECOVERY_CODE]). */
    @SerialName(MODE_RECOVERY_CODE)
    RECOVERY_CODE(MODE_RECOVERY_CODE);

    companion object {
        /** Query parameter key for navigation URL maps. */
        const val PARAM_NAME = "login_by_totp_mode"

        /** Default TOTP verification mode ([TOTP]). */
        val DEFAULT = TOTP

        /**
         * Returns [LoginByTotpMode] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): LoginByTotpMode? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [LoginByTotpMode] for the given string value.
         *
         * @throws IllegalArgumentException if [value] does not match any [LoginByTotpMode].
         */
        fun fromValueOrThrow(value: String): LoginByTotpMode =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for LoginByTotpMode: '$value'")
    }
}