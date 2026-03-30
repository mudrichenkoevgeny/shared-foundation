package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserPublic
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPublicPayload

/**
 * Maps between [UserPublicPayload] and domain [UserPublic].
 *
 * Wire strings map through [UserRole.fromValueOrNull] / [UserAccountStatus.fromValueOrNull]; payloads use
 * [UserRole.serialName] / [UserAccountStatus.serialName] on the way out.
 */

/**
 * Builds domain [UserPublic] from [UserPublicPayload].
 *
 * @throws IllegalArgumentException when `id` is not a valid user UUID string, or when a wire value
 * for `role` or `accountStatus` is not recognized by [UserRole.fromValueOrNull] or [UserAccountStatus.fromValueOrNull].
 */
fun UserPublicPayload.toUserPublic() = UserPublic(
    id = id.toUserIdOrThrow(),
    role = UserRole.fromValueOrThrow(role),
    accountStatus = UserAccountStatus.fromValueOrThrow(accountStatus)
)

/**
 * Builds network [UserPublicPayload] from domain [UserPublic].
 */
fun UserPublic.toUserPublicPayload() = UserPublicPayload(
    id = id.asHexDashString(),
    role = role.serialName,
    accountStatus = accountStatus.serialName
)