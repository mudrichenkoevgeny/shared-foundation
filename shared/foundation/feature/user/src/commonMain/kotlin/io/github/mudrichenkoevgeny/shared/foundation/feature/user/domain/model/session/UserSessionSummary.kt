package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.authprovider.UserAuthProvider
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionSummaryPayload
import kotlin.time.Instant

/**
 * Condensed session row for active device lists and session badges.
 *
 * Wire counterpart is [UserSessionSummaryPayload].
 *
 * @property id Session row id.
 * @property deviceInfo Hardware and environment device info.
 * @property identifierDisplayName Display name of the identifier used for login.
 * @property identifierAuthProvider Authentication provider category.
 * @property lastAccessedAt Timestamp of last session activity.
 * @property expiresAt Session expiration timestamp.
 */
data class UserSessionSummary(
    val id: UserSessionId = UserSessionId.generate(),
    val deviceInfo: ClientDeviceInfo,
    val identifierDisplayName: String,
    val identifierAuthProvider: UserAuthProvider,
    val lastAccessedAt: Instant,
    val expiresAt: Instant
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
