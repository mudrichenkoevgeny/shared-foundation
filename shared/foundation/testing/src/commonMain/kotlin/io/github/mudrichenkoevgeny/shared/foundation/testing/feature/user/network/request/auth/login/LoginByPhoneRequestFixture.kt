package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_PHONE

/**
 * Creates a test [LoginByPhoneRequest] request object instance.
 *
 * @param phoneNumber Login phone number string.
 * @param confirmationCode SMS confirmation code string.
 */
fun createTestLoginByPhoneRequest(
    phoneNumber: String = TEST_PHONE,
    confirmationCode: String = "123456"
) = LoginByPhoneRequest(
    phoneNumber = phoneNumber,
    confirmationCode = confirmationCode
)
