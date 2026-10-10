package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary

/**
 * Extension functions mapping between domain session representations.
 */

/**
 * Converts a [UserSessionInternal] into a client-facing [UserSessionPrivate].
 *
 * Excludes `refreshTokenHash`.
 */
fun UserSessionInternal.toUserSessionPrivate(
    isSensitiveValuesMasked: Boolean = false
): UserSessionPrivate = UserSessionPrivate(
    id = id,
    userId = userId,
    userRole = userRole,
    identifier = identifier,
    identifierId = identifierId,
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider,
    clientDeviceInfo = clientDeviceInfo,
    userAgent = userAgent,
    ipAddress = ipAddress,
    expiresAt = expiresAt,
    lastAccessedAt = lastAccessedAt,
    lastReauthenticatedAt = lastReauthenticatedAt,
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/**
 * Converts a [UserSessionPrivate] into a condensed [UserSessionSummary].
 */
fun UserSessionPrivate.toUserSessionSummary(): UserSessionSummary = UserSessionSummary(
    id = id,
    clientDeviceInfo = clientDeviceInfo,
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider,
    lastAccessedAt = lastAccessedAt,
    expiresAt = expiresAt
)

/**
 * Converts a [UserSessionInternal] directly into a condensed [UserSessionSummary].
 *
 * Maps fields directly without allocating an intermediate [UserSessionPrivate] instance.
 */
fun UserSessionInternal.toUserSessionSummary(): UserSessionSummary = UserSessionSummary(
    id = id,
    clientDeviceInfo = clientDeviceInfo,
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider,
    lastAccessedAt = lastAccessedAt,
    expiresAt = expiresAt
)
