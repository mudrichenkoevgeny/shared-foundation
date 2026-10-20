package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword.ResetPasswordParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

/**
 * Creates a test [ResetPasswordParams] navigation params instance.
 *
 * @param email Pre-filled email string.
 * @param code Pre-filled password reset confirmation code.
 */
fun createTestResetPasswordParams(
    email: String? = TEST_EMAIL,
    code: String? = null
): ResetPasswordParams = ResetPasswordParams(
    email = email,
    code = code
)
