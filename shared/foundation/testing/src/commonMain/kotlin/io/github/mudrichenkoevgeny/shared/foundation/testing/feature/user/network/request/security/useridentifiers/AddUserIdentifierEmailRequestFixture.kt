package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

fun createTestAddUserIdentifierEmailRequest(
    email: String = TEST_EMAIL,
    password: String = "Secret123!",
    confirmationCode: String = "123456"
) = AddUserIdentifierEmailRequest(
    email = email,
    password = password,
    confirmationCode = confirmationCode
)
