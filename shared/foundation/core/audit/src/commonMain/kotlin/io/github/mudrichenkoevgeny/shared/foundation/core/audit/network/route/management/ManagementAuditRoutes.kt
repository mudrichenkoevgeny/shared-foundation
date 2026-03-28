package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.response.AuditEventResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.base.BaseAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParamValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiQueryParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.listing.PagedResponse

/**
 * Route paths for audit in the management API.
 */
object ManagementAuditRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * **Pagination** (names from [CommonApiQueryParams.PaginationQueryParams]):
     * - [CommonApiQueryParams.PaginationQueryParams.PAGE] — zero-based page index.
     * - [CommonApiQueryParams.PaginationQueryParams.PAGE_SIZE] — page size.
     *
     * **Sort** (names from [CommonApiQueryParams.SortQueryParams]):
     * - [CommonApiQueryParams.SortQueryParams.SORT_BY] — [CommonApiQueryParamValues.TimestampSortBy.CREATED_AT]
     *   ([AuditEventResponse.createdAt]).
     * - [CommonApiQueryParams.SortQueryParams.SORT_ORDER] — [CommonApiQueryParamValues.SortOrder.ASC] or
     *   [CommonApiQueryParamValues.SortOrder.DESC].
     *
     * **Filters** ([AuditApiQueryParams.AuditEventQueryParams], optional). If omitted, no filtering on that axis (all
     * events matching caller access, then pagination/sort). Same key repeated means **OR**; different keys combine as **AND**.
     *
     * - [AuditApiQueryParams.AuditEventQueryParams.ACTOR_ID] — [AuditEventResponse.actorId] (UUID string).
     * - [AuditApiQueryParams.AuditEventQueryParams.ACTION] — [AuditEventResponse.action].
     * - [AuditApiQueryParams.AuditEventQueryParams.RESOURCE] — [AuditEventResponse.resource].
     * - [AuditApiQueryParams.AuditEventQueryParams.RESOURCE_ID] — [AuditEventResponse.resourceId].
     * - [AuditApiQueryParams.AuditEventQueryParams.STATUS] — [AuditStatus] serial name ([AuditEventResponse.status]).
     * - [AuditApiQueryParams.AuditEventQueryParams.MESSAGE] — optional substring match on [AuditEventResponse.message].
     *
     * Response body: [PagedResponse] of [AuditEventResponse].
     */
    const val GET_AUDIT_EVENTS = BaseAuditRoutes.GET_AUDIT_EVENTS
}
