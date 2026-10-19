package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.accountlockout

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutPolicy

fun createTestAccountLockoutPolicy(
    maxFailedPasswordAttempts: Int = 5,
    maxFailedOtpAttempts: Int = 5,
    maxFailedTotpAttempts: Int = 5,
    failedAttemptsWindowSeconds: Int = 300,
    lockoutDurationSeconds: Int = 1800,
    indefiniteLockoutThreshold: Int = 3,
    isSelfServiceUnlockEnabled: Boolean = true
) = AccountLockoutPolicy(
    maxFailedPasswordAttempts = maxFailedPasswordAttempts,
    maxFailedOtpAttempts = maxFailedOtpAttempts,
    maxFailedTotpAttempts = maxFailedTotpAttempts,
    failedAttemptsWindowSeconds = failedAttemptsWindowSeconds,
    lockoutDurationSeconds = lockoutDurationSeconds,
    indefiniteLockoutThreshold = indefiniteLockoutThreshold,
    isSelfServiceUnlockEnabled = isSelfServiceUnlockEnabled
)
