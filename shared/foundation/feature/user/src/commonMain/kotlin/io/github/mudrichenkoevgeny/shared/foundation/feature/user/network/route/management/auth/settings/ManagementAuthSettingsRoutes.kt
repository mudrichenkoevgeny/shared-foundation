package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.settings.UpdateAuthSettingsRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.settings.BaseAuthSettingsRoutes

/**
 * Route paths for authentication settings in the management API.
 */
object ManagementAuthSettingsRoutes {
    /**
     * **HTTP method:** `PUT`
     *
     * Request body: [UpdateAuthSettingsRequest].
     */
    const val UPDATE_AUTH_SETTINGS =
        "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseAuthSettingsRoutes.BASE_AUTH_SETTINGS_ROUTE}"
}
