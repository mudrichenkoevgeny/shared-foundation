package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.client.toClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload

/**
 * Builds wire [UserSessionPayload] from internal [UserSessionInternal].
 *
 * Security note: [UserSessionInternal.refreshTokenHash] is intentionally omitted from payload mapping.
 */
fun UserSessionInternal.toUserSessionPayload(): UserSessionPayload = UserSessionPayload(
    id = id.asHexDashString(),
    userId = userId.asHexDashString(),
    identifier = identifier,
    identifierId = identifierId.asHexDashString(),
    identifierAuthProvider = identifierAuthProvider.serialName,
    revoked = revoked,
    clientDeviceInfo = deviceInfo.toClientDeviceInfoPayload(),
    userAgent = userAgent,
    ipAddress = ipAddress,
    expiresAt = expiresAt?.toEpochMilliseconds(),
    lastAccessedAt = lastAccessedAt?.toEpochMilliseconds(),
    lastReauthenticatedAt = lastReauthenticatedAt?.toEpochMilliseconds(),
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt?.toEpochMilliseconds()
)
