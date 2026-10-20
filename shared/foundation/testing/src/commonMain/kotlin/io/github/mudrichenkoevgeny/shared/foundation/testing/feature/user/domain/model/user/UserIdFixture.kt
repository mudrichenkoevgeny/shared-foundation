package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.toUserIdOrThrow

/** Default deterministic user ID string for unit tests. */
const val TEST_USER_ID = "00000000-0000-4000-8000-000000000001"

/** Secondary deterministic user ID string for multi-user unit tests. */
const val TEST_USER_ID_2 = "00000000-0000-4000-8000-000000000002"

/**
 * Creates a test [UserId] instance for unit testing.
 *
 * @param value String representation of the UUID (defaults to [TEST_USER_ID]).
 */
fun createTestUserId(
    value: String = TEST_USER_ID
): UserId = value.toUserIdOrThrow()
