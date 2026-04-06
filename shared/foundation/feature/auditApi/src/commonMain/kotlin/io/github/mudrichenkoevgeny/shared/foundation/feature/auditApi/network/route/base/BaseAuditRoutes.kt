package io.github.mudrichenkoevgeny.shared.foundation.feature.auditApi.network.route.base

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes

/**
 * Shared path segments for audit URLs (prefix from [ManagementRoutes] where applicable).
 */
object BaseAuditRoutes {
    /** Prefix: [ManagementRoutes.BASE_MANAGEMENT_ROUTE] + `/audit`. */
    const val BASE_MANAGEMENT_AUDIT_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}/audit"

    /** [BASE_MANAGEMENT_AUDIT_ROUTE] + `/events`. */
    const val BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE = "$BASE_MANAGEMENT_AUDIT_ROUTE/events"

    /** List: [BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE]. */
    const val GET_AUDIT_EVENTS = BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE

    /**
     * One event: [BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE] + `/{id}`.
     *
     * Path parameter: [AuditApiPaths.EVENT_ID].
     *
     */
    const val GET_AUDIT_EVENT =
        "$BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE/{${AuditApiPaths.EVENT_ID}}"
}
