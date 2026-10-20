package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.password

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.ResetPasswordRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

/**
 * Creates a test [ResetPasswordRequest] request object instance.
 *
 * @param email Target user email address string.
 * @param confirmationCode Password reset confirmation code string.
 * @param newPassword New password string.
 */
fun createTestResetPasswordRequest(
    email: String = TEST_EMAIL,
    confirmationCode: String = "123456",
    newPassword: String = "NewSecret123!"
) = ResetPasswordRequest(
    email = email,
    confirmationCode = confirmationCode,
    newPassword = newPassword
)
