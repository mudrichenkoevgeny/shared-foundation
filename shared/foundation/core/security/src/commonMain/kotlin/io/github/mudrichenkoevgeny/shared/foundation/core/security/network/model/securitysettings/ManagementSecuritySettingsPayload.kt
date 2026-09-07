package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.ManagementPasswordPolicyPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire DTO representing management security settings and policies.
 *
 * @property recentAuthenticationValiditySeconds Validity window for self-service actions, in seconds.
 * @property recentAuthenticationValiditySecondsForManagement Validity window for management actions, in seconds.
 * @property passwordPolicy Full password policy payload including common passwords; see [ManagementPasswordPolicyPayload].
 * @property otpConfirmation OTP configuration payload; see [OtpConfirmationPayload].
 * @property mfaTokenExpirationSeconds Lifetime of the MFA challenge token, in seconds.
 * @property maxRequestsPerPeriod Maximum number of requests per time unit.
 * @property rateLimitPeriodSeconds Time window in seconds for request rate limiting.
 */
@Serializable
data class ManagementSecuritySettingsPayload(
    @SerialName(SecurityApiFields.RECENT_AUTHENTICATION_VALIDITY_SECONDS)
    val recentAuthenticationValiditySeconds: Int,

    @SerialName(SecurityApiFields.RECENT_AUTHENTICATION_VALIDITY_SECONDS_FOR_MANAGEMENT)
    val recentAuthenticationValiditySecondsForManagement: Int,

    @SerialName(SecurityApiFields.MANAGEMENT_PASSWORD_POLICY)
    val passwordPolicy: ManagementPasswordPolicyPayload,

    @SerialName(SecurityApiFields.OTP_CONFIRMATION)
    val otpConfirmation: OtpConfirmationPayload,

    @SerialName(SecurityApiFields.EXPIRATION_SECONDS)
    val mfaTokenExpirationSeconds: Int,

    @SerialName(SecurityApiFields.MAX_REQUESTS_PER_PERIOD)
    val maxRequestsPerPeriod: Int,

    @SerialName(SecurityApiFields.RATE_LIMIT_PERIOD_SECONDS)
    val rateLimitPeriodSeconds: Int
)
