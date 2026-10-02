package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes

/**
 * Shared base path segments for user management endpoints.
 */
internal object BaseManagementUserRoutes {
    /**
     * Base path for user management operations under [ManagementRoutes.BASE_MANAGEMENT_ROUTE].
     */
    internal const val BASE_MANAGEMENT_USERS_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}/users"
}
