package io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.network.route.open.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.SecuritySettingsPayload

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
    const val GET_SECURITY_SETTINGS = _root_ide_package_.io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.network.route.base.security.settings.BaseSecuritySettingsRoutes.BASE_SECURITY_SETTINGS_ROUTE
}