package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.response.GlobalSettingsResponse
/**
 * WebSocket event types related to system-wide settings functionality.
 */
object SettingsWebSocketEventTypes {
    /**
     * Triggered when global settings are updated.
     * Payload: [GlobalSettingsResponse]
     */
    const val GLOBAL_SETTINGS_UPDATED = "GLOBAL_SETTINGS_UPDATED"
}