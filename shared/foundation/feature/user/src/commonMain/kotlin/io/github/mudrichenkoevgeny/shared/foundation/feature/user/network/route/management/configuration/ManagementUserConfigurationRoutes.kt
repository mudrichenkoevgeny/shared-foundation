package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.ManagementUserConfigurationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.OpenUserConfigurationPayload

/**
 * Route paths for management-specific user configuration settings.
 */
object ManagementUserConfigurationRoutes {
    /** Base path for management user configuration endpoints. */
    private const val BASE_MANAGEMENT_USER_CONFIGURATION_ROUTE = "${CommonNetworkRoutes.BASE_MANAGEMENT_NETWORK_ROUTE}/user-configuration"

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves the configuration settings relevant for management tasks and staff workflows.
     *
     * Request body: None.
     * Response body: [ManagementUserConfigurationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:** None.
     */
    const val GET_MANAGEMENT_USER_CONFIGURATION = BASE_MANAGEMENT_USER_CONFIGURATION_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves the open (public) user configuration settings for unauthenticated management users.
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
     */
    const val GET_OPEN_USER_CONFIGURATION = "$BASE_MANAGEMENT_USER_CONFIGURATION_ROUTE/open"
}