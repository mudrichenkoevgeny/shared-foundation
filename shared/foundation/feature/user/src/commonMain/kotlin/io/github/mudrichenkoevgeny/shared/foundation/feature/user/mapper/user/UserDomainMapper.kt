package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserSummary

/**
 * Extension functions mapping between domain user representations.
 */

/**
 * Converts a [UserInternal] into a client-facing [UserPrivate].
 */
fun UserInternal.toUserPrivate(): UserPrivate = UserPrivate(
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
 * Converts a [UserPrivate] into a public [UserSummary].
 *
 * Retains only core public identifiers (`id`, `role`, `accountStatus`).
 */
fun UserPrivate.toUserSummary(): UserSummary = UserSummary(
    id = id,
    role = role,
    accountStatus = accountStatus
)

/**
 * Converts a [UserInternal] directly into a public [UserSummary].
 *
 * Maps fields directly without allocating an intermediate [UserPrivate] instance.
 */
fun UserInternal.toUserSummary(): UserSummary = UserSummary(
    id = id,
    role = role,
    accountStatus = accountStatus
)
