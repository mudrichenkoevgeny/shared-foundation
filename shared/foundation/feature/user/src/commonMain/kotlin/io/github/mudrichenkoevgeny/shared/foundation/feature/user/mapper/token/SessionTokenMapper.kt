package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.AccessToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import kotlin.time.Instant

/**
 * Maps between wire [SessionTokenPayload] and domain [SessionToken].
 *
 * [SessionTokenPayload.expiresAt] is epoch milliseconds on the wire and maps to [SessionToken.expiresAt]
 * via [Instant.fromEpochMilliseconds]. [AccessToken] and [RefreshToken] wrap the raw access and
 * refresh strings from the payload.
 */

/**
 * Builds domain [SessionToken] from [SessionTokenPayload].
 */
fun SessionTokenPayload.toSessionToken(): SessionToken =
    SessionToken(
        accessToken = AccessToken(accessToken),
        refreshToken = RefreshToken(refreshToken),
        expiresAt = Instant.fromEpochMilliseconds(expiresAt),
        tokenType = tokenType
    )

/**
 * Builds network [SessionTokenPayload] from domain [SessionToken].
 */
fun SessionToken.toSessionTokenPayload(): SessionTokenPayload =
    SessionTokenPayload(
        accessToken = accessToken.value,
        refreshToken = refreshToken.value,
        expiresAt = expiresAt.toEpochMilliseconds(),
        tokenType = tokenType
    )
