package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_PHONE

fun createTestLoginByPhoneRequest(
    phoneNumber: String = TEST_PHONE,
    confirmationCode: String = "123456"
) = LoginByPhoneRequest(
    phoneNumber = phoneNumber,
    confirmationCode = confirmationCode
)
