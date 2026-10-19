package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.AccessToken

const val TEST_ACCESS_TOKEN = "test_access_token"

fun createTestAccessToken(
    value: String = TEST_ACCESS_TOKEN
): AccessToken = AccessToken(value)
