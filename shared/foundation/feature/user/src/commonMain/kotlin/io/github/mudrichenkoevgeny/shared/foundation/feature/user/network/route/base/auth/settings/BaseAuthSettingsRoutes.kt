package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Path templates shared by open and management **auth settings** endpoints.
 *
 * [BASE_AUTH_SETTINGS_ROUTE] is the resource path relative to [BaseAuthRoutes.BASE_AUTH_ROUTE] (`/auth/settings`).
 * Typical use: open `GET` with a public settings DTO only.
 *
 * [MANAGEMENT_AUTH_SETTINGS_PATH] is the same logical resource under the management prefix
 * [ManagementRoutes.BASE_MANAGEMENT_ROUTE] (`/management/auth/settings`). Typical use: management
 * `GET` / `PUT` with the full settings payload. Concrete HTTP wiring and DTO links live in the
 * open/management route objects that reference these constants.
 */
object BaseAuthSettingsRoutes {

    /**
     * Path to the auth settings resource, appended to [BaseAuthRoutes.BASE_AUTH_ROUTE] (not including any management prefix).
     */
    const val BASE_AUTH_SETTINGS_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/settings"

    /**
     * Full management path for auth settings: [ManagementRoutes.BASE_MANAGEMENT_ROUTE] + [BASE_AUTH_SETTINGS_ROUTE].
     */
    const val MANAGEMENT_AUTH_SETTINGS_PATH = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}$BASE_AUTH_SETTINGS_ROUTE"
}
