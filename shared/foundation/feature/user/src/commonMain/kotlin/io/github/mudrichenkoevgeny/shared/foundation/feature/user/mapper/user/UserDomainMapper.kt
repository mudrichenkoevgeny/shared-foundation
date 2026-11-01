package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserSummary

/**
 * Extension functions mapping between domain user representations.
 */

/**
 * Converts a [UserInternal] into a client-facing [UserPrivate].
 *
 * @param avatarUrl Optional dynamically generated CDN or presigned GET avatar image URL.
 */
fun UserInternal.toUserPrivate(avatarUrl: String? = null): UserPrivate = UserPrivate(
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
    temporaryLockoutUntil = temporaryLockoutUntil,
    storageConfiguration = storageConfiguration,
    nickname = nickname,
    displayName = displayName,
    avatarFileId = avatarFileId,
    avatarUrl = avatarUrl,
    birthdate = birthdate
)

/**
 * Converts a [UserPrivate] into a public [UserSummary].
 *
 * Retains core public identifiers (`id`, `role`, `accountStatus`, `nickname`, `displayName`, `avatarUrl`).
 */
fun UserPrivate.toUserSummary(): UserSummary = UserSummary(
    id = id,
    role = role,
    accountStatus = accountStatus,
    nickname = nickname,
    displayName = displayName,
    avatarUrl = avatarUrl
)

/**
 * Converts a [UserInternal] directly into a public [UserSummary].
 *
 * Maps fields directly without allocating an intermediate [UserPrivate] instance.
 *
 * @param avatarUrl Optional dynamically generated CDN or presigned GET avatar image URL.
 */
fun UserInternal.toUserSummary(avatarUrl: String? = null): UserSummary = UserSummary(
    id = id,
    role = role,
    accountStatus = accountStatus,
    nickname = nickname,
    displayName = displayName,
    avatarUrl = avatarUrl
)
