package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.generateTestUuidString
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [UserPrivatePayload] wire object for unit testing mapper logic.
 *
 * @param id Raw user ID string.
 * @param role Wire string representation of [UserRole].
 * @param accountStatus Wire string representation of [UserAccountStatus].
 * @param accountStatusOnRestore Target account status wire string on restore.
 * @param authorityLevel Hierarchical weight (0-100).
 * @param permissionCodes Assigned permission code wire strings.
 * @param isTotpEnabled Whether TOTP 2FA is active.
 * @param lastLoginAt Last login epoch-millisecond timestamp.
 * @param lastActiveAt Last activity epoch-millisecond timestamp.
 * @param createdAt Creation epoch-millisecond timestamp.
 * @param updatedAt Modification epoch-millisecond timestamp.
 * @param scheduledPermanentDeletionAt Scheduled deletion epoch-millisecond timestamp.
 * @param lockoutType Wire string representation of [AccountLockoutType].
 * @param temporaryLockoutUntil Expiration epoch-millisecond timestamp for temporary lockout.
 */
fun createTestUserPrivatePayload(
    id: String = TEST_USER_ID,
    role: String = UserRole.USER.value,
    accountStatus: String = UserAccountStatus.ACTIVE.value,
    accountStatusOnRestore: String? = null,
    authorityLevel: Int = 10,
    permissionCodes: Set<String> = emptySet(),
    isTotpEnabled: Boolean = false,
    lastLoginAt: Long? = TEST_TIME_MILLIS,
    lastActiveAt: Long? = TEST_TIME_MILLIS,
    createdAt: Long = TEST_TIME_MILLIS,
    updatedAt: Long? = TEST_TIME_MILLIS,
    scheduledPermanentDeletionAt: Long? = null,
    lockoutType: String = AccountLockoutType.NONE.value,
    temporaryLockoutUntil: Long? = null
) = UserPrivatePayload(
    id = id,
    role = role,
    accountStatus = accountStatus,
    accountStatusOnRestore = accountStatusOnRestore,
    authorityLevel = authorityLevel,
    permissionCodes = permissionCodes,
    isTotpEnabled = isTotpEnabled,
    lastLoginAt = lastLoginAt,
    lastActiveAt = lastActiveAt,
    createdAt = createdAt,
    updatedAt = updatedAt,
    scheduledPermanentDeletionAt = scheduledPermanentDeletionAt,
    lockoutType = lockoutType,
    temporaryLockoutUntil = temporaryLockoutUntil
)

/**
 * Creates a list of test [UserPrivatePayload] wire objects with deterministic raw UUIDs
 * and epoch-millisecond time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserPrivatePayloadList(
    count: Int = 2,
    generator: (index: Int) -> UserPrivatePayload = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        createTestUserPrivatePayload(
            id = generateTestUuidString(index),
            createdAt = createdAtMs,
            updatedAt = createdAtMs + 30_000L,
            lastLoginAt = createdAtMs + 15_000L,
            lastActiveAt = createdAtMs + 15_000L
        )
    }
): List<UserPrivatePayload> = createTestList(count, generator)
