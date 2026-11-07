package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.passwordhash

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash.PasswordHash

/** Default deterministic password hash string for unit tests. */
public const val TEST_PASSWORD_HASH: String = "test_password_hash"

/**
 * Creates a test [PasswordHash] instance for unit testing.
 *
 * @param value Hash string (defaults to [TEST_PASSWORD_HASH]).
 */
public fun createTestPasswordHash(value: String = TEST_PASSWORD_HASH): PasswordHash = PasswordHash(value)
