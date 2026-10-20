package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.passwordhash

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash.PasswordHash

/**
 * Default password hash string for unit testing.
 */
const val TEST_PASSWORD_HASH = "test_password_hash"

/**
 * Creates a test [PasswordHash] domain instance.
 *
 * @param value Password hash string.
 */
fun createTestPasswordHash(
    value: String = TEST_PASSWORD_HASH
): PasswordHash = PasswordHash(value)
