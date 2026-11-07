package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.AccessToken

/** Default deterministic access token string for unit tests. */
public const val TEST_ACCESS_TOKEN: String = "test_access_token"

/**
 * Creates a test [AccessToken] instance for unit testing.
 *
 * @param value Token string (defaults to [TEST_ACCESS_TOKEN]).
 */
public fun createTestAccessToken(value: String = TEST_ACCESS_TOKEN): AccessToken = AccessToken(value)
