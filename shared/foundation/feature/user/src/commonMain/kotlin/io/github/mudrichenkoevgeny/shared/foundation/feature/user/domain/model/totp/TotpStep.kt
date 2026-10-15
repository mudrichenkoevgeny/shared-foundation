package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.totp

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val STEP_OVERVIEW = "overview"
private const val STEP_SETUP = "setup"
private const val STEP_VERIFY = "verify"

/**
 * Addressable steps for TOTP 2FA navigation screens.
 *
 * @property value String representation of the [TotpStep].
 */
@Serializable
enum class TotpStep(val value: String) {
    /** Main overview step ([OVERVIEW]). */
    @SerialName(STEP_OVERVIEW)
    OVERVIEW(STEP_OVERVIEW),

    /** TOTP secret key and QR code setup step ([SETUP]). */
    @SerialName(STEP_SETUP)
    SETUP(STEP_SETUP),

    /** TOTP verification step ([VERIFY]). */
    @SerialName(STEP_VERIFY)
    VERIFY(STEP_VERIFY);

    companion object {
        /** Default TOTP step ([OVERVIEW]). */
        val DEFAULT = OVERVIEW

        /**
         * Returns [TotpStep] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): TotpStep? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [TotpStep] for the given string value.
         *
         * @throws IllegalArgumentException if [value] does not match any [TotpStep].
         */
        fun fromValueOrThrow(value: String): TotpStep =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for TotpStep: '$value'")
    }
}