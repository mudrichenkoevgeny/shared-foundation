package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.open.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.SecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.base.security.settings.BaseSecuritySettingsRoutes

/**
 * Route paths for security settings in the public API.
 */
object OpenSecuritySettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Response body: [SecuritySettingsPayload].
     */
    const val GET_SECURITY_SETTINGS = BaseSecuritySettingsRoutes.BASE_SECURITY_SETTINGS_ROUTE
}