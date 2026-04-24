package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base path segments for authentication settings endpoints in the management API.
 */
object BaseManagementAuthSettingsRoutes {
    /**
     * Base path for management authentication settings operations.
     */
    const val BASE_MANAGEMENT_AUTH_SETTINGS_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseAuthRoutes.BASE_AUTH_ROUTE}/settings"

    /**
     * Complete management authentication settings path.
     */
    const val MANAGEMENT_AUTH_SETTINGS_PATH = BASE_MANAGEMENT_AUTH_SETTINGS_ROUTE
}