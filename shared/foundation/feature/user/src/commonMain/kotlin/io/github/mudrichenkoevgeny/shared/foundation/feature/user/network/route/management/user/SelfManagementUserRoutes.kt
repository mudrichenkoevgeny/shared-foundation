package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserDetailsPayload

/**
 * Route paths for the current authenticated principal's account in the management API (self-service).
 */
object SelfManagementUserRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Returns the full management-level details of the **current** authenticated staff member or administrator.
     *
     * Response body: [UserDetailsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     * (**OR** semantics).
     * - **Required Permissions:** None.
     */
    const val GET_USER = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}/self/user"
}