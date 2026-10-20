package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.unlock

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByEmailConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

/**
 * Creates a test [UnlockByEmailConfirmationRequest] request object instance.
 *
 * @param email Account email address string to unlock.
 * @param confirmationCode Email confirmation code string.
 */
fun createTestUnlockByEmailConfirmationRequest(
    email: String = TEST_EMAIL,
    confirmationCode: String = "123456"
) = UnlockByEmailConfirmationRequest(
    email = email,
    confirmationCode = confirmationCode
)
