package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.client.createTestClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.createTestUserId
import kotlin.time.Instant

fun createTestUserSessionSummary(
    id: UserSessionId = createTestUserSessionId(),
    userId: UserId = createTestUserId(),
    clientDeviceInfo: ClientDeviceInfo = createTestClientDeviceInfo(),
    identifierDisplayName: String = "Test User",
    identifierAuthProvider: UserAuthProvider = UserAuthProvider.EMAIL,
    lastAccessedAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    expiresAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS + 86400000L)
) = UserSessionSummary(
    id = id,
    userId = userId,
    clientDeviceInfo = clientDeviceInfo,
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider,
    lastAccessedAt = lastAccessedAt,
    expiresAt = expiresAt
)
