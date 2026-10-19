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
