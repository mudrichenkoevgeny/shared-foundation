package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.DeletedSessions
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId

fun createTestDeletedSessions(
    deletedSessionIds: List<UserSessionId> = listOf(createTestUserSessionId())
) = DeletedSessions(
    deletedSessionIds = deletedSessionIds
)
