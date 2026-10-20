package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.crypt

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.DecryptedString

/**
 * Default decrypted secret string for unit testing.
 */
const val TEST_DECRYPTED_STRING = "test_decrypted_secret"

/**
 * Creates a test [DecryptedString] domain instance.
 *
 * @param value Raw decrypted secret string.
 */
fun createTestDecryptedString(
    value: String = TEST_DECRYPTED_STRING
): DecryptedString = DecryptedString(value)
