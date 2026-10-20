package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserInternal
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import kotlin.time.Instant

/**
 * Creates a test [UserInternal] domain instance.
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
fun createTestUserInternal(
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
) = UserInternal(
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
