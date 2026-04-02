package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.base

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
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
     * Base path for audit event resources under [BASE_MANAGEMENT_AUDIT_ROUTE].
     */
    const val BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE = "$BASE_MANAGEMENT_AUDIT_ROUTE/events"

    /**
     * Path for listing audit events with **masked** sensitive fields (caller must hold a masked-read permission).
     *
     * The callable HTTP contract for this path is documented on [ManagementAuditRoutes.GET_AUDIT_EVENTS_MASKED].
     */
    const val GET_AUDIT_EVENTS_MASKED = "$BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE/masked"

    /**
     * Path for listing audit events with **unmasked** sensitive fields (caller must hold an unmasked-read permission).
     *
     * The callable HTTP contract for this path is documented on [ManagementAuditRoutes.GET_AUDIT_EVENTS_UNMASKED].
     */
    const val GET_AUDIT_EVENTS_UNMASKED = "$BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE/unmasked"

    /**
     * Path template for one audit event with **masked** sensitive fields ([AuditApiPaths.EVENT_ID]).
     */
    const val GET_AUDIT_EVENT_MASKED =
        "$BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE/masked/{${AuditApiPaths.EVENT_ID}}"

    /**
     * Path template for one audit event with **unmasked** sensitive fields ([AuditApiPaths.EVENT_ID]).
     */
    const val GET_AUDIT_EVENT_UNMASKED =
        "$BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE/unmasked/{${AuditApiPaths.EVENT_ID}}"
}
