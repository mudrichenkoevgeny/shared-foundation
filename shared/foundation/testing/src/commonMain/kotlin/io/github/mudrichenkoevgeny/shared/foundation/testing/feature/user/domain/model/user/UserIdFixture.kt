package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow

/**
 * Default user ID string for unit testing.
 */
const val TEST_USER_ID = "00000000-0000-0000-0000-000000000001"

/**
 * Secondary default user ID string for unit testing.
 */
const val TEST_USER_ID_2 = "00000000-0000-0000-0000-000000000002"

/**
 * Creates a test [UserId] domain instance.
 *
 * @param value Raw user ID string.
 */
fun createTestUserId(
    value: String = TEST_USER_ID
): UserId = value.toUserIdOrThrow()
