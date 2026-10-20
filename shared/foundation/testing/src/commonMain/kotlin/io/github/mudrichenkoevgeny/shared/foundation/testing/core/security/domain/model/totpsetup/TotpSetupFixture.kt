package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.totpsetup

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.totpsetup.TotpSetup

/**
 * Creates a test [TotpSetup] domain instance.
 *
 * @param secretKey TOTP secret key string.
 * @param otpAuthUrl Complete OTP Auth URI string.
 * @param mfaToken MFA setup session token string.
 */
fun createTestTotpSetup(
    secretKey: String = "JBSWY3DPEHPK3PXP",
    otpAuthUrl: String = "otpauth://totp/Test:user@example.com?secret=JBSWY3DPEHPK3PXP&issuer=Test",
    mfaToken: String = "mfa_token_12345"
) = TotpSetup(
    secretKey = secretKey,
    otpAuthUrl = otpAuthUrl,
    mfaToken = mfaToken
)
