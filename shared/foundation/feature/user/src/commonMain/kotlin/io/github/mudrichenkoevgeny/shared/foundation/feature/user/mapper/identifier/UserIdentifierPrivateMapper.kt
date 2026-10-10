package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierPrivatePayload
import kotlin.time.Instant

/**
 * Extension functions mapping private identifier models between domain and network representations.
 */

/**
 * Builds domain [UserIdentifierPrivate] from [UserIdentifierPrivatePayload].
 */
fun UserIdentifierPrivatePayload.toUserIdentifierPrivate(): UserIdentifierPrivate = UserIdentifierPrivate(
    id = id.toUserIdentifierIdOrThrow(),
    userId = userId.toUserIdOrThrow(),
    userAuthProvider = UserAuthProvider.fromValueOrThrow(userAuthProvider),
    identifier = identifier,
    displayName = displayName,
    externalProviderEmail = externalProviderEmail,
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds)
)

/**
 * Builds network [UserIdentifierPrivatePayload] from domain [UserIdentifierPrivate].
 */
fun UserIdentifierPrivate.toUserIdentifierPrivatePayload(): UserIdentifierPrivatePayload = UserIdentifierPrivatePayload(
    id = id.asHexDashString(),
    userId = userId.asHexDashString(),
    userAuthProvider = userAuthProvider.serialName,
    identifier = identifier,
    displayName = displayName,
    externalProviderEmail = externalProviderEmail,
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt?.toEpochMilliseconds()
)
