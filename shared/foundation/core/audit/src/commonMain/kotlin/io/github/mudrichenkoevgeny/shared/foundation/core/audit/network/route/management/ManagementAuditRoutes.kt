package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.permissions.AuditPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.route.management.ManagementAuditNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventSummaryPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes

/**
 * Route paths for audit management in the management API.
 */
object ManagementAuditRoutes {
    /** Base path for management audit events endpoints. */
    private const val BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE =
        "${CommonNetworkRoutes.BASE_MANAGEMENT_NETWORK_ROUTE}/audit/events"

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of condensed audit events.
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of
     *   [AuditSortValues.AuditEventSortBy.CREATED_AT],
     *   [AuditSortValues.AuditEventSortBy.UPDATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or [CommonApiFields.SortOrder.DESC].
     *
     * - **Filters** ([AuditFilterValues.AuditEventFilterValues]): all filters are optional.
     *   Multi-value filters use repeated query keys with **OR** semantics (e.g., `?actor_id=1&actor_id=2`);
     *   different keys combine as **AND**.
     *   - [AuditFilterValues.AuditEventFilterValues.ACTOR_ID] — filter by actor ID.
     *   - [AuditFilterValues.AuditEventFilterValues.ACTOR_TYPE] — filter by [AuditActorType] serial name.
     *   - [AuditFilterValues.AuditEventFilterValues.ACTOR_USER_ROLE] — filter by user role serial name.
     *   - [AuditFilterValues.AuditEventFilterValues.ACTION] — filter by audit action name.
     *   - [AuditFilterValues.AuditEventFilterValues.RESOURCE] — filter by audit resource name.
     *   - [AuditFilterValues.AuditEventFilterValues.RESOURCE_ID] — filter by resource ID.
     *   - [AuditFilterValues.AuditEventFilterValues.STATUS] — filter by [AuditStatus] serial name.
     *   - [AuditFilterValues.AuditEventFilterValues.MESSAGE] — filter by message substring pattern.
     *
     * Request body: None.
     * Response body: [PagedResult] of [AuditEventSummaryPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE`, `UserAccountStatus.READ_ONLY` (**OR** semantics).
     * - **Required Permissions:** Based on the event's actor type:
     *   - For [AuditActorType.USER]: [AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_MASKED]
     *     or [AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_UNMASKED].
     *   - For [AuditActorType.SYSTEM]: [AuditPermissionCode.AUDIT_GET_FOR_SYSTEM_ACTOR_MASKED]
     *     or [AuditPermissionCode.AUDIT_GET_FOR_SYSTEM_ACTOR_UNMASKED].
     *   - For [AuditActorType.SERVICE]: [AuditPermissionCode.AUDIT_GET_FOR_SERVICE_ACTOR_MASKED]
     *     or [AuditPermissionCode.AUDIT_GET_FOR_SERVICE_ACTOR_UNMASKED].
     *   - For Staff actors: [AuditPermissionCode.AUDIT_GET_FOR_STAFF_ACTOR_MASKED]
     *     or [AuditPermissionCode.AUDIT_GET_FOR_STAFF_ACTOR_UNMASKED].
     *   - For Admin actors: [AuditPermissionCode.AUDIT_GET_FOR_ADMIN_ACTOR_MASKED]
     *     or [AuditPermissionCode.AUDIT_GET_FOR_ADMIN_ACTOR_UNMASKED].
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementAuditNavigation.AuditEventList]
     */
    const val GET_AUDIT_EVENTS = BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves specific audit event details.
     *
     * Path parameter: [AuditApiPaths.EVENT_ID].
     *
     * Request body: None.
     * Response body: [AuditEventPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE`, `UserAccountStatus.READ_ONLY` (**OR** semantics).
     * - **Required Permissions:** Based on the event's actor type:
     *   - For [AuditActorType.USER]: [AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_MASKED]
     *     or [AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_UNMASKED].
     *   - For [AuditActorType.SYSTEM]: [AuditPermissionCode.AUDIT_GET_FOR_SYSTEM_ACTOR_MASKED]
     *     or [AuditPermissionCode.AUDIT_GET_FOR_SYSTEM_ACTOR_UNMASKED].
     *   - For [AuditActorType.SERVICE]: [AuditPermissionCode.AUDIT_GET_FOR_SERVICE_ACTOR_MASKED]
     *     or [AuditPermissionCode.AUDIT_GET_FOR_SERVICE_ACTOR_UNMASKED].
     *   - For Staff actors: [AuditPermissionCode.AUDIT_GET_FOR_STAFF_ACTOR_MASKED]
     *     or [AuditPermissionCode.AUDIT_GET_FOR_STAFF_ACTOR_UNMASKED].
     *   - For Admin actors: [AuditPermissionCode.AUDIT_GET_FOR_ADMIN_ACTOR_MASKED]
     *     or [AuditPermissionCode.AUDIT_GET_FOR_ADMIN_ACTOR_UNMASKED].
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementAuditNavigation.AuditEventDetail]
     */
    const val GET_AUDIT_EVENT = "$BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE/{${AuditApiPaths.EVENT_ID}}"
}