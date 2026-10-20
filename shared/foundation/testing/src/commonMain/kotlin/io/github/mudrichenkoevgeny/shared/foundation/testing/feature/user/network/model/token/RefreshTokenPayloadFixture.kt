package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.RefreshTokenPayload

/**
 * Creates a test [RefreshTokenPayload] wire object instance.
 *
 * @param refreshToken Refresh token string.
 */
fun createTestRefreshTokenPayload(
    refreshToken: String = "test_refresh_token"
) = RefreshTokenPayload(
    refreshToken = refreshToken
)
