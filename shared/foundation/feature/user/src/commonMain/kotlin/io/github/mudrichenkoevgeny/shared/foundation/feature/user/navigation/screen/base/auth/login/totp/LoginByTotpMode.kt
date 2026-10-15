package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val MODE_TOTP = "totp"
private const val MODE_RECOVERY_CODE = "recovery_code"

/**
 * Verification input mode for TOTP authentication screens ([TOTP] or [RECOVERY_CODE]).
 *
 * @property value String wire representation of the [LoginByTotpMode].
 */
@Serializable
enum class LoginByTotpMode(val value: String) {
    /** Standard authenticator application TOTP code entry mode ([MODE_TOTP]). */
    @SerialName(MODE_TOTP)
    TOTP(MODE_TOTP),

    /** Backup recovery code entry mode ([MODE_RECOVERY_CODE]). */
    @SerialName(MODE_RECOVERY_CODE)
    RECOVERY_CODE(MODE_RECOVERY_CODE);

    companion object {
        /** Default TOTP verification mode ([TOTP]). */
        val DEFAULT = TOTP

        /**
         * Resolves [LoginByTotpMode] matching the given [value], or `null` if no match is found.
         *
         * Compares against both wire [value] and enum [name] case-insensitively.
         */
        fun fromValueOrNull(value: String): LoginByTotpMode? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Resolves [LoginByTotpMode] for the given [value].
         *
         * @throws IllegalArgumentException if [value] does not match any mode.
         */
        fun fromValueOrThrow(value: String): LoginByTotpMode = fromValueOrNull(value)
            ?: throw IllegalArgumentException("Unknown value for LoginByTotpMode: '$value'")
    }
}