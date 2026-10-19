package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.crypt

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.crypt.DecryptedString

const val TEST_DECRYPTED_STRING = "test_decrypted_secret"

fun createTestDecryptedString(
    value: String = TEST_DECRYPTED_STRING
): DecryptedString = DecryptedString(value)
