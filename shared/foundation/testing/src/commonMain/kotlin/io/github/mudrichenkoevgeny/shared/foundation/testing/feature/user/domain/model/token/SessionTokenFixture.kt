package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.AccessToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.SessionToken
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserAuthSpec
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier.createTestUserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session.createTestUserSessionId
import kotlin.time.Instant

fun createTestSessionToken(
    accessToken: AccessToken = createTestAccessToken(),
    refreshToken: RefreshToken = createTestRefreshToken(),
    expiresAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS + 3600000L),
    tokenType: String = UserAuthSpec.TOKEN_TYPE_BEARER,
    sessionId: UserSessionId = createTestUserSessionId(),
    identifierId: UserIdentifierId = createTestUserIdentifierId()
) = SessionToken(
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiresAt = expiresAt,
    tokenType = tokenType,
    sessionId = sessionId,
    identifierId = identifierId
)
