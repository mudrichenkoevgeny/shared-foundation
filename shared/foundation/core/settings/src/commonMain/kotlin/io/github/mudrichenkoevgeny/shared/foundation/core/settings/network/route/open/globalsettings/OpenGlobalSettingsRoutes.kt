package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.open.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.GlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.base.globalsettings.BaseGlobalSettingsRoutes

/**
 * Route paths for global settings in the public API.
 */
object OpenGlobalSettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Response body: [GlobalSettingsPayload].
     */
    const val GET_GLOBAL_SETTINGS = BaseGlobalSettingsRoutes.BASE_GLOBAL_SETTINGS_ROUTE
}