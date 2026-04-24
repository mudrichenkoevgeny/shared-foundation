package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes

/**
 * Shared base path segments for the current authenticated principal's account in the management API.
 */
object BaseSelfManagementUserRoutes {
    /**
     * Base path for self-service management account operations.
     */
    const val BASE_SELF_MANAGEMENT_USER_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}/self/user"

    /**
     * Path for retrieving the current management account's profile ([BASE_SELF_MANAGEMENT_USER_ROUTE]).
     */
    const val GET_USER = BASE_SELF_MANAGEMENT_USER_ROUTE
}