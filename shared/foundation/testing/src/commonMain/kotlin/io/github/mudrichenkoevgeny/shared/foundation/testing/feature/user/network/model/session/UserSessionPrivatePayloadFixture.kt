package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.network.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.client.ClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_IP_ADDRESS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_USER_AGENT
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.generateTestUuidString
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.network.model.client.createTestClientDeviceInfoPayload
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier.TEST_IDENTIFIER_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session.TEST_SESSION_ID
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.TEST_USER_ID

/**
 * Creates a test [UserSessionPrivatePayload] wire object for unit testing mapper logic.
 *
 * @param id Raw session ID string.
 * @param userId Raw user ID string.
 * @param userRole Wire string representation of [UserRole].
 * @param identifier Raw credential string.
 * @param identifierId Raw credential ID string.
 * @param identifierDisplayName Display name string.
 * @param identifierAuthProvider Wire string representation of [UserAuthProvider].
 * @param clientDeviceInfo Hardware device info wire payload ([ClientDeviceInfoPayload]).
 * @param userAgent HTTP User-Agent header string.
 * @param ipAddress Client IP address.
 * @param expiresAt Expiration epoch-millisecond timestamp.
 * @param lastAccessedAt Last activity epoch-millisecond timestamp.
 * @param lastReauthenticatedAt Last re-authentication epoch-millisecond timestamp.
 * @param isSensitiveValuesMasked Whether sensitive values are masked.
 * @param createdAt Creation epoch-millisecond timestamp.
 * @param updatedAt Modification epoch-millisecond timestamp.
 */
fun createTestUserSessionPrivatePayload(
    id: String = TEST_SESSION_ID,
    userId: String = TEST_USER_ID,
    userRole: String = UserRole.USER.value,
    identifier: String = TEST_EMAIL,
    identifierId: String = TEST_IDENTIFIER_ID,
    identifierDisplayName: String = "Test User",
    identifierAuthProvider: String = UserAuthProvider.EMAIL.value,
    clientDeviceInfo: ClientDeviceInfoPayload = createTestClientDeviceInfoPayload(),
    userAgent: String? = TEST_USER_AGENT,
    ipAddress: String? = TEST_IP_ADDRESS,
    expiresAt: Long = TEST_TIME_MILLIS + 86400000L,
    lastAccessedAt: Long = TEST_TIME_MILLIS,
    lastReauthenticatedAt: Long = TEST_TIME_MILLIS,
    isSensitiveValuesMasked: Boolean = false,
    createdAt: Long = TEST_TIME_MILLIS,
    updatedAt: Long? = TEST_TIME_MILLIS
) = UserSessionPrivatePayload(
    id = id,
    userId = userId,
    userRole = userRole,
    identifier = identifier,
    identifierId = identifierId,
    identifierDisplayName = identifierDisplayName,
    identifierAuthProvider = identifierAuthProvider,
    clientDeviceInfo = clientDeviceInfo,
    userAgent = userAgent,
    ipAddress = ipAddress,
    expiresAt = expiresAt,
    lastAccessedAt = lastAccessedAt,
    lastReauthenticatedAt = lastReauthenticatedAt,
    isSensitiveValuesMasked = isSensitiveValuesMasked,
    createdAt = createdAt,
    updatedAt = updatedAt
)

/**
 * Creates a list of test [UserSessionPrivatePayload] wire objects with deterministic raw UUIDs,
 * alternating devices, and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserSessionPrivatePayloadList(
    count: Int = 2,
    generator: (index: Int) -> UserSessionPrivatePayload = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        val isEven = index % 2 == 0
        val winAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/120.0.0.0 Safari/537.36"
        val macAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) Safari/605.1.15"
        createTestUserSessionPrivatePayload(
            id = generateTestUuidString(index),
            userId = generateTestUuidString(index),
            identifierAuthProvider = if (isEven) UserAuthProvider.EMAIL.value else UserAuthProvider.GOOGLE.value,
            identifierDisplayName = if (isEven) "Email Identifier" else "Google Account",
            userAgent = if (isEven) winAgent else macAgent,
            clientDeviceInfo = createTestClientDeviceInfoPayload(
                deviceName = if (isEven) "Windows PC" else "MacBook Pro",
                operationSystemVersion = if (isEven) "Windows 10" else "macOS 14.0"
            ),
            createdAt = createdAtMs,
            lastAccessedAt = createdAtMs + (index * 5_000L),
            lastReauthenticatedAt = createdAtMs + (index * 5_000L),
            expiresAt = createdAtMs + 86_400_000L,
            updatedAt = createdAtMs + 10_000L
        )
    }
): List<UserSessionPrivatePayload> = createTestList(count, generator)
