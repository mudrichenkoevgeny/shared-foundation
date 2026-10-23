package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.open.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.BaseSettingsNetworkRoutes

/**
 * Route paths for global settings in the open API.
 */
object OpenGlobalSettingsRoutes {

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves open platform settings.
     *
     * Response body: [OpenGlobalSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **UI Presentation:** None.
     */
    const val GET_OPEN_GLOBAL_SETTINGS =
        "${CommonNetworkRoutes.BASE_SETTINGS_NETWORK_ROUTE}${BaseSettingsNetworkRoutes.BASE_GLOBAL_SETTINGS_NETWORK_ROUTE}"
}
