package io.github.mudrichenkoevgeny.shared.foundation.feature.user.mapper.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionInternal
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

/**
 * Unit tests verifying domain projection mapping for user sessions (Internal -> Private -> Summary).
 */
class UserSessionDomainMapperTest {
    private val testId: UserSessionId = UserSessionId.generate()
    private val testUserId: UserId = UserId.generate()
    private val testIdentifierId: UserIdentifierId = UserIdentifierId.generate()
    private val testCreatedAt: Instant = Instant.fromEpochMilliseconds(1700000000000L)
    private val testExpiresAt: Instant = Instant.fromEpochMilliseconds(1700086400000L)

    private val testDeviceInfo: ClientDeviceInfo = ClientDeviceInfo(
        clientType = null,
        language = "en",
        deviceId = null,
        deviceName = "Chrome",
        appVersion = "1.0.0",
        operationSystemVersion = "Windows"
    )

    private val testInternal: UserSessionInternal = UserSessionInternal(
        id = testId,
        userId = testUserId,
        userRole = UserRole.USER,
        identifier = "john.doe@example.com",
        identifierId = testIdentifierId,
        identifierDisplayName = "john.doe@example.com",
        identifierAuthProvider = UserAuthProvider.EMAIL,
        refreshTokenHash = RefreshTokenHash("hash_123"),
        clientDeviceInfo = testDeviceInfo,
        userAgent = "Mozilla/5.0",
        ipAddress = "127.0.0.1",
        expiresAt = testExpiresAt,
        lastAccessedAt = testCreatedAt,
        lastReauthenticatedAt = testCreatedAt,
        createdAt = testCreatedAt,
        updatedAt = null
    )

    @Test
    fun testInternalToPrivateMapping() {
        val privateModel: UserSessionPrivate = testInternal.toUserSessionPrivate(isSensitiveValuesMasked = false)

        assertEquals(testId, privateModel.id)
        assertEquals(testUserId, privateModel.userId)
        assertEquals(UserRole.USER, privateModel.userRole)
        assertEquals("john.doe@example.com", privateModel.identifier)
        assertEquals(testIdentifierId, privateModel.identifierId)
        assertEquals("john.doe@example.com", privateModel.identifierDisplayName)
        assertEquals(UserAuthProvider.EMAIL, privateModel.identifierAuthProvider)
        assertEquals(testDeviceInfo, privateModel.clientDeviceInfo)
        assertEquals("Mozilla/5.0", privateModel.userAgent)
        assertEquals("127.0.0.1", privateModel.ipAddress)
        assertEquals(testExpiresAt, privateModel.expiresAt)
        assertEquals(testCreatedAt, privateModel.lastAccessedAt)
        assertEquals(testCreatedAt, privateModel.lastReauthenticatedAt)
        assertEquals(false, privateModel.isSensitiveValuesMasked)
        assertEquals(testCreatedAt, privateModel.createdAt)
    }

    @Test
    fun testPrivateToSummaryMapping() {
        val privateModel: UserSessionPrivate = testInternal.toUserSessionPrivate(isSensitiveValuesMasked = false)
        val summaryModel: UserSessionSummary = privateModel.toUserSessionSummary()

        assertEquals(testId, summaryModel.id)
        assertEquals(testUserId, summaryModel.userId)
        assertEquals(testDeviceInfo, summaryModel.clientDeviceInfo)
        assertEquals("john.doe@example.com", summaryModel.identifierDisplayName)
        assertEquals(UserAuthProvider.EMAIL, summaryModel.identifierAuthProvider)
        assertEquals(testCreatedAt, summaryModel.lastAccessedAt)
        assertEquals(testExpiresAt, summaryModel.expiresAt)
    }

    @Test
    fun testInternalToSummaryMappingDirect() {
        val summaryModel: UserSessionSummary = testInternal.toUserSessionSummary()

        assertEquals(testId, summaryModel.id)
        assertEquals(testUserId, summaryModel.userId)
        assertEquals(testDeviceInfo, summaryModel.clientDeviceInfo)
        assertEquals("john.doe@example.com", summaryModel.identifierDisplayName)
        assertEquals(UserAuthProvider.EMAIL, summaryModel.identifierAuthProvider)
        assertEquals(testCreatedAt, summaryModel.lastAccessedAt)
        assertEquals(testExpiresAt, summaryModel.expiresAt)
    }
}
