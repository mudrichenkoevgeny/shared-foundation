package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.open.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload

/**
 * Route paths for security settings in the open API.
 */
object OpenSecuritySettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves open security settings.
     *
     * Response body: [OpenSecuritySettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     */
    const val GET_OPEN_SECURITY_SETTINGS = "/security/settings"
}
