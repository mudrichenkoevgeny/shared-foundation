package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.verifytotp

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload

/**
 * Creates a test [VerifyTotpPayload] wire object instance.
 *
 * @param mfaToken MFA session token string.
 * @param code 6-digit TOTP code string.
 */
fun createTestVerifyTotpPayload(
    mfaToken: String = "mfa_token_12345",
    code: String = "123456"
) = VerifyTotpPayload(
    mfaToken = mfaToken,
    code = code
)
