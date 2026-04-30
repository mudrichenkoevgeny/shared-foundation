package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.configuration

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.configuration.UserConfigurationPayload
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
     * Response body: [UserConfigurationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     * (**OR** semantics).
     * - **Required Permissions:** None.
     */
    const val GET_CONFIGURATION = BaseManagementUserConfigurationRoutes.BASE_USER_CONFIGURATION_ROUTE
}