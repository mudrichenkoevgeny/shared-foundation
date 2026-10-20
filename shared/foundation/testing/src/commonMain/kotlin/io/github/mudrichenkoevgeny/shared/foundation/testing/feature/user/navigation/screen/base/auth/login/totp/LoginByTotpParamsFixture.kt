package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.login.totp

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpMode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpParams

/**
 * Creates a test [LoginByTotpParams] navigation params instance.
 *
 * @param mfaToken MFA setup session token string.
 * @param loginByTotpMode TOTP login display mode ([LoginByTotpMode]).
 * @param redirectUrl Post-login redirect URL.
 */
fun createTestLoginByTotpParams(
    mfaToken: String? = "mfa_token_12345",
    loginByTotpMode: LoginByTotpMode = LoginByTotpMode.DEFAULT,
    redirectUrl: String? = null
): LoginByTotpParams = LoginByTotpParams(
    mfaToken = mfaToken,
    loginByTotpMode = loginByTotpMode,
    redirectUrl = redirectUrl
)
