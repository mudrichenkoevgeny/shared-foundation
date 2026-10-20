package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.unlock.targetinput

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput.UnlockTargetInputParams

/**
 * Creates a test [UnlockTargetInputParams] navigation params instance.
 *
 * @param unlockMethod Chosen account unlock method ([UnlockMethod]).
 */
fun createTestUnlockTargetInputParams(
    unlockMethod: UnlockMethod? = UnlockMethod.EMAIL
): UnlockTargetInputParams = UnlockTargetInputParams(
    unlockMethod = unlockMethod
)
