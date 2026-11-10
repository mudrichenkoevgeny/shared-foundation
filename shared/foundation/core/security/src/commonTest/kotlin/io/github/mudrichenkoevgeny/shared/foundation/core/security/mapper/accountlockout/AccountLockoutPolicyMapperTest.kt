package io.github.mudrichenkoevgeny.shared.foundation.core.security.mapper.accountlockout

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutPolicy
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.accountlockout.AccountLockoutPolicyPayload
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Unit tests verifying mapping between [AccountLockoutPolicyPayload] DTO and domain [AccountLockoutPolicy].
 */
class AccountLockoutPolicyMapperTest {
    @Test
    fun testAccountLockoutPolicyPayloadToDomainMapping() {
        val payload = AccountLockoutPolicyPayload(
            maxFailedPasswordAttempts = 5,
            maxFailedOtpAttempts = 3,
            maxFailedTotpAttempts = 4,
            failedAttemptsWindowSeconds = 600,
            lockoutDurationSeconds = 1200,
            indefiniteLockoutThreshold = 5,
            isSelfServiceUnlockEnabled = true
        )

        val domain = payload.toAccountLockoutPolicy()

        assertEquals(5, domain.maxFailedPasswordAttempts)
        assertEquals(3, domain.maxFailedOtpAttempts)
        assertEquals(4, domain.maxFailedTotpAttempts)
        assertEquals(600, domain.failedAttemptsWindowSeconds)
        assertEquals(1200, domain.lockoutDurationSeconds)
        assertEquals(5, domain.indefiniteLockoutThreshold)
        assertEquals(true, domain.isSelfServiceUnlockEnabled)
    }

    @Test
    fun testAccountLockoutPolicyDomainToPayloadMapping() {
        val domain = AccountLockoutPolicy(
            maxFailedPasswordAttempts = 10,
            maxFailedOtpAttempts = 5,
            maxFailedTotpAttempts = 5,
            failedAttemptsWindowSeconds = 900,
            lockoutDurationSeconds = 1800,
            indefiniteLockoutThreshold = 3,
            isSelfServiceUnlockEnabled = false
        )

        val payload = domain.toAccountLockoutPolicyPayload()

        assertEquals(10, payload.maxFailedPasswordAttempts)
        assertEquals(5, payload.maxFailedOtpAttempts)
        assertEquals(5, payload.maxFailedTotpAttempts)
        assertEquals(900, payload.failedAttemptsWindowSeconds)
        assertEquals(1800, payload.lockoutDurationSeconds)
        assertEquals(3, payload.indefiniteLockoutThreshold)
        assertEquals(false, payload.isSelfServiceUnlockEnabled)
    }
}
