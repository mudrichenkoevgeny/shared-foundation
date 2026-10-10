package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.client.toClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.mapper.client.toClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.toUserSessionIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPrivatePayload
import kotlin.time.Instant

/**
 * Extension functions mapping private session models between domain and network representations.
 */

/**
 * Builds domain [UserSessionPrivate] from [UserSessionPrivatePayload].
 */
fun UserSessionPrivatePayload.toUserSessionPrivate(): UserSessionPrivate = UserSessionPrivate(
    id = id.toUserSessionIdOrThrow(),
    userId = userId.toUserIdOrThrow(),
    userRole = UserRole.fromValueOrThrow(userRole),
    identifier = identifier,
    identifierId = identifierId.toUserIdentifierIdOrThrow(),
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = UserAuthProvider.fromValueOrThrow(identifierAuthProvider),
    clientDeviceInfo = clientDeviceInfo.toClientDeviceInfo(),
    userAgent = userAgent,
    ipAddress = ipAddress,
    expiresAt = Instant.fromEpochMilliseconds(expiresAt),
    lastAccessedAt = Instant.fromEpochMilliseconds(lastAccessedAt),
    lastReauthenticatedAt = Instant.fromEpochMilliseconds(lastReauthenticatedAt),
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds)
)

/**
 * Builds network [UserSessionPrivatePayload] from domain [UserSessionPrivate].
 */
fun UserSessionPrivate.toUserSessionPrivatePayload(): UserSessionPrivatePayload = UserSessionPrivatePayload(
    id = id.asHexDashString(),
    userId = userId.asHexDashString(),
    userRole = userRole.serialName,
    identifier = identifier,
    identifierId = identifierId.asHexDashString(),
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider.serialName,
    clientDeviceInfo = clientDeviceInfo.toClientDeviceInfoPayload(),
    userAgent = userAgent,
    ipAddress = ipAddress,
    expiresAt = expiresAt.toEpochMilliseconds(),
    lastAccessedAt = lastAccessedAt.toEpochMilliseconds(),
    lastReauthenticatedAt = lastReauthenticatedAt.toEpochMilliseconds(),
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt?.toEpochMilliseconds()
)
