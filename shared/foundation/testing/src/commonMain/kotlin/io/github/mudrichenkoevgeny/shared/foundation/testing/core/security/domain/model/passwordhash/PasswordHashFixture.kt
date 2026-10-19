package io.github.mudrichenkoevgeny.shared.foundation.testing.core.security.domain.model.passwordhash

import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.passwordhash.PasswordHash

const val TEST_PASSWORD_HASH = "test_password_hash"

fun createTestPasswordHash(
    value: String = TEST_PASSWORD_HASH
): PasswordHash = PasswordHash(value)
