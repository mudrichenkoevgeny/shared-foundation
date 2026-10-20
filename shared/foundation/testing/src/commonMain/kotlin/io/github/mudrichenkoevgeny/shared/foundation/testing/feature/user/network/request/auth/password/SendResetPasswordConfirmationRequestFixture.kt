package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.password

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.SendResetPasswordConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

/**
 * Creates a test [SendResetPasswordConfirmationRequest] request object instance.
 *
 * @param email Target email address string.
 */
fun createTestSendResetPasswordConfirmationRequest(
    email: String = TEST_EMAIL
) = SendResetPasswordConfirmationRequest(
    email = email
)
