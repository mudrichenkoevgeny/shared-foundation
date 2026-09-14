package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.accountlockout

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.accountlockout.AccountLockoutPolicyPayload

/**
 * Maps between [AccountLockoutPolicyPayload] DTO and domain [AccountLockoutPolicy].
 */

/**
 * Builds domain [AccountLockoutPolicy] from [AccountLockoutPolicyPayload] DTO.
 *
 * @return domain model aligned with [AccountLockoutPolicy] semantics.
 */
fun AccountLockoutPolicyPayload.toAccountLockoutPolicy(): AccountLockoutPolicy = AccountLockoutPolicy(
    maxFailedPasswordAttempts = maxFailedPasswordAttempts,
    maxFailedOtpAttempts = maxFailedOtpAttempts,
    maxFailedTotpAttempts = maxFailedTotpAttempts,
    failedAttemptsWindowSeconds = failedAttemptsWindowSeconds,
    lockoutDurationSeconds = lockoutDurationSeconds,
    indefiniteLockoutThreshold = indefiniteLockoutThreshold,
    isSelfServiceUnlockEnabled = isSelfServiceUnlockEnabled
)

/**
 * Builds network [AccountLockoutPolicyPayload] DTO from domain [AccountLockoutPolicy].
 *
 * @return payload model aligned with [AccountLockoutPolicyPayload] contract.
 */
fun AccountLockoutPolicy.toAccountLockoutPolicyPayload(): AccountLockoutPolicyPayload = AccountLockoutPolicyPayload(
    maxFailedPasswordAttempts = maxFailedPasswordAttempts,
    maxFailedOtpAttempts = maxFailedOtpAttempts,
    maxFailedTotpAttempts = maxFailedTotpAttempts,
    failedAttemptsWindowSeconds = failedAttemptsWindowSeconds,
    lockoutDurationSeconds = lockoutDurationSeconds,
    indefiniteLockoutThreshold = indefiniteLockoutThreshold,
    isSelfServiceUnlockEnabled = isSelfServiceUnlockEnabled
)
