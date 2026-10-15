package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.open.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.base.settings.BaseSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload

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
    const val GET_OPEN_GLOBAL_SETTINGS = "${BaseSettingsRoutes.BASE_SETTINGS_ROUTE}/global"
}