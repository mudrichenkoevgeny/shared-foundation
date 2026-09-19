package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.base.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.base.security.BaseSecurityRoutes

/**
 * Shared base routes for security settings endpoints.
 */
object BaseSecuritySettingsRoutes {
    /**
     * Base path for security settings operations.
     */
    const val BASE_SECURITY_SETTINGS_ROUTE = "${BaseSecurityRoutes.BASE_SECURITY_ROUTES}/settings"

    /**
     * Path template for resetting security settings to default values.
     */
    const val RESET_SECURITY_SETTINGS_ROUTE = "$BASE_SECURITY_SETTINGS_ROUTE/reset"
}
