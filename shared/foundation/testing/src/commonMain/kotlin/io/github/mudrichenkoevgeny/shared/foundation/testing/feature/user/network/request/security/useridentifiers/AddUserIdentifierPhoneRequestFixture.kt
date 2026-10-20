package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_PHONE

/**
 * Creates a test [AddUserIdentifierPhoneRequest] request object instance.
 *
 * @param phoneNumber New phone number string.
 * @param confirmationCode SMS confirmation code string.
 */
fun createTestAddUserIdentifierPhoneRequest(
    phoneNumber: String = TEST_PHONE,
    confirmationCode: String = "123456"
) = AddUserIdentifierPhoneRequest(
    phoneNumber = phoneNumber,
    confirmationCode = confirmationCode
)
