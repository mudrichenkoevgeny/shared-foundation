package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.iprestriction.IpRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.ManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.accountlockout.createTestAccountLockoutPolicy
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.iprestriction.createTestIpRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.otpconfirmation.createTestOtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.passwordpolicy.createTestManagementPasswordPolicy

/**
 * Creates a test [ManagementSecuritySettings] domain instance.
 *
 * @param recentAuthenticationValiditySecondsForOpenUser Session re-auth validity for open users.
 * @param recentAuthenticationValiditySecondsForManagementUser Session re-auth validity for management users.
 * @param passwordPolicy Password strength policy.
 * @param otpConfirmation OTP code generation and retry rules.
 * @param accountLockoutPolicy Account lockout rules for failed logins.
 * @param accountLockoutCheckIntervalSeconds Interval for account lockout cleanup.
 * @param openIpRestrictionPolicy IP restriction policy for open APIs.
 * @param managementIpRestrictionPolicy IP restriction policy for management APIs.
 * @param mfaTokenExpirationSeconds Expiration time in seconds for MFA setup tokens.
 * @param maxRequestsPerPeriod Maximum rate-limit requests per period.
 * @param rateLimitPeriodSeconds Duration in seconds of rate limit evaluation period.
 * @param refreshTokenRotationGracePeriodSeconds Grace period in seconds for refresh token rotation.
 */
fun createTestManagementSecuritySettings(
    recentAuthenticationValiditySecondsForOpenUser: Int = 300,
    recentAuthenticationValiditySecondsForManagementUser: Int = 600,
    passwordPolicy: ManagementPasswordPolicy = createTestManagementPasswordPolicy(),
    otpConfirmation: OtpConfirmation = createTestOtpConfirmation(),
    accountLockoutPolicy: AccountLockoutPolicy = createTestAccountLockoutPolicy(),
    accountLockoutCheckIntervalSeconds: Int = 60,
    openIpRestrictionPolicy: IpRestrictionPolicy = createTestIpRestrictionPolicy(),
    managementIpRestrictionPolicy: IpRestrictionPolicy = createTestIpRestrictionPolicy(),
    mfaTokenExpirationSeconds: Int = 300,
    maxRequestsPerPeriod: Int = 100,
    rateLimitPeriodSeconds: Int = 60,
    refreshTokenRotationGracePeriodSeconds: Int = 10
) = ManagementSecuritySettings(
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
