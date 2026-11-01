package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientDeviceId
import kotlin.time.Instant

/**
 * Utility extension functions for client-facing user session domain models ([UserSessionPrivate], [UserSessionSummary]).
 */

/**
 * Returns `true` when [UserSessionPrivate] is not expired and matches [clientDeviceId].
 */
fun UserSessionPrivate.isValid(clientDeviceId: ClientDeviceId?, now: Instant): Boolean =
    !isUserSessionExpired(expiresAt, now) && isUserSessionCorrectDevice(clientDeviceInfo.deviceId, clientDeviceId)

/**
 * Returns `true` when [UserSessionSummary] is not expired and matches [clientDeviceId].
 */
fun UserSessionSummary.isValid(clientDeviceId: ClientDeviceId?, now: Instant): Boolean =
    !isUserSessionExpired(expiresAt, now) && isUserSessionCorrectDevice(clientDeviceInfo.deviceId, clientDeviceId)

private fun isUserSessionExpired(expiresAt: Instant, now: Instant): Boolean = expiresAt <= now

private fun isUserSessionCorrectDevice(sessionDeviceId: ClientDeviceId?, clientDeviceId: ClientDeviceId?): Boolean {
    if (clientDeviceId == null || sessionDeviceId == null) {
        return true
    }
    return clientDeviceId == sessionDeviceId
}
