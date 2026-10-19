package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash

const val TEST_REFRESH_TOKEN_HASH = "test_refresh_token_hash"

fun createTestRefreshTokenHash(
    value: String = TEST_REFRESH_TOKEN_HASH
): RefreshTokenHash = RefreshTokenHash(value)
