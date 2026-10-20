package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_IP_ADDRESS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_USER_AGENT
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.createTestList
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.client.createTestClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier.createTestUserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.createTestUserId
import kotlin.time.Instant

/**
 * Creates a test [UserSessionPrivate] instance with default mock properties for unit testing.
 *
 * @param id Unique session ID ([UserSessionId]).
 * @param userId Owning user ID ([UserId]).
 * @param userRole User access level ([UserRole]).
 * @param identifier Login credential identifier string.
 * @param identifierId Credential ID ([UserIdentifierId]).
 * @param identifierDisplayName Display name of the identifier.
 * @param identifierAuthProvider Provider category ([UserAuthProvider]).
 * @param clientDeviceInfo Device info ([ClientDeviceInfo]).
 * @param userAgent HTTP User-Agent header string.
 * @param ipAddress Client IP address.
 * @param expiresAt Expiration timestamp.
 * @param lastAccessedAt Last activity timestamp.
 * @param lastReauthenticatedAt Last re-authentication timestamp.
 * @param isSensitiveValuesMasked Whether sensitive values are masked.
 * @param createdAt Creation timestamp.
 * @param updatedAt Modification timestamp.
 */
fun createTestUserSessionPrivate(
    id: UserSessionId = createTestUserSessionId(),
    userId: UserId = createTestUserId(),
    userRole: UserRole = UserRole.USER,
    identifier: String = TEST_EMAIL,
    identifierId: UserIdentifierId = createTestUserIdentifierId(),
    identifierDisplayName: String = "Test User",
    identifierAuthProvider: UserAuthProvider = UserAuthProvider.EMAIL,
    clientDeviceInfo: ClientDeviceInfo = createTestClientDeviceInfo(),
    userAgent: String? = TEST_USER_AGENT,
    ipAddress: String? = TEST_IP_ADDRESS,
    expiresAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS + 86400000L),
    lastAccessedAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    lastReauthenticatedAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    isSensitiveValuesMasked: Boolean = false,
    createdAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    updatedAt: Instant? = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS)
) = UserSessionPrivate(
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
 * Creates a list of test [UserSessionPrivate] instances with deterministic index-based IDs (`sess_1`, `sess_2`, ...),
 * alternating devices, and time shifts.
 *
 * @param count Number of elements to generate (defaults to 2).
 * @param generator Custom item builder receiving 0-based element index.
 */
fun createTestUserSessionPrivateList(
    count: Int = 2,
    generator: (index: Int) -> UserSessionPrivate = { index ->
        val createdAtMs = TEST_TIME_MILLIS + (index * 60_000L)
        val isEven = index % 2 == 0
        val winAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/120.0.0.0 Safari/537.36"
        val macAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) Safari/605.1.15"
        val winDevice = createTestClientDeviceInfo(
            deviceName = "Windows PC",
            operationSystemVersion = "Windows 10"
        )
        val macDevice = createTestClientDeviceInfo(
            deviceName = "MacBook Pro",
            operationSystemVersion = "macOS 14.0"
        )
        createTestUserSessionPrivate(
            id = createTestUserSessionId("sess_${index + 1}"),
            userId = createTestUserId("usr_${index + 1}"),
            identifierAuthProvider = if (isEven) UserAuthProvider.EMAIL else UserAuthProvider.GOOGLE,
            identifierDisplayName = if (isEven) "Email Identifier" else "Google Account",
            userAgent = if (isEven) winAgent else macAgent,
            clientDeviceInfo = if (isEven) winDevice else macDevice,
            createdAt = Instant.fromEpochMilliseconds(createdAtMs),
            lastAccessedAt = Instant.fromEpochMilliseconds(createdAtMs + (index * 5_000L)),
            lastReauthenticatedAt = Instant.fromEpochMilliseconds(createdAtMs + (index * 5_000L)),
            expiresAt = Instant.fromEpochMilliseconds(createdAtMs + 86_400_000L),
            updatedAt = Instant.fromEpochMilliseconds(createdAtMs + 10_000L)
        )
    }
): List<UserSessionPrivate> = createTestList(count, generator)
