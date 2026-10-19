package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.DeletedSessionsPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session.TEST_SESSION_ID

fun createTestDeletedSessionsPayload(
    deletedSessionIds: List<String> = listOf(TEST_SESSION_ID)
) = DeletedSessionsPayload(
    deletedSessionIds = deletedSessionIds
)
