package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.confirmation

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_PHONE

/**
 * Creates a test [SendConfirmationToPhoneRequest] request object instance.
 *
 * @param phoneNumber Destination phone number string.
 */
fun createTestSendConfirmationToPhoneRequest(
    phoneNumber: String = TEST_PHONE
) = SendConfirmationToPhoneRequest(
    phoneNumber = phoneNumber
)
