package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.open.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.response.GlobalSettingsResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.base.globalsettings.BaseGlobalSettingsRoutes

/**
 * Route paths for global settings in the public API.
 */
object OpenGlobalSettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Response body: [GlobalSettingsResponse].
     */
    const val GET_GLOBAL_SETTINGS = BaseGlobalSettingsRoutes.BASE_GLOBAL_SETTINGS_ROUTE
}