package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload

/**
 * WebSocket event types related to system-wide settings functionality.
 */
public object SettingsWebSocketEventTypes {
    /**
     * Triggered when open global settings are updated.
     * Payload: [OpenGlobalSettingsPayload]
     */
    public const val OPEN_GLOBAL_SETTINGS_UPDATED: String = "OPEN_GLOBAL_SETTINGS_UPDATED"

    /**
     * Triggered when management global settings are updated.
     * Payload: [ManagementGlobalSettingsPayload]
     */
    public const val MANAGEMENT_GLOBAL_SETTINGS_UPDATED: String = "MANAGEMENT_GLOBAL_SETTINGS_UPDATED"
}
