package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.unlock.methodselection

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection.UnlockMethodSelectionParams

fun createTestUnlockMethodSelectionParams(
    unlockMethod: UnlockMethod? = UnlockMethod.EMAIL,
    accountLockoutType: AccountLockoutType? = AccountLockoutType.TEMPORARY,
    temporaryLockoutUntil: Long? = null
): UnlockMethodSelectionParams = UnlockMethodSelectionParams(
    unlockMethod = unlockMethod,
    accountLockoutType = accountLockoutType,
    temporaryLockoutUntil = temporaryLockoutUntil
)
