package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.accountlockout

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutPolicy

/**
 * Creates a test [AccountLockoutPolicy] domain instance.
 *
 * @param maxFailedPasswordAttempts Maximum allowed failed password attempts before lockout.
 * @param maxFailedOtpAttempts Maximum allowed failed OTP attempts before lockout.
 * @param maxFailedTotpAttempts Maximum allowed failed TOTP attempts before lockout.
 * @param failedAttemptsWindowSeconds Time window in seconds for tracking failed attempts.
 * @param lockoutDurationSeconds Duration in seconds for temporary account lockout.
 * @param indefiniteLockoutThreshold Threshold for triggering indefinite account lockout.
 * @param isSelfServiceUnlockEnabled Flag indicating whether self-service unlock is allowed.
 */
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
