package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management

/**
 * Route paths for management API endpoints.
 */
object ManagementRoutes {
    /**
     * Base path for management operations.
     */
    const val BASE_MANAGEMENT_ROUTE = "/management"

    /**
     * Base path for self-management operations (actions the authenticated staff member performs on their own account).
     */
    const val BASE_SELF_MANAGEMENT_ROUTE = "$BASE_MANAGEMENT_ROUTE/self"
}