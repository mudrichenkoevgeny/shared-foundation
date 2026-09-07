package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.OpenPasswordPolicyPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire DTO representing open security settings.
 *
 * @property passwordPolicy Public password validation rules payload; see [OpenPasswordPolicyPayload].
 * @property otpConfirmation OTP configuration payload; see [OtpConfirmationPayload].
 */
@Serializable
data class OpenSecuritySettingsPayload(
    @SerialName(SecurityApiFields.OPEN_PASSWORD_POLICY)
    val passwordPolicy: OpenPasswordPolicyPayload,

    @SerialName(SecurityApiFields.OTP_CONFIRMATION)
    val otpConfirmation: OtpConfirmationPayload
)
