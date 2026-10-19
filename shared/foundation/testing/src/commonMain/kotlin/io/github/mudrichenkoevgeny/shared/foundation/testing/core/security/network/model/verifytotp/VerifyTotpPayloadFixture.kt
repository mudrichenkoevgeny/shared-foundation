package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.network.model.verifytotp

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload

fun createTestVerifyTotpPayload(
    mfaToken: String = "mfa_token_12345",
    code: String = "123456"
) = VerifyTotpPayload(
    mfaToken = mfaToken,
    code = code
)
