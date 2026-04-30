package io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.network.route.open.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.SecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.network.route.base.security.settings.BaseSecuritySettingsRoutes

/**
 * Route paths for security settings in the public API.
 */
object OpenSecuritySettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves public-facing security settings.
     *
     * Response body: [SecuritySettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     */
    const val GET_SECURITY_SETTINGS = BaseSecuritySettingsRoutes.BASE_SECURITY_SETTINGS_ROUTE
}