package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.base.settings.BaseSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Route paths for authentication settings in the open API.
 */
object OpenAuthSettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves open authentication settings (e.g., enabled providers, password requirements).
     *
     * Response body: [OpenAuthSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **UI Presentation:** None.
     */
    const val GET_OPEN_AUTH_SETTINGS =
        "${BaseSettingsRoutes.BASE_SETTINGS_ROUTE}${BaseAuthRoutes.BASE_AUTH_ROUTE}"
}