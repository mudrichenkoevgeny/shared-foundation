package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.client.createTestClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.generateTestUuidString
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.createTestUserId
import kotlin.time.Instant

/**
 * Creates a test [UserSessionSummary] instance with default mock properties for unit testing.
 *
 * @param id Unique session ID ([UserSessionId]).
 * @param userId Owning user ID ([UserId]).
 * @param clientDeviceInfo Hardware and device info ([ClientDeviceInfo]).
 * @param identifierDisplayName Identifier display name.
 * @param identifierAuthProvider Auth provider category ([UserAuthProvider]).
 * @param lastAccessedAt Timestamp of last activity.
 * @param expiresAt Expiration timestamp.
 */
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

/**
 * Creates a list of test [UserSessionSummary] instances with deterministic index-based UUIDs,
 * alternating devices, and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserSessionSummaryList(
    count: Int = 2,
    generator: (index: Int) -> UserSessionSummary = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        val isEven = index % 2 == 0
        val winDevice = createTestClientDeviceInfo(
            deviceName = "Windows PC",
            operationSystemVersion = "Windows 10"
        )
        val macDevice = createTestClientDeviceInfo(
            deviceName = "MacBook Pro",
            operationSystemVersion = "macOS 14.0"
        )
        createTestUserSessionSummary(
            id = createTestUserSessionId(generateTestUuidString(index = index, group = 1)),
            userId = createTestUserId(generateTestUuidString(index = index, group = 2)),
            identifierAuthProvider = if (isEven) UserAuthProvider.EMAIL else UserAuthProvider.GOOGLE,
            identifierDisplayName = if (isEven) "Email Identifier" else "Google Account",
            clientDeviceInfo = if (isEven) winDevice else macDevice,
            lastAccessedAt = Instant.fromEpochMilliseconds(createdAtMs + (index * 5_000L)),
            expiresAt = Instant.fromEpochMilliseconds(createdAtMs + 86_400_000L)
        )
    }
): List<UserSessionSummary> = createTestList(count, generator)
