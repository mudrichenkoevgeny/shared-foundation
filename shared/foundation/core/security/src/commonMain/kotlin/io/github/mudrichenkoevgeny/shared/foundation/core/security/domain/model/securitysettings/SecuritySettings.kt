package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.PasswordPolicy

/**
 * Security configuration surfaced to services, repositories, and clients (outside wire-only DTOs).
 *
 * @property recentAuthenticationValidityInMinutes How long, in whole minutes, a recent
 *   step-up or re-authentication remains valid for sensitive operations.
 * @property passwordPolicy Rules for password strength and validation.
 * @property otpConfirmation Configuration for One-Time Password generation and validation.
 */
data class SecuritySettings(
    val recentAuthenticationValidityInMinutes: Long,
    val passwordPolicy: PasswordPolicy,
    val otpConfirmation: OtpConfirmation
)