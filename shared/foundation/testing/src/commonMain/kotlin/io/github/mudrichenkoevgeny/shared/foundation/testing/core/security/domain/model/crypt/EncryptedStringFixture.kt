package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.crypt

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.EncryptedString

/** Default deterministic encrypted string for unit tests. */
const val TEST_ENCRYPTED_STRING = "test_encrypted_secret"

/**
 * Creates a test [EncryptedString] instance for unit testing.
 *
 * @param value Encrypted string value (defaults to [TEST_ENCRYPTED_STRING]).
 */
fun createTestEncryptedString(
    value: String = TEST_ENCRYPTED_STRING
): EncryptedString = EncryptedString(value)
