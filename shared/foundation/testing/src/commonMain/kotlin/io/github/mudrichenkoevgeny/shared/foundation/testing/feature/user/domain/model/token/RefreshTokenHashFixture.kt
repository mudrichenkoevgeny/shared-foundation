package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash

/** Default deterministic refresh token hash string for unit tests. */
const val TEST_REFRESH_TOKEN_HASH = "test_refresh_token_hash"

/**
 * Creates a test [RefreshTokenHash] instance for unit testing.
 *
 * @param value Hash string (defaults to [TEST_REFRESH_TOKEN_HASH]).
 */
fun createTestRefreshTokenHash(
    value: String = TEST_REFRESH_TOKEN_HASH
): RefreshTokenHash = RefreshTokenHash(value)
