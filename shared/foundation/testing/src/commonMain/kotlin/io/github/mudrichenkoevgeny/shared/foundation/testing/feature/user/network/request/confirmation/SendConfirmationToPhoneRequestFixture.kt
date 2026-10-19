package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.confirmation

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_PHONE

fun createTestSendConfirmationToPhoneRequest(
    phoneNumber: String = TEST_PHONE
) = SendConfirmationToPhoneRequest(
    phoneNumber = phoneNumber
)
