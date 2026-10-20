package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshToken

/**
 * Default refresh token string for unit testing.
 */
const val TEST_REFRESH_TOKEN = "test_refresh_token"

/**
 * Creates a test [RefreshToken] domain instance.
 *
 * @param value Raw refresh token string.
 */
fun createTestRefreshToken(
    value: String = TEST_REFRESH_TOKEN
): RefreshToken = RefreshToken(value)
