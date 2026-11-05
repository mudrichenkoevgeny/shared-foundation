package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.request.auth.unlock

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByPhoneConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_PHONE

/**
* Creates a test [UnlockByPhoneConfirmationRequest] request object instance.
*
* @param phoneNumber Phone number string to unlock.
* @param confirmationCode SMS confirmation code string.
*/
public fun createTestUnlockByPhoneConfirmationRequest(
    phoneNumber: String = TEST_PHONE,
    confirmationCode: String = "123456"
): UnlockByPhoneConfirmationRequest =
    UnlockByPhoneConfirmationRequest(
        phoneNumber = phoneNumber,
        confirmationCode = confirmationCode
    )
