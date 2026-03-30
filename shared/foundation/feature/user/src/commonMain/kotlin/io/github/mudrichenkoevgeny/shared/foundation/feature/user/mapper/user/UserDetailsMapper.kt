package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserDetails
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload
import kotlin.time.Instant

/**
 * Maps between [UserDetailsPayload] and domain [UserDetails].
 *
 * Role and account status strings use [UserRole.fromValueOrNull] / [UserAccountStatus.fromValueOrNull]; reverse
 * mapping uses [UserRole.serialName] / [UserAccountStatus.serialName]. Epoch millis fields use
 * [Instant.fromEpochMilliseconds] / [Instant.toEpochMilliseconds].
 */

/**
 * Builds domain [UserDetails] from [UserDetailsPayload].
 *
 * @throws IllegalArgumentException when `id` is not a valid user UUID string, or when a wire value
 * for `role`, `accountStatus`, or `accountStatusBeforeDeletion` is not recognized by
 * [UserRole.fromValueOrNull] or [UserAccountStatus.fromValueOrNull].
 */
fun UserDetailsPayload.toUserDetails() = UserDetails(
    id = id.toUserIdOrThrow(),
    role = UserRole.fromValueOrThrow(role),
    accountStatus = UserAccountStatus.fromValueOrThrow(accountStatus),
    accountStatusBeforeDeletion = UserAccountStatus.fromValueOrThrow(accountStatusBeforeDeletion),
    permissions = permissions,
    lastLoginAt = lastLoginAt?.let(Instant::fromEpochMilliseconds),
    lastActiveAt = lastActiveAt?.let(Instant::fromEpochMilliseconds),
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds),
    scheduledPermanentDeletionAt = scheduledPermanentDeletionAt?.let(Instant::fromEpochMilliseconds)
)

/**
 * Builds network [UserDetailsPayload] from domain [UserDetails].
 */
fun UserDetails.toUserDetailsPayload() = UserDetailsPayload(
    id = id.asHexDashString(),
    role = role.serialName,
    accountStatus = accountStatus.serialName,
    accountStatusBeforeDeletion = accountStatusBeforeDeletion.serialName,
    permissions = permissions,
    lastLoginAt = lastLoginAt?.toEpochMilliseconds(),
    lastActiveAt = lastActiveAt?.toEpochMilliseconds(),
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt?.toEpochMilliseconds(),
    scheduledPermanentDeletionAt = scheduledPermanentDeletionAt?.toEpochMilliseconds()
)
