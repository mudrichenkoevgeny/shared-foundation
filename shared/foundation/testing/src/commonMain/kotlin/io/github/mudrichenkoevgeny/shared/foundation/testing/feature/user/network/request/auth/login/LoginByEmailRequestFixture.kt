package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

fun createTestLoginByEmailRequest(
    email: String = TEST_EMAIL,
    password: String = "Secret123!"
) = LoginByEmailRequest(
    email = email,
    password = password
)
