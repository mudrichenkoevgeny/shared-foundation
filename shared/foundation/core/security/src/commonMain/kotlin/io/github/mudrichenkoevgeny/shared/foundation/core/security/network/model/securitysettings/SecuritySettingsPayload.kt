package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.PasswordPolicyPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Security settings as serialized in API and compatible storage (snake_case field names).
 *
 * @property recentAuthenticationValiditySeconds Validity window for self-service actions, in seconds.
 * @property recentAuthenticationValiditySecondsForManagement Validity window for management actions, in seconds.
 * @property passwordPolicy Rules for password strength and validation.
 * @property otpConfirmation Serialized OTP configuration settings.
 * @property mfaTokenExpirationSeconds Lifetime of the MFA challenge token, in seconds.
 */
@Serializable
data class SecuritySettingsPayload(
    @SerialName(SecurityApiFields.RECENT_AUTHENTICATION_VALIDITY_SECONDS)
    val recentAuthenticationValiditySeconds: Int,

    @SerialName(SecurityApiFields.RECENT_AUTHENTICATION_VALIDITY_SECONDS_FOR_MANAGEMENT)
    val recentAuthenticationValiditySecondsForManagement: Int,

    @SerialName(SecurityApiFields.PASSWORD_POLICY)
    val passwordPolicy: PasswordPolicyPayload,

    @SerialName(SecurityApiFields.OTP_CONFIRMATION)
    val otpConfirmation: OtpConfirmationPayload,

    @SerialName(SecurityApiFields.EXPIRATION_SECONDS)
    val mfaTokenExpirationSeconds: Int
)