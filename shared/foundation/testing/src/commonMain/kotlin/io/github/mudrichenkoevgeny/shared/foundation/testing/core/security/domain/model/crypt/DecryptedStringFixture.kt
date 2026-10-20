package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.crypt

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.DecryptedString

/** Default deterministic decrypted string for unit tests. */
const val TEST_DECRYPTED_STRING = "test_decrypted_secret"

/**
 * Creates a test [DecryptedString] instance for unit testing.
 *
 * @param value Decrypted string value (defaults to [TEST_DECRYPTED_STRING]).
 */
fun createTestDecryptedString(
    value: String = TEST_DECRYPTED_STRING
): DecryptedString = DecryptedString(value)
