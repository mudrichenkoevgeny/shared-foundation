package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.SecurityRoutes.BASE_SECURITY_ROUTE

/**
 * Object containing route definitions for security configuration.
 *
 * @property GET_SECURITY_SETTINGS The endpoint to retrieve security-related rules.
 */
object SecuritySettingsRoutes {
    const val GET_SECURITY_SETTINGS = "$BASE_SECURITY_ROUTE/settings"
}