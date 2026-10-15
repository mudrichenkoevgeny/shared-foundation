package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.base.management.BaseManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementSelfUserNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload

/**
 * Route paths for the current authenticated principal's account in the management API (self-service).
 */
object SelfManagementUserRoutes {
    /** Base path for self-service user operations in the management API. */
    internal const val BASE_SELF_MANAGEMENT_USER_ROUTE = "${BaseManagementRoutes.BASE_SELF_MANAGEMENT_ROUTE}/user"

    /**
     * **HTTP method:** `GET`
     *
     * Returns the private management-level details of the **current** authenticated staff member or administrator.
     *
     * Response body: [UserPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfUserNavigation.Main]
     */
    const val GET_USER = BASE_SELF_MANAGEMENT_USER_ROUTE
}