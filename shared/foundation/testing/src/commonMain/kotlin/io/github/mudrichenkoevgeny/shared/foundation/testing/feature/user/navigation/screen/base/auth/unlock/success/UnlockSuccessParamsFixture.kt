package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.unlock.success

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success.UnlockSuccessParams

fun createTestUnlockSuccessParams(
    unlockMethod: UnlockMethod? = UnlockMethod.EMAIL
): UnlockSuccessParams = UnlockSuccessParams(
    unlockMethod = unlockMethod
)
