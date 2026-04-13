package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload
import kotlin.time.Instant

/**
 * Session row; wire counterpart [UserSessionPayload]. [identifier] and [ipAddress] follow [isSensitiveValuesMasked].
 * Use [UserSessionInternal] for server-side storage fields that must not be exposed via API payloads.
 */
data class UserSession(
    val id: UserSessionId = UserSessionId.generate(),
    val userId: UserId,
    val identifier: String,
    val identifierId: UserIdentifierId,
    val identifierAuthProvider: UserAuthProvider,
    val revoked: Boolean,
    val deviceInfo: ClientDeviceInfo,
    val userAgent: String?,
    val ipAddress: String?,
    val expiresAt: Instant?,
    val lastAccessedAt: Instant?,
    val lastReauthenticatedAt: Instant?,
    val isSensitiveValuesMasked: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant?
) {
    /**
     * Returns `true` when the session is not revoked, not expired and matches the caller device.
     *
     * Device matching is permissive: if either the session or the client does not provide a device id,
     * the session is considered valid for device checks.
     */
    fun isValid(clientDeviceId: ClientDeviceId?, now: Instant): Boolean {
        return !revoked
            && !isExpired(now)
            && isCorrectDevice(clientDeviceId)
    }

    private fun isExpired(now: Instant): Boolean = expiresAt?.let { it <= now } == true

    private fun isCorrectDevice(clientDeviceId: ClientDeviceId?): Boolean {
        val sessionDeviceId = deviceInfo.deviceId
        if (clientDeviceId == null || sessionDeviceId == null) {
            return true
        }
        return clientDeviceId == sessionDeviceId
    }
}
