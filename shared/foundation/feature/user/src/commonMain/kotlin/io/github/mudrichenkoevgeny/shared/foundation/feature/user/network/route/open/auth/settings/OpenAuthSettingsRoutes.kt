package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.PublicAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.settings.BaseOpenAuthSettingsRoutes.BASE_AUTH_SETTINGS_ROUTE

/**
 * Route paths for authentication settings in the open API.
 */
object OpenAuthSettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Response body: [PublicAuthSettingsPayload].
     */
    const val GET_AUTH_SETTINGS = BASE_AUTH_SETTINGS_ROUTE
}