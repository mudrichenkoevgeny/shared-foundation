package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.totpsetup

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.totpsetup.TotpSetupPayload

/**
 * Creates a test [TotpSetupPayload] wire object instance.
 *
 * @param secretKey TOTP secret key string.
 * @param otpAuthUrl Complete OTP Auth URI string.
 * @param mfaToken MFA setup session token string.
 */
fun createTestTotpSetupPayload(
    secretKey: String = "JBSWY3DPEHPK3PXP",
    otpAuthUrl: String = "otpauth://totp/Test:user@example.com?secret=JBSWY3DPEHPK3PXP&issuer=Test",
    mfaToken: String = "mfa_token_12345"
) = TotpSetupPayload(
    secretKey = secretKey,
    otpAuthUrl = otpAuthUrl,
    mfaToken = mfaToken
)
