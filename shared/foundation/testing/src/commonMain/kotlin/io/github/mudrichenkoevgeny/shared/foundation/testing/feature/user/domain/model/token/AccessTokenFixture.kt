package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.AccessToken

/**
 * Default access token string for unit testing.
 */
const val TEST_ACCESS_TOKEN = "test_access_token"

/**
 * Creates a test [AccessToken] domain instance.
 *
 * @param value Raw access token string.
 */
fun createTestAccessToken(
    value: String = TEST_ACCESS_TOKEN
): AccessToken = AccessToken(value)
