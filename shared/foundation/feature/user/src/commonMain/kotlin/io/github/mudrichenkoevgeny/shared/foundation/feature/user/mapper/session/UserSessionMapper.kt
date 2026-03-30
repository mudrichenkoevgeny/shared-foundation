package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.clienttype.ClientType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionMasked
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionUnmasked
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.toUserSessionIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionMaskedPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionUnmaskedPayload
import kotlin.time.Instant

/**
 * Builds domain [UserSessionMasked] from [UserSessionMaskedPayload].
 *
 * @throws IllegalArgumentException when the session `id`, `userId`, or `identifierId` is not a valid
 * UUID string, when `identifierAuthProvider` is unknown to [UserAuthProvider.fromValueOrNull], or when a
 * present `clientType` wire value is unknown to [ClientType.fromValueOrNull].
 */
fun UserSessionMaskedPayload.toUserSessionMasked(): UserSessionMasked =
    UserSessionMasked(
        id = id.toUserSessionIdOrThrow(),
        userId = userId.toUserIdOrThrow(),
        maskedIdentifier = maskedIdentifier,
        identifierId = identifierId.toUserIdentifierIdOrThrow(),
        identifierAuthProvider = UserAuthProvider.fromValueOrThrow(identifierAuthProvider),
        revoked = revoked,
        clientType = clientType?.let { ClientType.fromValueOrNull(clientType) },
        userAgent = userAgent,
        maskedIpAddress = maskedIpAddress,
        language = language,
        deviceId = deviceId,
        deviceName = deviceName,
        appVersion = appVersion,
        operationSystemVersion = operationSystemVersion,
        expiresAt = expiresAt?.let(Instant::fromEpochMilliseconds),
        lastAccessedAt = lastAccessedAt?.let(Instant::fromEpochMilliseconds),
        lastReauthenticatedAt = lastReauthenticatedAt?.let(Instant::fromEpochMilliseconds),
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds)
    )

/**
 * Builds network [UserSessionMaskedPayload] from domain [UserSessionMasked].
 */
fun UserSessionMasked.toUserSessionMaskedPayload(): UserSessionMaskedPayload =
    UserSessionMaskedPayload(
        id = id.asHexDashString(),
        userId = userId.asHexDashString(),
        maskedIdentifier = maskedIdentifier,
        identifierId = identifierId.asHexDashString(),
        identifierAuthProvider = identifierAuthProvider.serialName,
        revoked = revoked,
        clientType = clientType?.serialName,
        userAgent = userAgent,
        maskedIpAddress = maskedIpAddress,
        language = language,
        deviceId = deviceId,
        deviceName = deviceName,
        appVersion = appVersion,
        operationSystemVersion = operationSystemVersion,
        expiresAt = expiresAt?.toEpochMilliseconds(),
        lastAccessedAt = lastAccessedAt?.toEpochMilliseconds(),
        lastReauthenticatedAt = lastReauthenticatedAt?.toEpochMilliseconds(),
        createdAt = createdAt.toEpochMilliseconds(),
        updatedAt = updatedAt?.toEpochMilliseconds()
    )

/**
 * Builds domain [UserSessionUnmasked] from [UserSessionUnmaskedPayload].
 *
 * @throws IllegalArgumentException when the session `id`, `userId`, or `identifierId` is not a valid
 * UUID string, when `identifierAuthProvider` is unknown to [UserAuthProvider.fromValueOrNull], or when a
 * present `clientType` wire value is unknown to [ClientType.fromValueOrNull].
 */
fun UserSessionUnmaskedPayload.toUserSessionUnmasked(): UserSessionUnmasked =
    UserSessionUnmasked(
        id = id.toUserSessionIdOrThrow(),
        userId = userId.toUserIdOrThrow(),
        identifier = identifier,
        identifierId = identifierId.toUserIdentifierIdOrThrow(),
        identifierAuthProvider = UserAuthProvider.fromValueOrThrow(identifierAuthProvider),
        revoked = revoked,
        clientType = clientType?.let { ClientType.fromValueOrNull(clientType) },
        userAgent = userAgent,
        ipAddress = ipAddress,
        language = language,
        deviceId = deviceId,
        deviceName = deviceName,
        appVersion = appVersion,
        operationSystemVersion = operationSystemVersion,
        expiresAt = expiresAt?.let(Instant::fromEpochMilliseconds),
        lastAccessedAt = lastAccessedAt?.let(Instant::fromEpochMilliseconds),
        lastReauthenticatedAt = lastReauthenticatedAt?.let(Instant::fromEpochMilliseconds),
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds)
    )

/**
 * Builds network [UserSessionUnmaskedPayload] from domain [UserSessionUnmasked].
 */
fun UserSessionUnmasked.toUserSessionUnmaskedPayload(): UserSessionUnmaskedPayload =
    UserSessionUnmaskedPayload(
        id = id.asHexDashString(),
        userId = userId.asHexDashString(),
        identifier = identifier,
        identifierId = identifierId.asHexDashString(),
        identifierAuthProvider = identifierAuthProvider.serialName,
        revoked = revoked,
        clientType = clientType?.serialName,
        userAgent = userAgent,
        ipAddress = ipAddress,
        language = language,
        deviceId = deviceId,
        deviceName = deviceName,
        appVersion = appVersion,
        operationSystemVersion = operationSystemVersion,
        expiresAt = expiresAt?.toEpochMilliseconds(),
        lastAccessedAt = lastAccessedAt?.toEpochMilliseconds(),
        lastReauthenticatedAt = lastReauthenticatedAt?.toEpochMilliseconds(),
        createdAt = createdAt.toEpochMilliseconds(),
        updatedAt = updatedAt?.toEpochMilliseconds()
    )
