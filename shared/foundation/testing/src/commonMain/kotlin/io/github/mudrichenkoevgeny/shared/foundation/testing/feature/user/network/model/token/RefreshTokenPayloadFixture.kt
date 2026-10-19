package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.RefreshTokenPayload

fun createTestRefreshTokenPayload(
    refreshToken: String = "test_refresh_token"
) = RefreshTokenPayload(
    refreshToken = refreshToken
)
