package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.configuration

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.OpenUserConfigurationPayload

/**
 * Route paths for user configuration in the open API.
 */
object OpenUserConfigurationRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves the global or default user configuration settings.
     *
     * Response body: [OpenUserConfigurationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     */
    const val GET_CONFIGURATION = "user-configuration"
}