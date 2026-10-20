package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.crypt

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.EncryptedString

/**
 * Default encrypted secret string for unit testing.
 */
const val TEST_ENCRYPTED_STRING = "test_encrypted_secret"

/**
 * Creates a test [EncryptedString] domain instance.
 *
 * @param value Raw encrypted secret string.
 */
fun createTestEncryptedString(
    value: String = TEST_ENCRYPTED_STRING
): EncryptedString = EncryptedString(value)
