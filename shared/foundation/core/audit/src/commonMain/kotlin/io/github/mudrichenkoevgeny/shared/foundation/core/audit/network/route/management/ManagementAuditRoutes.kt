package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.base.BaseAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult

/**
 * Route paths for audit in the management API.
 */
object ManagementAuditRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Returns a paginated list of audit events with **masked** sensitive fields ([AuditEventPayload]) per server policy.
     * Caller must hold the **masked** list-read permission for this surface.
     *
     * **Pagination** (names from [ListingParamNames.Pagination]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     *
     * **Sort** (names from [ListingParamNames.Sort]):
     * - [ListingParamNames.Sort.SORT_BY] — [AuditSortValues.AuditEventSortBy.CREATED_AT]
     *   ([AuditEventPayload.createdAt]).
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     *   [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([AuditFilterValues.AuditEventFilterValues], optional; absent = no filter on that axis; repeat key =
     * **OR**, different keys = **AND**).
     *
     * - [AuditFilterValues.AuditEventFilterValues.ACTOR_ID] — [AuditEventPayload.actorId].
     * - [AuditFilterValues.AuditEventFilterValues.ACTOR_TYPE] — [AuditEventPayload.actorType] ([AuditActorType] wire).
     * - [AuditFilterValues.AuditEventFilterValues.ACTOR_USER_ROLE] — [AuditEventPayload.actorUserRole].
     * - [AuditFilterValues.AuditEventFilterValues.ACTION] — [AuditEventPayload.action].
     * - [AuditFilterValues.AuditEventFilterValues.RESOURCE] — [AuditEventPayload.resource].
     * - [AuditFilterValues.AuditEventFilterValues.RESOURCE_ID] — [AuditEventPayload.resourceId].
     * - [AuditFilterValues.AuditEventFilterValues.STATUS] — [AuditEventPayload.status] ([AuditStatus] wire).
     * - [AuditFilterValues.AuditEventFilterValues.MESSAGE] — [AuditEventPayload.message] (typically substring).
     *
     * Response body: [PagedResult] of [AuditEventPayload].
     */
    const val GET_AUDIT_EVENTS_MASKED = BaseAuditRoutes.GET_AUDIT_EVENTS_MASKED

    /**
     * **HTTP method:** `GET`
     *
     * Returns a paginated list of audit events with **unmasked** sensitive fields ([AuditEventPayload]). Caller must hold
     * the **unmasked** list-read permission for this surface.
     *
     * **Pagination** (names from [ListingParamNames.Pagination]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     *
     * **Sort** (names from [ListingParamNames.Sort]):
     * - [ListingParamNames.Sort.SORT_BY] — [AuditSortValues.AuditEventSortBy.CREATED_AT]
     *   ([AuditEventPayload.createdAt]).
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or
     *   [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([AuditFilterValues.AuditEventFilterValues], optional; absent = no filter on that axis; repeat key =
     * **OR**, different keys = **AND**).
     *
     * - [AuditFilterValues.AuditEventFilterValues.ACTOR_ID] — [AuditEventPayload.actorId].
     * - [AuditFilterValues.AuditEventFilterValues.ACTOR_TYPE] — [AuditEventPayload.actorType] ([AuditActorType] wire).
     * - [AuditFilterValues.AuditEventFilterValues.ACTOR_USER_ROLE] — [AuditEventPayload.actorUserRole].
     * - [AuditFilterValues.AuditEventFilterValues.ACTION] — [AuditEventPayload.action].
     * - [AuditFilterValues.AuditEventFilterValues.RESOURCE] — [AuditEventPayload.resource].
     * - [AuditFilterValues.AuditEventFilterValues.RESOURCE_ID] — [AuditEventPayload.resourceId].
     * - [AuditFilterValues.AuditEventFilterValues.STATUS] — [AuditEventPayload.status] ([AuditStatus] wire).
     * - [AuditFilterValues.AuditEventFilterValues.MESSAGE] — [AuditEventPayload.message] (typically substring).
     *
     * Response body: [PagedResult] of [AuditEventPayload].
     */
    const val GET_AUDIT_EVENTS_UNMASKED = BaseAuditRoutes.GET_AUDIT_EVENTS_UNMASKED

    /**
     * **HTTP method:** `GET`
     *
     * Returns a single persisted audit event with **masked** sensitive fields.
     *
     * Path parameter: [AuditApiPaths.EVENT_ID].
     *
     * Response body: [AuditEventPayload].
     */
    const val GET_AUDIT_EVENT_MASKED = BaseAuditRoutes.GET_AUDIT_EVENT_MASKED

    /**
     * **HTTP method:** `GET`
     *
     * Returns a single persisted audit event with **unmasked** sensitive fields.
     *
     * Path parameter: [AuditApiPaths.EVENT_ID].
     *
     * Response body: [AuditEventPayload].
     */
    const val GET_AUDIT_EVENT_UNMASKED = BaseAuditRoutes.GET_AUDIT_EVENT_UNMASKED
}
