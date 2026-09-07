package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload

/**
 * WebSocket event types related to system-wide settings functionality.
 */
object SettingsWebSocketEventTypes {
    /**
     * Triggered when open global settings are updated.
     * Payload: [OpenGlobalSettingsPayload]
     */
    const val OPEN_GLOBAL_SETTINGS_UPDATED = "OPEN_GLOBAL_SETTINGS_UPDATED"

    /**
     * Triggered when management global settings are updated.
     * Payload: [ManagementGlobalSettingsPayload]
     */
    const val MANAGEMENT_GLOBAL_SETTINGS_UPDATED = "MANAGEMENT_GLOBAL_SETTINGS_UPDATED"
}
