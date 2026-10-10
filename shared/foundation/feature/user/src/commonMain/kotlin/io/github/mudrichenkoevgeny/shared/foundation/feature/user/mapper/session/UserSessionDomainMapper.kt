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
 * Excludes `refreshTokenHash` and database row modification timestamp (`updatedAt`).
 */
fun UserSessionInternal.toUserSessionPrivate(): UserSessionPrivate = UserSessionPrivate(
    id = id,
    userId = userId,
    userRole = userRole,
    identifier = identifier,
    identifierId = identifierId,
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider,
    deviceInfo = deviceInfo,
    userAgent = userAgent,
    ipAddress = ipAddress,
    expiresAt = expiresAt,
    lastAccessedAt = lastAccessedAt,
    lastReauthenticatedAt = lastReauthenticatedAt,
    isSensitiveValuesMasked = false,
    createdAt = createdAt
)

/**
 * Converts a [UserSessionInternal] directly into a condensed [UserSessionSummary].
 *
 * Maps fields directly without allocating an intermediate [UserSessionPrivate] instance.
 */
fun UserSessionInternal.toUserSessionSummary(): UserSessionSummary = UserSessionSummary(
    id = id,
    deviceInfo = deviceInfo,
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider,
    lastAccessedAt = lastAccessedAt,
    expiresAt = expiresAt
)


/**
 * Converts a [UserSessionPrivate] into a condensed [UserSessionSummary].
 */
fun UserSessionPrivate.toUserSessionSummary(): UserSessionSummary = UserSessionSummary(
    id = id,
    deviceInfo = deviceInfo,
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider,
    lastAccessedAt = lastAccessedAt,
    expiresAt = expiresAt
)