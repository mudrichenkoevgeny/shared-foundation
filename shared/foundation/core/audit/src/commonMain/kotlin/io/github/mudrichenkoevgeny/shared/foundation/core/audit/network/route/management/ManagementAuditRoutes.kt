package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditEventFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditEventSortBy
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
public object ManagementAuditRoutes {
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
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of:
     *   - [AuditEventSortBy.CREATED_AT]
     * - [ListingParamNames.Sort.SORT_ORDER] — exactly one of:
     *   - [CommonApiFields.SortOrder.ASC]
     *   - [CommonApiFields.SortOrder.DESC]
     *
     * - **Filters** ([AuditEventFilterValues]): all filters are optional.
     *   Multi-value filters use repeated query keys with **OR** semantics; different keys combine as **AND**.
     *   - **Multi-value Include Filters** (repeated query keys, **OR** semantics):
     *     - [AuditEventFilterValues.EVENT_ID] — filter by one or more specific audit event record IDs.
     *     - [AuditEventFilterValues.ACTOR_ID] — filter by one or more actor IDs.
     *     - [AuditEventFilterValues.ACTOR_TYPE] — filter by one or more [AuditActorType] serial names.
     *     - [AuditEventFilterValues.ACTOR_USER_ROLE] — filter by one or more user actor role snapshots.
     *     - [AuditEventFilterValues.ACTION] — filter by one or more audit action names.
     *     - [AuditEventFilterValues.RESOURCE] — filter by one or more audit resource names.
     *     - [AuditEventFilterValues.RESOURCE_ID] — filter by one or more resource instance IDs.
     *     - [AuditEventFilterValues.STATUS] — filter by one or more [AuditStatus] serial names.
     *   - **Multi-value Exclude Filters** (repeated query keys, **NOT IN** semantics):
     *     - [AuditEventFilterValues.EXCLUDE_ACTOR_TYPE] — exclude specific [AuditActorType] serial names.
     *     - [AuditEventFilterValues.EXCLUDE_ACTION] — exclude specific audit action names.
     *     - [AuditEventFilterValues.EXCLUDE_STATUS] — exclude specific [AuditStatus] serial names.
     *   - **Range Bounds** (inclusive bounds):
     *     - [AuditEventFilterValues.CREATED_AT_FROM] — inclusive lower bound creation timestamp.
     *     - [AuditEventFilterValues.CREATED_AT_TO] — inclusive upper bound creation timestamp.
     *   - **Free-text Search**:
     *     - [AuditEventFilterValues.SEARCH_QUERY] — free-text search query string targeting:
     *       - Event human-readable message ([AuditEventPrivate.message]).
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
     *
     * @see AuditStatus
     * @see AuditEventPrivate
     */
    public const val GET_AUDIT_EVENTS: String = BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE

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
    public const val GET_AUDIT_EVENT: String = "$BASE_MANAGEMENT_AUDIT_EVENTS_ROUTE/{${AuditApiPaths.EVENT_ID}}"
}
