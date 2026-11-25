package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.user.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.user.toUserSessionIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.user.TEST_SESSION_ID

/** Default deterministic user session ID string for unit tests. */
public const val TEST_SESSION_ID: String = "00000000-0000-4000-8000-000000000010"

/**
 * Creates a test [UserSessionId] instance for unit testing.
 *
 * @param value String representation of the UUID (defaults to [TEST_SESSION_ID]).
 */
public fun createTestUserSessionId(value: String = TEST_SESSION_ID): UserSessionId = value.toUserSessionIdOrThrow()
