package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload

/**
 * WebSocket event types related to user functionality.
 */
public object UserWebSocketEventTypes {
    /**
     * Sent when the current access token expires or becomes invalid.
     * The client should refresh the token and reconnect.
     */
    public const val UNAUTHORIZED: String = "UNAUTHORIZED"

    /**
     * Triggered when auth settings are updated in the open scope.
     * Payload: [OpenAuthSettingsPayload]
     */
    public const val OPEN_AUTH_SETTINGS_UPDATED: String = "OPEN_AUTH_SETTINGS_UPDATED"

    /**
     * Triggered when auth settings are updated in the management scope.
     * Payload: [ManagementAuthSettingsPayload]
     */
    public const val MANAGEMENT_AUTH_SETTINGS_UPDATED: String = "MANAGEMENT_AUTH_SETTINGS_UPDATED"

    /**
     * Sent when the user's data (user, role, account status, or permissions) is updated.
     * Use this to synchronize the local state of the current user.
     * Payload: [UserPrivatePayload]
     */
    public const val USER_UPDATED: String = "USER_UPDATED"

    /**
     * Forced deletion of the current session. The client must clear local data and logout.
     */
    public const val SESSION_DELETED: String = "SESSION_DELETED"
}
