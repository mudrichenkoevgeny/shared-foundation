package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifier
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPayload
import kotlin.time.Instant

/**
 * Maps between [UserIdentifierPayload] and domain [UserIdentifier].
 */

/**
 * Builds domain [UserIdentifier] from [UserIdentifierPayload].
 *
 * @throws IllegalArgumentException when `id` or `userId` is not a valid UUID string, or when `userAuthProvider` is
 * unknown to [UserAuthProvider.fromValueOrNull].
 */
fun UserIdentifierPayload.toUserIdentifier(): UserIdentifier = UserIdentifier(
    id = id.toUserIdentifierIdOrThrow(),
    userId = userId.toUserIdOrThrow(),
    userAuthProvider = UserAuthProvider.fromValueOrThrow(userAuthProvider),
    identifier = identifier,
    externalProviderEmail = externalProviderEmail,
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds)
)

/**
 * Builds network [UserIdentifierPayload] from domain [UserIdentifier].
 */
fun UserIdentifier.toUserIdentifierPayload(): UserIdentifierPayload = UserIdentifierPayload(
    id = id.asHexDashString(),
    userId = userId.asHexDashString(),
    userAuthProvider = userAuthProvider.serialName,
    identifier = identifier,
    externalProviderEmail = externalProviderEmail,
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt?.toEpochMilliseconds()
)

fun UserIdentifierInternal.toUserIdentifier(
    isSensitiveValuesMasked: Boolean = false
) = UserIdentifier(
    id = id,
    userId = userId,
    userAuthProvider = userAuthProvider,
    identifier = identifier,
    externalProviderEmail = externalProviderEmail,
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = createdAt,
    updatedAt = updatedAt
)