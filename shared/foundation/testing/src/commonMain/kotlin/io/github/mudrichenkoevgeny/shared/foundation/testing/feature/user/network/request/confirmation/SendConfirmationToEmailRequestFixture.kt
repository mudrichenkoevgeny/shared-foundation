package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.confirmation

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

/**
 * Creates a test [SendConfirmationToEmailRequest] request object instance.
 *
 * @param email Destination email address string.
 */
fun createTestSendConfirmationToEmailRequest(
    email: String = TEST_EMAIL
) = SendConfirmationToEmailRequest(
    email = email
)
