package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.response.settings.SecuritySettingsResponse

/**
 * WebSocket event types related to security functionality.
 */
object SecurityWebSocketEventTypes {
    /**
     * Triggered when security settings are updated.
     * Payload: [SecuritySettingsResponse]
     */
    const val SECURITY_SETTINGS_UPDATED = "SECURITY_SETTINGS_UPDATED"
}