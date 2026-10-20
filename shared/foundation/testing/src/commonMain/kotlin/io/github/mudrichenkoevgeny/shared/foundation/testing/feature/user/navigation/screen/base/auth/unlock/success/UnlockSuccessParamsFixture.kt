package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.unlock.success

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success.UnlockSuccessParams

/**
 * Creates a test [UnlockSuccessParams] navigation params instance.
 *
 * @param unlockMethod Unlock method completed ([UnlockMethod]).
 */
fun createTestUnlockSuccessParams(
    unlockMethod: UnlockMethod? = UnlockMethod.EMAIL
): UnlockSuccessParams = UnlockSuccessParams(
    unlockMethod = unlockMethod
)
