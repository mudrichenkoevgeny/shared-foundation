package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_PHONE

fun createTestAddUserIdentifierPhoneRequest(
    phoneNumber: String = TEST_PHONE,
    confirmationCode: String = "123456"
) = AddUserIdentifierPhoneRequest(
    phoneNumber = phoneNumber,
    confirmationCode = confirmationCode
)
