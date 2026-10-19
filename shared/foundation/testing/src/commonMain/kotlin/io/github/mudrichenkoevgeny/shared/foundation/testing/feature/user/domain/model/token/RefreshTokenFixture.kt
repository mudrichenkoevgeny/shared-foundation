package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshToken

const val TEST_REFRESH_TOKEN = "test_refresh_token"

fun createTestRefreshToken(
    value: String = TEST_REFRESH_TOKEN
): RefreshToken = RefreshToken(value)
