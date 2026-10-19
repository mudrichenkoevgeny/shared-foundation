package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.password

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.SendResetPasswordConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

fun createTestSendResetPasswordConfirmationRequest(
    email: String = TEST_EMAIL
) = SendResetPasswordConfirmationRequest(
    email = email
)
