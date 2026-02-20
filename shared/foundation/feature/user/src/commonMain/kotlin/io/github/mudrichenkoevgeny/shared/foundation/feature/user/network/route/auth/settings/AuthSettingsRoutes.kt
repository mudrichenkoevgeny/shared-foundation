package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.auth.AuthRoutes

/**
 * Object containing route definitions for authentication configuration.
 *
 * @property GET_AUTH_SETTINGS The endpoint to authentication settings.
 */
object AuthSettingsRoutes {
    const val GET_AUTH_SETTINGS = "${AuthRoutes.BASE_AUTH_ROUTE}/settings"
}