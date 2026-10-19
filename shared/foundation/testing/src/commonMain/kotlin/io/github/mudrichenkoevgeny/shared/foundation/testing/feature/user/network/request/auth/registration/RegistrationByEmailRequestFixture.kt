package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.registration

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.registration.RegistrationByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

fun createTestRegistrationByEmailRequest(
    email: String = TEST_EMAIL,
    password: String = "Secret123!",
    confirmationCode: String = "123456"
) = RegistrationByEmailRequest(
    email = email,
    password = password,
    confirmationCode = confirmationCode
)
