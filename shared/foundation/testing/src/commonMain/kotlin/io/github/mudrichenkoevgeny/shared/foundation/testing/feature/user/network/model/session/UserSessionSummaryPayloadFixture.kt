package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.network.model.client.createTestClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session.TEST_SESSION_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

fun createTestUserSessionSummaryPayload(
    id: String = TEST_SESSION_ID,
    userId: String = TEST_USER_ID,
    clientDeviceInfo: ClientDeviceInfoPayload = createTestClientDeviceInfoPayload(),
    identifierDisplayName: String = "Test User",
    identifierAuthProvider: String = UserAuthProvider.EMAIL.value,
    lastAccessedAt: Long = TEST_TIME_MILLIS,
    expiresAt: Long = TEST_TIME_MILLIS + 86400000L
) = UserSessionSummaryPayload(
    id = id,
    userId = userId,
    clientDeviceInfo = clientDeviceInfo,
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider,
    lastAccessedAt = lastAccessedAt,
    expiresAt = expiresAt
)
