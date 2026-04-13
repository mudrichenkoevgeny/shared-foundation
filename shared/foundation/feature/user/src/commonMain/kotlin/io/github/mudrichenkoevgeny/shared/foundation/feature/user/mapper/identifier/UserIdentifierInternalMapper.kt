package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload

/**
 * Builds wire [UserIdentifierPayload] from internal [UserIdentifierInternal].
 *
 * Security note: [UserIdentifierInternal.passwordHash] is intentionally omitted from payload mapping.
 */
fun UserIdentifierInternal.toUserIdentifierPayload(): UserIdentifierPayload = UserIdentifierPayload(
    id = id.asHexDashString(),
    userId = userId.asHexDashString(),
    userAuthProvider = userAuthProvider.serialName,
    identifier = identifier,
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt?.toEpochMilliseconds()
)
