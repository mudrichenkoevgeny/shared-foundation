package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.unlock.targetinput

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput.UnlockTargetInputParams

fun createTestUnlockTargetInputParams(
    unlockMethod: UnlockMethod? = UnlockMethod.EMAIL
): UnlockTargetInputParams = UnlockTargetInputParams(
    unlockMethod = unlockMethod
)
