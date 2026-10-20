package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.toUserSessionIdOrThrow

/** Default deterministic user session ID string for unit tests. */
const val TEST_SESSION_ID = "00000000-0000-4000-8000-000000000010"

/**
 * Creates a test [UserSessionId] instance for unit testing.
 *
 * @param value String representation of the UUID (defaults to [TEST_SESSION_ID]).
 */
fun createTestUserSessionId(
    value: String = TEST_SESSION_ID
): UserSessionId = value.toUserSessionIdOrThrow()
