package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload

/**
 * WebSocket event types related to security functionality.
 */
object SecurityWebSocketEventTypes {
    /**
     * Triggered when open security settings are updated.
     * Payload: [OpenSecuritySettingsPayload]
     */
    const val OPEN_SECURITY_SETTINGS_UPDATED = "OPEN_SECURITY_SETTINGS_UPDATED"

    /**
     * Triggered when management security settings are updated.
     * Payload: [ManagementSecuritySettingsPayload]
     */
    const val MANAGEMENT_SECURITY_SETTINGS_UPDATED = "MANAGEMENT_SECURITY_SETTINGS_UPDATED"
}
