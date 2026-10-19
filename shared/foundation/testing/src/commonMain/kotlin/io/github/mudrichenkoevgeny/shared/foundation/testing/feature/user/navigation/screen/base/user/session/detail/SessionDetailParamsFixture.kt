package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.navigation.screen.base.user.session.detail

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.detail.SessionDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session.TEST_SESSION_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

fun createTestSessionDetailParams(
    sessionId: String = TEST_SESSION_ID,
    userId: String? = TEST_USER_ID
): SessionDetailParams = SessionDetailParams(
    sessionId = sessionId,
    userId = userId
)
