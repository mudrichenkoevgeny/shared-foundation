package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.security.useridentifiers

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.security.useridentifiers.AddUserIdentifierEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

/**
 * Creates a test [AddUserIdentifierEmailRequest] request object instance.
 *
 * @param email New email identifier string.
 * @param password Account password string for re-auth.
 * @param confirmationCode Email confirmation code string.
 */
fun createTestAddUserIdentifierEmailRequest(
    email: String = TEST_EMAIL,
    password: String = "Secret123!",
    confirmationCode: String = "123456"
) = AddUserIdentifierEmailRequest(
    email = email,
    password = password,
    confirmationCode = confirmationCode
)
