package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.unlock.otpinput

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput.UnlockOtpInputParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL

/**
 * Creates a test [UnlockOtpInputParams] navigation params instance.
 *
 * @param unlockMethod Chosen account unlock method ([UnlockMethod]).
 * @param identifier Target credential string.
 * @param retryAfterSeconds Delay in seconds before a new code can be requested.
 * @param code Pre-filled OTP code.
 */
fun createTestUnlockOtpInputParams(
    unlockMethod: UnlockMethod? = UnlockMethod.EMAIL,
    identifier: String? = TEST_EMAIL,
    retryAfterSeconds: Int? = null,
    code: String? = null
): UnlockOtpInputParams = UnlockOtpInputParams(
    unlockMethod = unlockMethod,
    identifier = identifier,
    retryAfterSeconds = retryAfterSeconds,
    code = code
)
