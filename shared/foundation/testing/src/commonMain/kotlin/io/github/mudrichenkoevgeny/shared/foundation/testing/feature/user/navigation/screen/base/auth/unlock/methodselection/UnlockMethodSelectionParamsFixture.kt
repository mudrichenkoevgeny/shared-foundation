package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.auth.unlock.methodselection

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.UnlockMethod
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection.UnlockMethodSelectionParams

/**
 * Creates a test [UnlockMethodSelectionParams] navigation params instance.
 *
 * @param unlockMethod Chosen account unlock method ([UnlockMethod]).
 * @param accountLockoutType Lockout type ([AccountLockoutType]).
 * @param temporaryLockoutUntil Timestamp until temporary lockout expires.
 */
fun createTestUnlockMethodSelectionParams(
    unlockMethod: UnlockMethod? = UnlockMethod.EMAIL,
    accountLockoutType: AccountLockoutType? = AccountLockoutType.TEMPORARY,
    temporaryLockoutUntil: Long? = null
): UnlockMethodSelectionParams = UnlockMethodSelectionParams(
    unlockMethod = unlockMethod,
    accountLockoutType = accountLockoutType,
    temporaryLockoutUntil = temporaryLockoutUntil
)
