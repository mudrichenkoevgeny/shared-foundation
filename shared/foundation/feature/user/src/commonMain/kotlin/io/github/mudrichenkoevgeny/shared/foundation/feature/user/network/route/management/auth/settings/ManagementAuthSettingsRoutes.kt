package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.settings.BaseAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.settings.OpenAuthSettingsRoutes

/**
 * Route paths for authentication settings in the management API.
 *
 * `GET` and `PUT` share the same path; the management prefix and admin auth distinguish them from
 * [OpenAuthSettingsRoutes] (`GET` only, public slice).
 */
object ManagementAuthSettingsRoutes {

    /**
     * **HTTP method:** `GET`
     *
     * Response body: [ManagementAuthSettingsPayload].
     */
    const val GET_AUTH_SETTINGS_MANAGEMENT = BaseAuthSettingsRoutes.MANAGEMENT_AUTH_SETTINGS_PATH

    /**
     * **HTTP method:** `PUT`
     *
     * Request body: [ManagementAuthSettingsPayload].
     */
    const val UPDATE_AUTH_SETTINGS = BaseAuthSettingsRoutes.MANAGEMENT_AUTH_SETTINGS_PATH
}
