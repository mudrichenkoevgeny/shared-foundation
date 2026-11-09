package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.time.Instant

class UserSessionModelUtilsTest {
    @Test
    fun `isValid returns true for active session on same device`() {
        val now = Instant.fromEpochMilliseconds(1000)
        val expiresAt = Instant.fromEpochMilliseconds(2000)
        val deviceId = ClientDeviceId.generate()

        val session =
            UserSessionSummary(
                id = UserSessionId.generate(),
                userId = UserId.generate(),
                clientDeviceInfo = ClientDeviceInfo(
                    deviceId = deviceId,
                    deviceName = "Pixel 8",
                    clientType = null,
                    language = null,
                    appVersion = null,
                    operationSystemVersion = null
                ),
                identifierDisplayName = "user",
                identifierAuthProvider = UserAuthProvider.EMAIL,
                lastAccessedAt = now,
                expiresAt = expiresAt
            )

        assertTrue(session.isValid(deviceId, now))
    }

    @Test
    fun `isValid returns false when session is expired`() {
        val now = Instant.fromEpochMilliseconds(3000)
        val expiresAt = Instant.fromEpochMilliseconds(2000)
        val deviceId = ClientDeviceId.generate()

        val session =
            UserSessionSummary(
                id = UserSessionId.generate(),
                userId = UserId.generate(),
                clientDeviceInfo = ClientDeviceInfo(
                    deviceId = deviceId,
                    deviceName = "Pixel 8",
                    clientType = null,
                    language = null,
                    appVersion = null,
                    operationSystemVersion = null
                ),
                identifierDisplayName = "user",
                identifierAuthProvider = UserAuthProvider.EMAIL,
                lastAccessedAt = Instant.fromEpochMilliseconds(1000),
                expiresAt = expiresAt
            )

        assertFalse(session.isValid(deviceId, now))
    }

    @Test
    fun `isValid returns false when device IDs do not match`() {
        val now = Instant.fromEpochMilliseconds(1000)
        val expiresAt = Instant.fromEpochMilliseconds(2000)
        val sessionDeviceId = ClientDeviceId.generate()
        val callerDeviceId = ClientDeviceId.generate()

        val session =
            UserSessionSummary(
                id = UserSessionId.generate(),
                userId = UserId.generate(),
                clientDeviceInfo = ClientDeviceInfo(
                    deviceId = sessionDeviceId,
                    deviceName = "Pixel 8",
                    clientType = null,
                    language = null,
                    appVersion = null,
                    operationSystemVersion = null
                ),
                identifierDisplayName = "user",
                identifierAuthProvider = UserAuthProvider.EMAIL,
                lastAccessedAt = now,
                expiresAt = expiresAt
            )

        assertFalse(session.isValid(callerDeviceId, now))
    }
}
