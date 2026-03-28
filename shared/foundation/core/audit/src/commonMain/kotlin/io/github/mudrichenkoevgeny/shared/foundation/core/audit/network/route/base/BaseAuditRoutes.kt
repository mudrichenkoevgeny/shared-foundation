package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.base

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes

/**
 * Shared path segments for audit HTTP endpoints under the management API.
 */
object BaseAuditRoutes {
    /**
     * Base path for audit operations under [ManagementRoutes.BASE_MANAGEMENT_ROUTE].
     */
    const val BASE_MANAGEMENT_AUDIT_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}/audit"

    /**
     * Path for listing audit events (pagination, sort, and filters via query parameters).
     *
     * The callable HTTP contract for this path is documented on [ManagementAuditRoutes.GET_AUDIT_EVENTS].
     */
    const val GET_AUDIT_EVENTS = "$BASE_MANAGEMENT_AUDIT_ROUTE/events"
}
