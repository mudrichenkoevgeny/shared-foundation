package io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.OpenPasswordPolicy

/**
 * Open security settings surfaced to clients and open API endpoints.
 *
 * @property passwordPolicy Public password validation rules without common passwords; see [OpenPasswordPolicy].
 * @property otpConfirmation Configuration for One-Time Password generation and validation; see [OtpConfirmation].
 */
data class OpenSecuritySettings(
    val passwordPolicy: OpenPasswordPolicy,
    val otpConfirmation: OtpConfirmation
)
