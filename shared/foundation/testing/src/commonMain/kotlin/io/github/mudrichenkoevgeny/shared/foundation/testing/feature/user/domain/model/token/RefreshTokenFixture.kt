package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshToken

/** Default deterministic refresh token string for unit tests. */
const val TEST_REFRESH_TOKEN = "test_refresh_token"

/**
 * Creates a test [RefreshToken] instance for unit testing.
 *
 * @param value Token string (defaults to [TEST_REFRESH_TOKEN]).
 */
fun createTestRefreshToken(
    value: String = TEST_REFRESH_TOKEN
): RefreshToken = RefreshToken(value)
