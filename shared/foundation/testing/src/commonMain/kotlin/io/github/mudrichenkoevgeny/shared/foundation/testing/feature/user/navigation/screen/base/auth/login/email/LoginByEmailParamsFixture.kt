package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.login.email

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email.LoginByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

fun createTestLoginByEmailParams(
    email: String? = TEST_EMAIL,
    redirectUrl: String? = null
): LoginByEmailParams = LoginByEmailParams(
    email = email,
    redirectUrl = redirectUrl
)
