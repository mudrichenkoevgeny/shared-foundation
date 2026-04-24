package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totpsetup

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO containing the necessary data to set up a TOTP authenticator app.
 *
 * @property secretKey The Base32 encoded secret key to be manually entered by the user.
 * @property otpAuthUrl The `otpauth://` URI used to generate a QR code for easy scanning.
 * @property mfaToken A temporary challenge token representing this setup session.
 */
@Serializable
data class TotpSetupPayload(
    @SerialName(SecurityApiFields.TOTP_SECRET_KEY)
    val secretKey: String,

    @SerialName(SecurityApiFields.TOTP_OTP_AUTH_URL)
    val otpAuthUrl: String,

    @SerialName(SecurityApiFields.MFA_TOKEN)
    val mfaToken: String
)