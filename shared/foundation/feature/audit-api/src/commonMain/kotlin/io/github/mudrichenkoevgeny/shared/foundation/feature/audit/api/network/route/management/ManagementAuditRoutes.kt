package io.github.mudrichenkoevgeny.shared.foundation.feature.audit.api.network.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.audit.api.domain.permissions.AuditPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.audit.api.network.route.base.BaseAuditRoutes

/** Management API route constants for audit. */
object ManagementAuditRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Paginated [AuditEventPayload] list. Output depends on the caller’s [AuditPermissionCode] grants.
     *
     * **Pagination** ([ListingParamNames.Pagination]): [ListingParamNames.Pagination.PAGE_NUMBER],
     * [ListingParamNames.Pagination.PAGE_SIZE].
     *
     * **Sort** ([ListingParamNames.Sort]): [ListingParamNames.Sort.SORT_BY] ([AuditSortValues.AuditEventSortBy.CREATED_AT],
     * [AuditEventPayload.createdAt]); [ListingParamNames.Sort.SORT_ORDER] ([CommonApiFields.SortOrder.ASC] /
     * [CommonApiFields.SortOrder.DESC]).
     *
     * **Filters** ([AuditFilterValues.AuditEventFilterValues], optional; same key repeated = **OR**;  different keys combine as **AND**.
     * - [AuditFilterValues.AuditEventFilterValues.ACTOR_ID] — list of actor IDs.
     * - [AuditFilterValues.AuditEventFilterValues.ACTOR_TYPE] — list of [AuditActorType] serial names.
     * - [AuditFilterValues.AuditEventFilterValues.ACTOR_USER_ROLE] — list of user roles.
     * - [AuditFilterValues.AuditEventFilterValues.ACTION] — list of audit actions.
     * - [AuditFilterValues.AuditEventFilterValues.RESOURCE] — list of audit resources.
     * - [AuditFilterValues.AuditEventFilterValues.RESOURCE_ID] — list of resource IDs.
     * - [AuditFilterValues.AuditEventFilterValues.STATUS] — list of [AuditStatus] serial names.
     * - [AuditFilterValues.AuditEventFilterValues.MESSAGE] — list of free-text messages; server-defined.
     *
     * Response body: [PagedResult] of [AuditEventPayload].
     */
    const val GET_AUDIT_EVENTS = BaseAuditRoutes.GET_AUDIT_EVENTS

    /**
     * **HTTP method:** `GET`
     *
     * Single [AuditEventPayload]. Output depends on the caller’s [AuditPermissionCode] grants.
     *
     * Path parameter: [AuditApiPaths.EVENT_ID].
     *
     * Response body: [AuditEventPayload].
     */
    const val GET_AUDIT_EVENT = BaseAuditRoutes.GET_AUDIT_EVENT
}
