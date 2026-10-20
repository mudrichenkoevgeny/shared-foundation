package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.OpenPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.otpconfirmation.createTestOtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.passwordpolicy.createTestOpenPasswordPolicyPayload

/**
 * Creates a test [OpenSecuritySettingsPayload] wire object instance.
 *
 * @param passwordPolicy Password policy payload for open sign-up/password changes.
 * @param otpConfirmation OTP configuration settings payload.
 */
fun createTestOpenSecuritySettingsPayload(
    passwordPolicy: OpenPasswordPolicyPayload = createTestOpenPasswordPolicyPayload(),
    otpConfirmation: OtpConfirmationPayload = createTestOtpConfirmationPayload()
) = OpenSecuritySettingsPayload(
    passwordPolicy = passwordPolicy,
    otpConfirmation = otpConfirmation
)
