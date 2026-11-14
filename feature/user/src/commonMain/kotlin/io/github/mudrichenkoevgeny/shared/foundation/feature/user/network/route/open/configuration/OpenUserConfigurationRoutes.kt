package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.configuration

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.OpenUserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes.BASE_USER_ROUTE

/**
 * Route paths for user configuration in the open API.
 */
public object OpenUserConfigurationRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves the global or default user configuration settings.
     *
     * Request body: None.
     * Response body: [OpenUserConfigurationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **UI Presentation:** None.
     *
     * @see OpenUserConfigurationPayload
     */
    public const val GET_OPEN_USER_CONFIGURATION: String = "$BASE_USER_ROUTE/configuration"
}
