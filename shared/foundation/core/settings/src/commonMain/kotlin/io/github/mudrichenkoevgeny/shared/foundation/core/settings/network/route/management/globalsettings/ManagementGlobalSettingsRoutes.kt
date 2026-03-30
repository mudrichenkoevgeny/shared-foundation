package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.management.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.GlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.base.globalsettings.BaseGlobalSettingsRoutes

/**
 * Route paths for global settings in the management API.
 */
object ManagementGlobalSettingsRoutes {
    /**
     * **HTTP method:** `PUT`
     *
     * Request body: [GlobalSettingsPayload].
     */
    const val UPDATE_GLOBAL_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseGlobalSettingsRoutes.BASE_GLOBAL_SETTINGS_ROUTE}"
}