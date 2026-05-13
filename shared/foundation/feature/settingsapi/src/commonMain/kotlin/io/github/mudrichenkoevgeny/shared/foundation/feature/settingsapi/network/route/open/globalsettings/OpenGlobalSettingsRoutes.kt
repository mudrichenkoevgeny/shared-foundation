package io.github.mudrichenkoevgeny.shared.foundation.feature.settingsapi.network.route.open.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.GlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsapi.network.route.base.globalsettings.BaseGlobalSettingsRoutes

/**
 * Route paths for global settings in the public API.
 */
object OpenGlobalSettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves public-facing platform settings.
     *
     * Response body: [GlobalSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     */
    const val GET_GLOBAL_SETTINGS = BaseGlobalSettingsRoutes.BASE_GLOBAL_SETTINGS_ROUTE
}