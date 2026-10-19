package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.crypt

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.EncryptedString

const val TEST_ENCRYPTED_STRING = "test_encrypted_secret"

fun createTestEncryptedString(
    value: String = TEST_ENCRYPTED_STRING
): EncryptedString = EncryptedString(value)
