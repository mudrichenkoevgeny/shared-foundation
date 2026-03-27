package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.auth.settings.AuthSettingsResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.settings.BaseAuthSettingsRoutes.BASE_AUTH_SETTINGS_ROUTE

/**
 * Route paths for authentication settings in the management API.
 */
object ManagementAuthSettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Response body: [AuthSettingsResponse].
     */
    const val GET_AUTH_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}$BASE_AUTH_SETTINGS_ROUTE"
}