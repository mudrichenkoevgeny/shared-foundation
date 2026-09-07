package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.ManagementPasswordPolicy

/**
 * Management security configuration containing full policies and administrative parameters.
 *
 * @property recentAuthenticationValiditySeconds How long a recent re-authentication remains valid for sensitive self-service actions, in seconds.
 * @property recentAuthenticationValiditySecondsForManagement How long a recent re-authentication remains valid for management actions, in seconds.
 * @property passwordPolicy Full rules for password strength and validation including [ManagementPasswordPolicy.commonPasswords].
 * @property otpConfirmation Configuration for One-Time Password generation and validation.
 * @property mfaTokenExpirationSeconds Lifetime of the temporary MFA challenge token, in seconds.
 * @property maxRequestsPerPeriod Maximum number of requests per time unit.
 * @property rateLimitPeriodSeconds Time window in seconds for request rate limiting.
 */
data class ManagementSecuritySettings(
    val recentAuthenticationValiditySeconds: Int,
    val recentAuthenticationValiditySecondsForManagement: Int,
    val passwordPolicy: ManagementPasswordPolicy,
    val otpConfirmation: OtpConfirmation,
    val mfaTokenExpirationSeconds: Int,
    val maxRequestsPerPeriod: Int,
    val rateLimitPeriodSeconds: Int
)
