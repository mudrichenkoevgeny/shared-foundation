package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserAuthSpec
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier.TEST_IDENTIFIER_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session.TEST_SESSION_ID

fun createTestSessionTokenPayload(
    accessToken: String = "test_access_token",
    refreshToken: String = "test_refresh_token",
    expiresAt: Long = TEST_TIME_MILLIS + 3600000L,
    tokenType: String = UserAuthSpec.TOKEN_TYPE_BEARER,
    sessionId: String = TEST_SESSION_ID,
    identifierId: String = TEST_IDENTIFIER_ID
) = SessionTokenPayload(
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiresAt = expiresAt,
    tokenType = tokenType,
    sessionId = sessionId,
    identifierId = identifierId
)
