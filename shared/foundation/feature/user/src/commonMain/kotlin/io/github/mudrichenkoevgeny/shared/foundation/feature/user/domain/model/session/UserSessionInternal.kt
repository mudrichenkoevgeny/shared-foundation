package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.token.RefreshTokenHash
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import kotlin.time.Instant

/**
 * Internal session row for server-side storage and business logic.
 *
 * Adds [refreshTokenHash] and database row modification timestamp [updatedAt] to [UserSessionPrivate]-equivalent fields;
 * these internal fields must not be exposed directly via public API payloads.
 *
 * @property id Session row id.
 * @property userId Owning user id.
 * @property userRole User role during the session.
 * @property identifier Raw or masked login identifier string.
 * @property identifierId Id of the identifier used for login.
 * @property identifierDisplayName Display name of the identifier used for login.
 * @property identifierAuthProvider Authentication provider category.
 * @property refreshTokenHash Hash of the refresh token.
 * @property clientDeviceInfo Hardware and environment device info.
 * @property userAgent HTTP `User-Agent` header value, or `null`.
 * @property ipAddress Raw or masked client IP address, or `null`.
 * @property expiresAt Session expiration timestamp.
 * @property lastAccessedAt Timestamp of last session activity.
 * @property lastReauthenticatedAt Timestamp of last re-authentication.
 * @property createdAt Creation timestamp.
 * @property updatedAt Last database row update timestamp, or `null`.
 */
data class UserSessionInternal(
    val id: UserSessionId = UserSessionId.generate(),
    val userId: UserId,
    val userRole: UserRole,
    val identifier: String,
    val identifierId: UserIdentifierId,
    val identifierDisplayName: String,
    val identifierAuthProvider: UserAuthProvider,
    val refreshTokenHash: RefreshTokenHash,
    val clientDeviceInfo: ClientDeviceInfo,
    val userAgent: String?,
    val ipAddress: String?,
    val expiresAt: Instant,
    val lastAccessedAt: Instant,
    val lastReauthenticatedAt: Instant,
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
        val sessionDeviceId = clientDeviceInfo.deviceId
        if (clientDeviceId == null || sessionDeviceId == null) {
            return true
        }
        return clientDeviceId == sessionDeviceId
    }
}
