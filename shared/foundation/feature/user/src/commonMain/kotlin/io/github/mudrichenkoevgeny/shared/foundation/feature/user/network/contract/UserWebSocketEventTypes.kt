package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.auth.settings.AuthSettingsResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.user.CurrentUserResponse

/**
 * WebSocket event types related to user functionality.
 */
object UserWebSocketEventTypes {
    /**
     * Triggered when auth settings are updated.
     * Payload: [AuthSettingsResponse]
     */
    const val AUTH_SETTINGS_UPDATED = "AUTH_SETTINGS_UPDATED"

    /**
     * Sent when the account status of the current user changes. See [UserAccountStatus].
     * Payload: [CurrentUserResponse]
     */
    const val ACCOUNT_STATUS_CHANGED = "ACCOUNT_STATUS_CHANGED"

    /**
     * Forced termination of the current session. The client must clear local data and logout.
     */
    const val SESSION_TERMINATED = "SESSION_TERMINATED"
}