package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.securitysettings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.otpconfirmation.OtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordpolicy.OpenPasswordPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.OpenSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.otpconfirmation.createTestOtpConfirmation
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.passwordpolicy.createTestOpenPasswordPolicy

fun createTestOpenSecuritySettings(
    passwordPolicy: OpenPasswordPolicy = createTestOpenPasswordPolicy(),
    otpConfirmation: OtpConfirmation = createTestOtpConfirmation()
) = OpenSecuritySettings(
    passwordPolicy = passwordPolicy,
    otpConfirmation = otpConfirmation
)
