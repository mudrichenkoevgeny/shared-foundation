package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.accountlockout.AccountLockoutPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.iprestriction.IpRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.ManagementPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.accountlockout.createTestAccountLockoutPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.iprestriction.createTestIpRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.otpconfirmation.createTestOtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.passwordpolicy.createTestManagementPasswordPolicyPayload

/**
 * Creates a test [ManagementSecuritySettingsPayload] wire object instance.
 *
 * @param recentAuthenticationValiditySecondsForOpenUser Session re-auth validity for open users.
 * @param recentAuthenticationValiditySecondsForManagementUser Session re-auth validity for management users.
 * @param passwordPolicy Password strength policy payload.
 * @param otpConfirmation OTP code generation and retry rules payload.
 * @param accountLockoutPolicy Account lockout rules payload for failed logins.
 * @param accountLockoutCheckIntervalSeconds Interval for account lockout cleanup.
 * @param openIpRestrictionPolicy IP restriction policy payload for open APIs.
 * @param managementIpRestrictionPolicy IP restriction policy payload for management APIs.
 * @param mfaTokenExpirationSeconds Expiration time in seconds for MFA setup tokens.
 * @param maxRequestsPerPeriod Maximum rate-limit requests per period.
 * @param rateLimitPeriodSeconds Duration in seconds of rate limit evaluation period.
 * @param refreshTokenRotationGracePeriodSeconds Grace period in seconds for refresh token rotation.
 */
fun createTestManagementSecuritySettingsPayload(
    recentAuthenticationValiditySecondsForOpenUser: Int = 300,
    recentAuthenticationValiditySecondsForManagementUser: Int = 600,
    passwordPolicy: ManagementPasswordPolicyPayload = createTestManagementPasswordPolicyPayload(),
    otpConfirmation: OtpConfirmationPayload = createTestOtpConfirmationPayload(),
    accountLockoutPolicy: AccountLockoutPolicyPayload = createTestAccountLockoutPolicyPayload(),
    accountLockoutCheckIntervalSeconds: Int = 60,
    openIpRestrictionPolicy: IpRestrictionPolicyPayload = createTestIpRestrictionPolicyPayload(),
    managementIpRestrictionPolicy: IpRestrictionPolicyPayload = createTestIpRestrictionPolicyPayload(),
    mfaTokenExpirationSeconds: Int = 300,
    maxRequestsPerPeriod: Int = 100,
    rateLimitPeriodSeconds: Int = 60,
    refreshTokenRotationGracePeriodSeconds: Int = 10
) = ManagementSecuritySettingsPayload(
    recentAuthenticationValiditySecondsForOpenUser = recentAuthenticationValiditySecondsForOpenUser,
    recentAuthenticationValiditySecondsForManagementUser = recentAuthenticationValiditySecondsForManagementUser,
    passwordPolicy = passwordPolicy,
    otpConfirmation = otpConfirmation,
    accountLockoutPolicy = accountLockoutPolicy,
    accountLockoutCheckIntervalSeconds = accountLockoutCheckIntervalSeconds,
    openIpRestrictionPolicy = openIpRestrictionPolicy,
    managementIpRestrictionPolicy = managementIpRestrictionPolicy,
    mfaTokenExpirationSeconds = mfaTokenExpirationSeconds,
    maxRequestsPerPeriod = maxRequestsPerPeriod,
    rateLimitPeriodSeconds = rateLimitPeriodSeconds,
    refreshTokenRotationGracePeriodSeconds = refreshTokenRotationGracePeriodSeconds
)
