package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.accountlockout.AccountLockoutType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import kotlin.time.Instant

/**
 * Extension functions mapping private user models between domain and network representations.
 */

/**
 * Builds domain [UserPrivate] from [UserPrivatePayload].
 *
 * @throws IllegalArgumentException when `id` is not a valid user UUID string, or when a wire value
 * for `role`, `accountStatus`, `accountStatusOnRestore`, or `lockoutType` is not recognized.
 */
fun UserPrivatePayload.toUserPrivate(): UserPrivate = UserPrivate(
    id = id.toUserIdOrThrow(),
    role = UserRole.fromValueOrThrow(role),
    accountStatus = UserAccountStatus.fromValueOrThrow(accountStatus),
    accountStatusOnRestore = accountStatusOnRestore?.let { accountStatusOnRestore ->
        UserAccountStatus.fromValueOrNull(accountStatusOnRestore)
    },
    authorityLevel = authorityLevel,
    permissionCodes = permissionCodes.map { permissionCode ->
        PermissionCode(permissionCode)
    }.toSet(),
    isTotpEnabled = isTotpEnabled,
    lastLoginAt = lastLoginAt?.let(Instant::fromEpochMilliseconds),
    lastActiveAt = lastActiveAt?.let(Instant::fromEpochMilliseconds),
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds),
    scheduledPermanentDeletionAt = scheduledPermanentDeletionAt?.let(Instant::fromEpochMilliseconds),
    lockoutType = AccountLockoutType.fromValueOrThrow(lockoutType),
    temporaryLockoutUntil = temporaryLockoutUntil?.let(Instant::fromEpochMilliseconds)
)

/**
 * Builds network [UserPrivatePayload] from domain [UserPrivate].
 */
fun UserPrivate.toUserPrivatePayload(): UserPrivatePayload = UserPrivatePayload(
    id = id.asHexDashString(),
    role = role.serialName,
    accountStatus = accountStatus.serialName,
    accountStatusOnRestore = accountStatusOnRestore?.serialName,
    authorityLevel = authorityLevel,
    permissionCodes = permissionCodes.map { permissionCode ->
        permissionCode.value
    }.toSet(),
    isTotpEnabled = isTotpEnabled,
    lastLoginAt = lastLoginAt?.toEpochMilliseconds(),
    lastActiveAt = lastActiveAt?.toEpochMilliseconds(),
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt?.toEpochMilliseconds(),
    scheduledPermanentDeletionAt = scheduledPermanentDeletionAt?.toEpochMilliseconds(),
    lockoutType = lockoutType.serialName,
    temporaryLockoutUntil = temporaryLockoutUntil?.toEpochMilliseconds()
)
