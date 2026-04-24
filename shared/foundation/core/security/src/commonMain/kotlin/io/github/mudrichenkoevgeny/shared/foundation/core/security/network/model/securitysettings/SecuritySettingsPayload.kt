package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.PasswordPolicyPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Security settings as serialized in API and compatible storage (snake_case field names).
 *
 * @property recentAuthenticationValidityInMinutes How long, in whole minutes, a recent
 *   step-up or re-authentication remains valid for sensitive operations.
 * @property passwordPolicy Rules for password strength and validation.
 * @property otpConfirmation Serialized OTP configuration settings.
 */
@Serializable
data class SecuritySettingsPayload(
    @SerialName(SecurityApiFields.RECENT_AUTHENTICATION_VALIDITY_IN_MINUTES)
    val recentAuthenticationValidityInMinutes: Long,

    @SerialName(SecurityApiFields.PASSWORD_POLICY)
    val passwordPolicy: PasswordPolicyPayload,

    @SerialName(SecurityApiFields.OTP_CONFIRMATION)
    val otpConfirmation: OtpConfirmationPayload
)