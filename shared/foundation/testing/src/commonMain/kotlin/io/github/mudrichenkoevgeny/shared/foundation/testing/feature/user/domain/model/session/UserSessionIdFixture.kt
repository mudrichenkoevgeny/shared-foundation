package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.toUserSessionIdOrThrow

/**
 * Default user session ID string for unit testing.
 */
const val TEST_SESSION_ID = "00000000-0000-0000-0000-000000000010"

/**
 * Creates a test [UserSessionId] domain instance.
 *
 * @param value Raw session ID string.
 */
fun createTestUserSessionId(
    value: String = TEST_SESSION_ID
): UserSessionId = value.toUserSessionIdOrThrow()
