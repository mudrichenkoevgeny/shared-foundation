package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicy

/**
 * Security configuration surfaced to services, repositories, and clients (outside wire-only DTOs).
 *
 * @property recentAuthenticationValiditySeconds How long a recent step-up or re-authentication
 * remains valid for sensitive **self-service** operations, in seconds.
 * @property recentAuthenticationValiditySecondsForManagement How long a recent re-authentication
 * remains valid for high-risk **management** actions, in seconds.
 * @property passwordPolicy Rules for password strength and validation.
 * @property otpConfirmation Configuration for One-Time Password generation and validation.
 * @property mfaTokenExpirationSeconds Lifetime, in seconds, of the temporary MFA challenge token.
 */
data class SecuritySettings(
    val recentAuthenticationValiditySeconds: Int,
    val recentAuthenticationValiditySecondsForManagement: Int,
    val passwordPolicy: PasswordPolicy,
    val otpConfirmation: OtpConfirmation,
    val mfaTokenExpirationSeconds: Int
)