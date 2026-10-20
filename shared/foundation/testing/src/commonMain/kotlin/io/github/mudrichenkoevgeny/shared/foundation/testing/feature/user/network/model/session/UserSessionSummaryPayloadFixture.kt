package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.generateTestUuidString
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.network.model.client.createTestClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session.TEST_SESSION_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [UserSessionSummaryPayload] wire object for unit testing mapper logic.
 *
 * @param id Raw session ID string.
 * @param userId Raw user ID string.
 * @param clientDeviceInfo Hardware device info wire payload ([ClientDeviceInfoPayload]).
 * @param identifierDisplayName Display name string.
 * @param identifierAuthProvider Wire string representation of [UserAuthProvider].
 * @param lastAccessedAt Last activity epoch-millisecond timestamp.
 * @param expiresAt Expiration epoch-millisecond timestamp.
 */
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

/**
 * Creates a list of test [UserSessionSummaryPayload] wire objects with deterministic raw UUIDs,
 * alternating devices, and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserSessionSummaryPayloadList(
    count: Int = 2,
    generator: (index: Int) -> UserSessionSummaryPayload = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        val isEven = index % 2 == 0
        createTestUserSessionSummaryPayload(
            id = generateTestUuidString(index),
            userId = generateTestUuidString(index),
            identifierAuthProvider = if (isEven) UserAuthProvider.EMAIL.value else UserAuthProvider.GOOGLE.value,
            identifierDisplayName = if (isEven) "Email Identifier" else "Google Account",
            clientDeviceInfo = createTestClientDeviceInfoPayload(
                deviceName = if (isEven) "Windows PC" else "MacBook Pro",
                operationSystemVersion = if (isEven) "Windows 10" else "macOS 14.0"
            ),
            lastAccessedAt = createdAtMs + (index * 5_000L),
            expiresAt = createdAtMs + 86_400_000L
        )
    }
): List<UserSessionSummaryPayload> = createTestList(count, generator)
