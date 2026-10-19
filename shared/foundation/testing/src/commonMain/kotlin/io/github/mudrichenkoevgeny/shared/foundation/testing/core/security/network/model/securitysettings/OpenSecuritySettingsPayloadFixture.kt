package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.passwordpolicy.OpenPasswordPolicyPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.otpconfirmation.createTestOtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.passwordpolicy.createTestOpenPasswordPolicyPayload

fun createTestOpenSecuritySettingsPayload(
    passwordPolicy: OpenPasswordPolicyPayload = createTestOpenPasswordPolicyPayload(),
    otpConfirmation: OtpConfirmationPayload = createTestOtpConfirmationPayload()
) = OpenSecuritySettingsPayload(
    passwordPolicy = passwordPolicy,
    otpConfirmation = otpConfirmation
)
