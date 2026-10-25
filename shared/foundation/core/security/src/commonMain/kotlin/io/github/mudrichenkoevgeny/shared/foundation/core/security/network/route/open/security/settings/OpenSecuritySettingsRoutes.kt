package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.open.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.OpenSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.BaseSecurityNetworkRoutes

/**
 * Route paths for security settings in the open API.
 */
object OpenSecuritySettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves open security settings.
     *
     * Request body: None.
     * Response body: [OpenSecuritySettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **UI Presentation:** None.
     */
    const val GET_OPEN_SECURITY_SETTINGS = "${CommonNetworkRoutes.BASE_SETTINGS_NETWORK_ROUTE}${BaseSecurityNetworkRoutes.BASE_SECURITY_NETWORK_ROUTE}"
}