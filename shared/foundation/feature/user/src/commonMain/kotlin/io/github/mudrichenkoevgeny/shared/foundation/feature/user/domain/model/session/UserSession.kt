package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload
import kotlin.time.Instant

/**
 * Session row; wire counterpart [UserSessionPayload]. [identifier] and [ipAddress] follow [isSensitiveValuesMasked].
 * Use [UserSessionInternal] for server-side storage fields that must not be exposed via API payloads.
 *
 * @property id Session row id.
 * @property userId Owning user id.
 * @property userRole User role during the session.
 * @property identifier Raw or masked login identifier string.
 * @property identifierId Id of the identifier used for login.
 * @property identifierDisplayName Display name of the identifier used for login.
 * @property identifierAuthProvider Authentication provider category.
 * @property deviceInfo Hardware and environment device info.
 * @property userAgent HTTP `User-Agent` header value, or `null`.
 * @property ipAddress Raw or masked client IP address, or `null`.
 * @property expiresAt Session expiration timestamp.
 * @property lastAccessedAt Timestamp of last session activity.
 * @property lastReauthenticatedAt Timestamp of last re-authentication.
 * @property isSensitiveValuesMasked `true` when [identifier] and [ipAddress] are masked.
 * @property createdAt Creation timestamp.
 * @property updatedAt Last modification timestamp, or `null`.
 */
data class UserSession(
    val id: UserSessionId = UserSessionId.generate(),
    val userId: UserId,
    val userRole: UserRole,
    val identifier: String,
    val identifierId: UserIdentifierId,
    val identifierDisplayName: String,
    val identifierAuthProvider: UserAuthProvider,
    val deviceInfo: ClientDeviceInfo,
    val userAgent: String?,
    val ipAddress: String?,
    val expiresAt: Instant,
    val lastAccessedAt: Instant,
    val lastReauthenticatedAt: Instant,
    val isSensitiveValuesMasked: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant?
) {
    /**
     * Returns `true` when the session is not expired and matches the caller device.
     *
     * Device matching is permissive: if either the session or the client does not provide a device id,
     * the session is considered valid for device checks.
     */
    fun isValid(clientDeviceId: ClientDeviceId?, now: Instant): Boolean {
        return !isExpired(now) && isCorrectDevice(clientDeviceId)
    }

    private fun isExpired(now: Instant): Boolean = expiresAt <= now

    private fun isCorrectDevice(clientDeviceId: ClientDeviceId?): Boolean {
        val sessionDeviceId = deviceInfo.deviceId
        if (clientDeviceId == null || sessionDeviceId == null) {
            return true
        }
        return clientDeviceId == sessionDeviceId
    }
}
