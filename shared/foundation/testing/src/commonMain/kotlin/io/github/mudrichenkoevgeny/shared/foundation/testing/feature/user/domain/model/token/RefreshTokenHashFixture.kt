package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash

/**
 * Default refresh token hash string for unit testing.
 */
const val TEST_REFRESH_TOKEN_HASH = "test_refresh_token_hash"

/**
 * Creates a test [RefreshTokenHash] domain instance.
 *
 * @param value Raw refresh token hash string.
 */
fun createTestRefreshTokenHash(
    value: String = TEST_REFRESH_TOKEN_HASH
): RefreshTokenHash = RefreshTokenHash(value)
