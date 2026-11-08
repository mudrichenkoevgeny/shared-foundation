package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary

/**
 * Converts a [UserIdentifierInternal] into a client-facing [UserIdentifierPrivate].
 *
 * Excludes `passwordHash`.
 */
public fun UserIdentifierInternal.toUserIdentifierPrivate(
    isSensitiveValuesMasked: Boolean = false
): UserIdentifierPrivate = UserIdentifierPrivate(
    id = id,
    userId = userId,
    userAuthProvider = userAuthProvider,
    identifier = identifier,
    displayName = displayName,
    externalProviderEmail = externalProviderEmail,
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/**
 * Converts a [UserIdentifierPrivate] into a condensed [UserIdentifierSummary].
 */
public fun UserIdentifierPrivate.toUserIdentifierSummary(): UserIdentifierSummary = UserIdentifierSummary(
    id = id,
    userId = userId,
    userAuthProvider = userAuthProvider,
    identifier = identifier,
    displayName = displayName
)

/**
 * Converts a [UserIdentifierInternal] directly into a condensed [UserIdentifierSummary].
 *
 * Maps fields directly without allocating an intermediate [UserIdentifierPrivate] instance.
 */
public fun UserIdentifierInternal.toUserIdentifierSummary(): UserIdentifierSummary = UserIdentifierSummary(
    id = id,
    userId = userId,
    userAuthProvider = userAuthProvider,
    identifier = identifier,
    displayName = displayName
)
