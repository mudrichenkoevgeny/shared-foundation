package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.iprestriction.IpRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy

/**
 * Management security configuration containing full policies and administrative parameters.
 *
 * @property recentAuthenticationValiditySecondsForOpenUser How long a recent re-authentication remains valid for open user actions, in seconds.
 * @property recentAuthenticationValiditySecondsForManagementUser How long a recent re-authentication remains valid for management user actions, in seconds.
 * @property passwordPolicy Full rules for password strength and validation including [ManagementPasswordPolicy.commonPasswords].
 * @property otpConfirmation Configuration for One-Time Password generation and validation.
 * @property accountLockoutPolicy Policy defining account and identifier lockout rules for authentication brute-force protection.
 * @property accountLockoutCheckIntervalSeconds Background worker check interval in seconds for processing expired account lockouts.
 * @property openIpRestrictionPolicy IP blacklist and whitelist restriction policy for open API endpoints; see [IpRestrictionPolicy].
 * @property managementIpRestrictionPolicy IP blacklist and whitelist restriction policy for management API endpoints; see [IpRestrictionPolicy].
 * @property mfaTokenExpirationSeconds Lifetime of the temporary MFA challenge token, in seconds.
 * @property maxRequestsPerPeriod Maximum number of requests per time unit.
 * @property rateLimitPeriodSeconds Time window in seconds for request rate limiting.
 * @property refreshTokenRotationGracePeriodSeconds Grace period in seconds during refresh token rotation before triggering replay attack protection.
 */
data class ManagementSecuritySettings(
    val recentAuthenticationValiditySecondsForOpenUser: Int,
    val recentAuthenticationValiditySecondsForManagementUser: Int,
    val passwordPolicy: ManagementPasswordPolicy,
    val otpConfirmation: OtpConfirmation,
    val accountLockoutPolicy: AccountLockoutPolicy,
    val accountLockoutCheckIntervalSeconds: Int,
    val openIpRestrictionPolicy: IpRestrictionPolicy,
    val managementIpRestrictionPolicy: IpRestrictionPolicy,
    val mfaTokenExpirationSeconds: Int,
    val maxRequestsPerPeriod: Int,
    val rateLimitPeriodSeconds: Int,
    val refreshTokenRotationGracePeriodSeconds: Int
)
