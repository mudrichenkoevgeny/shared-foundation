package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.client.toClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.client.toClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSession
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.toUserSessionIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload
import kotlin.time.Instant

/**
 * Maps between [UserSessionPayload] and domain [UserSession].
 */

/**
 * Builds domain [UserSession] from [UserSessionPayload].
 *
 * @throws IllegalArgumentException when the session `id`, `userId`, or `identifierId` is not a valid UUID string, when
 * `identifierAuthProvider` is unknown to [UserAuthProvider.fromValueOrNull].
 */
fun UserSessionPayload.toUserSession(): UserSession = UserSession(
    id = id.toUserSessionIdOrThrow(),
    userId = userId.toUserIdOrThrow(),
    identifier = identifier,
    identifierId = identifierId.toUserIdentifierIdOrThrow(),
    identifierAuthProvider = UserAuthProvider.fromValueOrThrow(identifierAuthProvider),
    deviceInfo = clientDeviceInfo.toClientDeviceInfo(),
    userAgent = userAgent,
    ipAddress = ipAddress,
    expiresAt = expiresAt?.let(Instant::fromEpochMilliseconds),
    lastAccessedAt = lastAccessedAt?.let(Instant::fromEpochMilliseconds),
    lastReauthenticatedAt = lastReauthenticatedAt?.let(Instant::fromEpochMilliseconds),
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds)
)

/**
 * Builds network [UserSessionPayload] from domain [UserSession].
 */
fun UserSession.toUserSessionPayload(): UserSessionPayload = UserSessionPayload(
    id = id.asHexDashString(),
    userId = userId.asHexDashString(),
    identifier = identifier,
    identifierId = identifierId.asHexDashString(),
    identifierAuthProvider = identifierAuthProvider.serialName,
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
