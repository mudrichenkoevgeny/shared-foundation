package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.generateTestUuidString
import kotlin.time.Instant

/**
 * Creates a test [UserPrivate] instance with default mock properties for unit testing.
 *
 * @param id Unique user ID ([UserId]).
 * @param role Assigned access role ([UserRole]).
 * @param accountStatus Current account state ([UserAccountStatus]).
 * @param accountStatusOnRestore Target account status on restore ([UserAccountStatus]).
 * @param authorityLevel Hierarchical weight (0-100).
 * @param permissionCodes Explicit permission codes assigned.
 * @param isTotpEnabled Whether TOTP 2FA is active.
 * @param lastLoginAt Last authentication timestamp.
 * @param lastActiveAt Last activity timestamp.
 * @param createdAt Creation timestamp.
 * @param updatedAt Modification timestamp.
 * @param scheduledPermanentDeletionAt Scheduled deletion timestamp.
 * @param lockoutType Category of lockout ([AccountLockoutType]).
 * @param temporaryLockoutUntil Timestamp until temporary lockout expires.
 */
fun createTestUserPrivate(
    id: UserId = createTestUserId(),
    role: UserRole = UserRole.USER,
    accountStatus: UserAccountStatus = UserAccountStatus.ACTIVE,
    accountStatusOnRestore: UserAccountStatus? = null,
    authorityLevel: Int = 10,
    permissionCodes: Set<PermissionCode> = emptySet(),
    isTotpEnabled: Boolean = false,
    lastLoginAt: Instant? = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    lastActiveAt: Instant? = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    createdAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    updatedAt: Instant? = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    scheduledPermanentDeletionAt: Instant? = null,
    lockoutType: AccountLockoutType = AccountLockoutType.NONE,
    temporaryLockoutUntil: Instant? = null
) = UserPrivate(
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
 * Creates a list of test [UserPrivate] instances with deterministic index-based UUIDs
 * and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserPrivateList(
    count: Int = 2,
    generator: (index: Int) -> UserPrivate = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        createTestUserPrivate(
            id = createTestUserId(generateTestUuidString(index)),
            createdAt = Instant.fromEpochMilliseconds(createdAtMs),
            updatedAt = Instant.fromEpochMilliseconds(createdAtMs + 30_000L),
            lastLoginAt = Instant.fromEpochMilliseconds(createdAtMs + 15_000L),
            lastActiveAt = Instant.fromEpochMilliseconds(createdAtMs + 15_000L)
        )
    }
): List<UserPrivate> = createTestList(count, generator)
