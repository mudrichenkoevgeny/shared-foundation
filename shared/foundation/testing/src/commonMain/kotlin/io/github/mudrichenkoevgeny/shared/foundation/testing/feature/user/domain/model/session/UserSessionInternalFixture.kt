package io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_EMAIL
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_IP_ADDRESS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_USER_AGENT
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.domain.model.client.createTestClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.identifier.createTestUserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.token.createTestRefreshTokenHash
import io.github.mudrichenkoevgeny.shared.foundation.testing.feature.user.domain.model.user.createTestUserId
import kotlin.time.Instant

/**
 * Creates a test [UserSessionInternal] domain instance.
 *
 * @param id Unique session ID ([UserSessionId]).
 * @param userId Owning user ID ([UserId]).
 * @param userRole Access role ([UserRole]).
 * @param identifier Credential string.
 * @param identifierId Credential ID ([UserIdentifierId]).
 * @param identifierDisplayName Display name of identifier.
 * @param identifierAuthProvider Provider category ([UserAuthProvider]).
 * @param clientDeviceInfo Client device info ([ClientDeviceInfo]).
 * @param userAgent User agent string.
 * @param ipAddress IP address.
 * @param refreshTokenHash Refresh token hash ([RefreshTokenHash]).
 * @param expiresAt Expiration timestamp.
 * @param lastAccessedAt Last activity timestamp.
 * @param lastReauthenticatedAt Last re-authentication timestamp.
 * @param createdAt Creation timestamp.
 * @param updatedAt Modification timestamp.
 */
fun createTestUserSessionInternal(
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
    refreshTokenHash: RefreshTokenHash = createTestRefreshTokenHash(),
    expiresAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS + 86400000L),
    lastAccessedAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    lastReauthenticatedAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    createdAt: Instant = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS),
    updatedAt: Instant? = Instant.fromEpochMilliseconds(TEST_TIME_MILLIS)
) = UserSessionInternal(
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
    refreshTokenHash = refreshTokenHash,
    expiresAt = expiresAt,
    lastAccessedAt = lastAccessedAt,
    lastReauthenticatedAt = lastReauthenticatedAt,
    createdAt = createdAt,
    updatedAt = updatedAt
)
