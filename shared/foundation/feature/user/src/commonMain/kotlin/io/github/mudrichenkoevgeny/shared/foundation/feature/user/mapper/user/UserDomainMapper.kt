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
public fun UserInternal.toUserPrivate(): UserPrivate =
    UserPrivate(
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
        avatarUrls = avatarUrls,
        birthdate = birthdate
    )

/**
 * Converts a [UserPrivate] into a public [UserSummary].
 *
 * Retains core public identifiers (`id`, `role`, `accountStatus`, `nickname`, `displayName`, `avatarUrls`).
 */
public fun UserPrivate.toUserSummary(): UserSummary =
    UserSummary(
        id = id,
        role = role,
        accountStatus = accountStatus,
        nickname = nickname,
        displayName = displayName,
        avatarUrls = avatarUrls
    )

/**
 * Converts a [UserInternal] directly into a public [UserSummary].
 *
 * Maps fields directly without allocating an intermediate [UserPrivate] instance.
 */
public fun UserInternal.toUserSummary(): UserSummary =
    UserSummary(
        id = id,
        role = role,
        accountStatus = accountStatus,
        nickname = nickname,
        displayName = displayName,
        avatarUrls = avatarUrls
    )
