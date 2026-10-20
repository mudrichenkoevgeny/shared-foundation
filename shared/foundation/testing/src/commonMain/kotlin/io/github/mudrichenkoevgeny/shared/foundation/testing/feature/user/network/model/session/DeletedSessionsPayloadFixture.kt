package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.DeletedSessionsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session.TEST_SESSION_ID

/**
 * Creates a test [DeletedSessionsPayload] wire object instance.
 *
 * @param deletedSessionIds List of deleted session ID strings.
 */
fun createTestDeletedSessionsPayload(
    deletedSessionIds: List<String> = listOf(TEST_SESSION_ID)
) = DeletedSessionsPayload(
    deletedSessionIds = deletedSessionIds
)
