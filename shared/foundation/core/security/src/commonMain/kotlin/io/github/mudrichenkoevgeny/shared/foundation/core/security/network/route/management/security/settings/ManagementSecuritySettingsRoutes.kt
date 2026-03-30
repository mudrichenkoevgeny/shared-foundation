package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.management.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.SecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.base.security.settings.BaseSecuritySettingsRoutes

/**
 * Route paths for security settings in the management API.
 */
object ManagementSecuritySettingsRoutes {
    /**
     * **HTTP method:** `PUT`
     *
     * Request body: [SecuritySettingsPayload].
     */
    const val UPDATE_SECURITY_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseSecuritySettingsRoutes.BASE_SECURITY_SETTINGS_ROUTE}"
}