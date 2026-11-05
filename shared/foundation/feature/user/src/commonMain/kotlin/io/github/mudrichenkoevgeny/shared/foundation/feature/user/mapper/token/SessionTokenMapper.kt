package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.toUserIdentifierIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.toUserSessionIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.AccessToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import kotlin.time.Instant

/**
 * Builds domain [SessionToken] from [SessionTokenPayload].
 *
 * @throws IllegalArgumentException when `sessionId` or `identifierId` is not a valid UUID string.
 */
public fun SessionTokenPayload.toSessionToken(): SessionToken =
    SessionToken(
        accessToken = AccessToken(accessToken),
        refreshToken = RefreshToken(refreshToken),
        expiresAt = Instant.fromEpochMilliseconds(expiresAt),
        tokenType = tokenType,
        sessionId = sessionId.toUserSessionIdOrThrow(),
        identifierId = identifierId.toUserIdentifierIdOrThrow()
    )

/**
 * Builds network [SessionTokenPayload] from domain [SessionToken].
 */
public fun SessionToken.toSessionTokenPayload(): SessionTokenPayload =
    SessionTokenPayload(
        accessToken = accessToken.value,
        refreshToken = refreshToken.value,
        expiresAt = expiresAt.toEpochMilliseconds(),
        tokenType = tokenType,
        sessionId = sessionId.asHexDashString(),
        identifierId = identifierId.asHexDashString()
    )
