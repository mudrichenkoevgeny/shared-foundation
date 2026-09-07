package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.ManagementSecuritySettings
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
    recentAuthenticationValiditySeconds = recentAuthenticationValiditySeconds,
    recentAuthenticationValiditySecondsForManagement = recentAuthenticationValiditySecondsForManagement,
    passwordPolicy = passwordPolicy.toManagementPasswordPolicy(),
    otpConfirmation = otpConfirmation.toOtpConfirmation(),
    mfaTokenExpirationSeconds = mfaTokenExpirationSeconds,
    maxRequestsPerPeriod = maxRequestsPerPeriod,
    rateLimitPeriodSeconds = rateLimitPeriodSeconds
)

/**
 * Builds network [ManagementSecuritySettingsPayload] DTO from domain [ManagementSecuritySettings].
 *
 * @return payload model aligned with [ManagementSecuritySettingsPayload] contract.
 */
fun ManagementSecuritySettings.toManagementSecuritySettingsPayload(): ManagementSecuritySettingsPayload = ManagementSecuritySettingsPayload(
    recentAuthenticationValiditySeconds = recentAuthenticationValiditySeconds,
    recentAuthenticationValiditySecondsForManagement = recentAuthenticationValiditySecondsForManagement,
    passwordPolicy = passwordPolicy.toManagementPasswordPolicyPayload(),
    otpConfirmation = otpConfirmation.toOtpConfirmationPayload(),
    mfaTokenExpirationSeconds = mfaTokenExpirationSeconds,
    maxRequestsPerPeriod = maxRequestsPerPeriod,
    rateLimitPeriodSeconds = rateLimitPeriodSeconds
)
