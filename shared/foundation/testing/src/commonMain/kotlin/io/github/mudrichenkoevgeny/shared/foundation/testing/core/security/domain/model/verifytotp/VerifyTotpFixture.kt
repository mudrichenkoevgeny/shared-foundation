package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.verifytotp

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.verifytotp.VerifyTotp

fun createTestVerifyTotp(
    mfaToken: String = "mfa_token_12345",
    code: String = "123456"
) = VerifyTotp(
    mfaToken = mfaToken,
    code = code
)
