package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.OpenSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.otpconfirmation.toOtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.otpconfirmation.toOtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy.toOpenPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.passwordpolicy.toOpenPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload

/**
 * Maps between [OpenSecuritySettingsPayload] DTO and domain [OpenSecuritySettings].
 */

/**
 * Builds domain [OpenSecuritySettings] from [OpenSecuritySettingsPayload] DTO.
 *
 * @return domain model aligned with [OpenSecuritySettings] semantics.
 */
fun OpenSecuritySettingsPayload.toOpenSecuritySettings(): OpenSecuritySettings = OpenSecuritySettings(
    passwordPolicy = passwordPolicy.toOpenPasswordPolicy(),
    otpConfirmation = otpConfirmation.toOtpConfirmation()
)

/**
 * Builds network [OpenSecuritySettingsPayload] DTO from domain [OpenSecuritySettings].
 *
 * @return payload model aligned with [OpenSecuritySettingsPayload] contract.
 */
fun OpenSecuritySettings.toOpenSecuritySettingsPayload(): OpenSecuritySettingsPayload = OpenSecuritySettingsPayload(
    passwordPolicy = passwordPolicy.toOpenPasswordPolicyPayload(),
    otpConfirmation = otpConfirmation.toOtpConfirmationPayload()
)
