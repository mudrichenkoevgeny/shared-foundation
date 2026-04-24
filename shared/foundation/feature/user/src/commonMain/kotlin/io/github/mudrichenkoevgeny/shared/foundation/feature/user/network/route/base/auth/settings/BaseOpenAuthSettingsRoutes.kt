package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base path segments for authentication settings endpoints.
 */
object BaseOpenAuthSettingsRoutes {
    /**
     * Path to the authentication settings resource.
     */
    const val BASE_AUTH_SETTINGS_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/settings"
}