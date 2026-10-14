package io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.detail.ManagementAuditEventDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.list.ManagementAuditEventListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import kotlinx.serialization.Serializable

/**
 * Management navigation screen routes for audit events and event details.
 */
@Serializable
sealed interface ManagementAuditNavigation : NavigationRoute {

    /**
     * Navigation screen for displaying a paged list of [AuditEventSummary].
     *
     * **Screen Parameters:** [ManagementAuditEventListParams]
     *
     * **Primary API:** [ManagementAuditRoutes.GET_AUDIT_EVENTS]
     *
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class AuditEventList(
        override val params: ManagementAuditEventListParams = ManagementAuditEventListParams()
    ) : ManagementAuditNavigation, ParameterizedNavigationRoute<ManagementAuditEventListParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for the audit events list. */
            const val ROUTE = BASE_AUDIT_ROUTE

            /** Parent navigation route (main management hub). */
            const val PARENT_ROUTE = ManagementMainNavigation.Main.ROUTE
        }
    }

    /**
     * Navigation screen for displaying specific [AuditEventPrivate] details.
     *
     * **Screen Parameters:** [ManagementAuditEventDetailParams]
     *
     * **Primary API:** [ManagementAuditRoutes.GET_AUDIT_EVENT]
     *
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class AuditEventDetail(
        override val params: ManagementAuditEventDetailParams
    ) : ManagementAuditNavigation, ParameterizedNavigationRoute<ManagementAuditEventDetailParams> {
        override val route: String get() = getPath(params.eventId)
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route template for audit event details. */
            const val ROUTE = "$BASE_AUDIT_ROUTE/{${AuditApiPaths.EVENT_ID}}"

            /** Parent navigation route for fallback navigation and breadcrumbs. */
            const val PARENT_ROUTE = AuditEventList.ROUTE

            /**
             * Builds a concrete route path for the specified [eventId].
             *
             * @param eventId Target audit event ID.
             * @return Formatted URL path replacing parameter placeholder with [eventId].
             */
            fun getPath(eventId: AuditEventId): String =
                ROUTE.replace("{${AuditApiPaths.EVENT_ID}}", eventId.asHexDashString())

            /**
             * Builds a concrete route path for the specified string [eventId].
             *
             * @param eventId String representation of the target event ID.
             * @return Formatted URL path replacing parameter placeholder with [eventId].
             */
            fun getPath(eventId: String): String =
                ROUTE.replace("{${AuditApiPaths.EVENT_ID}}", eventId)
        }
    }

    companion object {
        /** Base route path for audit navigation. */
        private const val BASE_AUDIT_ROUTE = "/audit/events"
    }
}
