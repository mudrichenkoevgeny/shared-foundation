package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract.SecurityApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.accountlockout.AccountLockoutPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.iprestriction.IpRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.ManagementPasswordPolicyPayload
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Wire DTO representing management security settings and policies.
 *
 * @property recentAuthenticationValiditySecondsForOpenUser Validity window for open user actions, in seconds.
 * @property recentAuthenticationValiditySecondsForManagementUser Validity window for management user actions, in seconds.
 * @property passwordPolicy Full password policy payload including common passwords; see [ManagementPasswordPolicyPayload].
 * @property otpConfirmation OTP configuration payload; see [OtpConfirmationPayload].
 * @property accountLockoutPolicy Account lockout policy payload; see [AccountLockoutPolicyPayload].
 * @property accountLockoutCheckIntervalSeconds Background worker check interval in seconds for processing expired account lockouts.
 * @property openIpRestrictionPolicy IP blacklist and whitelist restriction policy payload for open API endpoints; see [IpRestrictionPolicyPayload].
 * @property managementIpRestrictionPolicy IP blacklist and whitelist restriction policy payload for management API endpoints; see [IpRestrictionPolicyPayload].
 * @property mfaTokenExpirationSeconds Lifetime of the MFA challenge token, in seconds.
 * @property maxRequestsPerPeriod Maximum number of requests per time unit.
 * @property rateLimitPeriodSeconds Time window in seconds for request rate limiting.
 * @property refreshTokenRotationGracePeriodSeconds Grace period in seconds during refresh token rotation before triggering replay attack protection.
 */
@Serializable
data class ManagementSecuritySettingsPayload(
    @SerialName(SecurityApiFields.RECENT_AUTHENTICATION_VALIDITY_SECONDS_FOR_OPEN_USER)
    val recentAuthenticationValiditySecondsForOpenUser: Int,

    @SerialName(SecurityApiFields.RECENT_AUTHENTICATION_VALIDITY_SECONDS_FOR_MANAGEMENT_USER)
    val recentAuthenticationValiditySecondsForManagementUser: Int,

    @SerialName(SecurityApiFields.MANAGEMENT_PASSWORD_POLICY)
    val passwordPolicy: ManagementPasswordPolicyPayload,

    @SerialName(SecurityApiFields.OTP_CONFIRMATION)
    val otpConfirmation: OtpConfirmationPayload,

    @SerialName(SecurityApiFields.ACCOUNT_LOCKOUT_POLICY)
    val accountLockoutPolicy: AccountLockoutPolicyPayload,

    @SerialName(SecurityApiFields.ACCOUNT_LOCKOUT_CHECK_INTERVAL_SECONDS)
    val accountLockoutCheckIntervalSeconds: Int,

    @SerialName(SecurityApiFields.OPEN_IP_RESTRICTION_POLICY)
    val openIpRestrictionPolicy: IpRestrictionPolicyPayload,

    @SerialName(SecurityApiFields.MANAGEMENT_IP_RESTRICTION_POLICY)
    val managementIpRestrictionPolicy: IpRestrictionPolicyPayload,

    @SerialName(SecurityApiFields.EXPIRATION_SECONDS)
    val mfaTokenExpirationSeconds: Int,

    @SerialName(SecurityApiFields.MAX_REQUESTS_PER_PERIOD)
    val maxRequestsPerPeriod: Int,

    @SerialName(SecurityApiFields.RATE_LIMIT_PERIOD_SECONDS)
    val rateLimitPeriodSeconds: Int,

    @SerialName(SecurityApiFields.REFRESH_TOKEN_ROTATION_GRACE_PERIOD_SECONDS)
    val refreshTokenRotationGracePeriodSeconds: Int
)
