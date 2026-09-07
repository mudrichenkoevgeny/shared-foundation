package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.configuration

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.OpenUserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.configuration.BaseManagementUserConfigurationRoutes

/**
 * Route paths for management-specific user configuration settings.
 */
object ManagementUserConfigurationRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves the configuration settings relevant for management tasks and staff workflows.
     *
     * Response body: [OpenUserConfigurationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     */
    const val GET_CONFIGURATION = BaseManagementUserConfigurationRoutes.BASE_USER_CONFIGURATION_ROUTE
}