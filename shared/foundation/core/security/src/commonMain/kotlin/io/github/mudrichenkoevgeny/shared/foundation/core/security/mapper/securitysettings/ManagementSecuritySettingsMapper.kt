package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.ManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.accountlockout.toAccountLockoutPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.accountlockout.toAccountLockoutPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.iprestriction.toIpRestrictionPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.iprestriction.toIpRestrictionPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.otpconfirmation.toOtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.otpconfirmation.toOtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy.toManagementPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy.toManagementPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload

/**
 * Maps between [ManagementSecuritySettingsPayload] DTO and domain [ManagementSecuritySettings].
 */

/**
 * Builds domain [ManagementSecuritySettings] from [ManagementSecuritySettingsPayload] DTO.
 *
 * @return domain model aligned with [ManagementSecuritySettings] semantics.
 */
fun ManagementSecuritySettingsPayload.toManagementSecuritySettings(): ManagementSecuritySettings = ManagementSecuritySettings(
    recentAuthenticationValiditySecondsForOpenUser = recentAuthenticationValiditySecondsForOpenUser,
    recentAuthenticationValiditySecondsForManagementUser = recentAuthenticationValiditySecondsForManagementUser,
    passwordPolicy = passwordPolicy.toManagementPasswordPolicy(),
    otpConfirmation = otpConfirmation.toOtpConfirmation(),
    accountLockoutPolicy = accountLockoutPolicy.toAccountLockoutPolicy(),
    accountLockoutCheckIntervalSeconds = accountLockoutCheckIntervalSeconds,
    openIpRestrictionPolicy = openIpRestrictionPolicy.toIpRestrictionPolicy(),
    managementIpRestrictionPolicy = managementIpRestrictionPolicy.toIpRestrictionPolicy(),
    mfaTokenExpirationSeconds = mfaTokenExpirationSeconds,
    maxRequestsPerPeriod = maxRequestsPerPeriod,
    rateLimitPeriodSeconds = rateLimitPeriodSeconds,
    refreshTokenRotationGracePeriodSeconds = refreshTokenRotationGracePeriodSeconds
)

/**
 * Builds network [ManagementSecuritySettingsPayload] DTO from domain [ManagementSecuritySettings].
 *
 * @return payload model aligned with [ManagementSecuritySettingsPayload] contract.
 */
fun ManagementSecuritySettings.toManagementSecuritySettingsPayload(): ManagementSecuritySettingsPayload = ManagementSecuritySettingsPayload(
    recentAuthenticationValiditySecondsForOpenUser = recentAuthenticationValiditySecondsForOpenUser,
    recentAuthenticationValiditySecondsForManagementUser = recentAuthenticationValiditySecondsForManagementUser,
    passwordPolicy = passwordPolicy.toManagementPasswordPolicyPayload(),
    otpConfirmation = otpConfirmation.toOtpConfirmationPayload(),
    accountLockoutPolicy = accountLockoutPolicy.toAccountLockoutPolicyPayload(),
    accountLockoutCheckIntervalSeconds = accountLockoutCheckIntervalSeconds,
    openIpRestrictionPolicy = openIpRestrictionPolicy.toIpRestrictionPolicyPayload(),
    managementIpRestrictionPolicy = managementIpRestrictionPolicy.toIpRestrictionPolicyPayload(),
    mfaTokenExpirationSeconds = mfaTokenExpirationSeconds,
    maxRequestsPerPeriod = maxRequestsPerPeriod,
    rateLimitPeriodSeconds = rateLimitPeriodSeconds,
    refreshTokenRotationGracePeriodSeconds = refreshTokenRotationGracePeriodSeconds
)
