package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload

/**
 * WebSocket event types related to user functionality.
 */
object UserWebSocketEventTypes {
    /**
     * Sent when the current access token expires or becomes invalid.
     * The client should refresh the token and reconnect.
     */
    const val UNAUTHORIZED = "UNAUTHORIZED"

    /**
     * Triggered when auth settings are updated in the open scope.
     * Payload: [OpenAuthSettingsPayload]
     */
    const val OPEN_AUTH_SETTINGS_UPDATED = "OPEN_AUTH_SETTINGS_UPDATED"

    /**
     * Triggered when auth settings are updated in the management scope.
     * Payload: [ManagementAuthSettingsPayload]
     */
    const val MANAGEMENT_AUTH_SETTINGS_UPDATED = "MANAGEMENT_AUTH_SETTINGS_UPDATED"

    /**
     * Sent when the user's data (profile, role, account status, or permissions) is updated.
     * Use this to synchronize the local state of the current user.
     * Payload: [UserDetailsPayload]
     */
    const val USER_UPDATED = "USER_UPDATED"

    /**
     * Forced deletion of the current session. The client must clear local data and logout.
     */
    const val SESSION_DELETED = "SESSION_DELETED"
}