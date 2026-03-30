package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.identifier

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierMasked
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierUnmasked
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierMaskedPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.identifier.UserIdentifierUnmaskedPayload
import kotlin.time.Instant

/**
 * Maps identifier list DTOs to domain [UserIdentifierMasked] / [UserIdentifierUnmasked].
 */

/**
 * Builds domain [UserIdentifierMasked] from [UserIdentifierMaskedPayload].
 *
 * @throws IllegalArgumentException when `id` or `userId` is not a valid UUID string, or when
 * `userAuthProvider` is unknown to [UserAuthProvider.fromValueOrNull].
 */
fun UserIdentifierMaskedPayload.toUserIdentifierMasked(): UserIdentifierMasked =
    UserIdentifierMasked(
        id = id.toUserIdentifierIdOrThrow(),
        userId = userId.toUserIdOrThrow(),
        userAuthProvider = UserAuthProvider.fromValueOrThrow(userAuthProvider),
        maskedIdentifier = maskedIdentifier,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds)
    )

/**
 * Builds network [UserIdentifierMaskedPayload] from domain [UserIdentifierMasked].
 */
fun UserIdentifierMasked.toUserIdentifierMaskedPayload(): UserIdentifierMaskedPayload =
    UserIdentifierMaskedPayload(
        id = id.asHexDashString(),
        userId = userId.asHexDashString(),
        userAuthProvider = userAuthProvider.serialName,
        maskedIdentifier = maskedIdentifier,
        createdAt = createdAt.toEpochMilliseconds(),
        updatedAt = updatedAt?.toEpochMilliseconds()
    )

/**
 * Builds domain [UserIdentifierUnmasked] from [UserIdentifierUnmaskedPayload].
 *
 * @throws IllegalArgumentException when `id` or `userId` is not a valid UUID string, or when
 * `userAuthProvider` is unknown to [UserAuthProvider.fromValueOrNull].
 */
fun UserIdentifierUnmaskedPayload.toUserIdentifierUnmasked(): UserIdentifierUnmasked =
    UserIdentifierUnmasked(
        id = id.toUserIdentifierIdOrThrow(),
        userId = userId.toUserIdOrThrow(),
        userAuthProvider = UserAuthProvider.fromValueOrThrow(userAuthProvider),
        identifier = identifier,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds)
    )

/**
 * Builds network [UserIdentifierUnmaskedPayload] from domain [UserIdentifierUnmasked].
 */
fun UserIdentifierUnmasked.toUserIdentifierUnmaskedPayload(): UserIdentifierUnmaskedPayload =
    UserIdentifierUnmaskedPayload(
        id = id.asHexDashString(),
        userId = userId.asHexDashString(),
        userAuthProvider = userAuthProvider.serialName,
        identifier = identifier,
        createdAt = createdAt.toEpochMilliseconds(),
        updatedAt = updatedAt?.toEpochMilliseconds()
    )
