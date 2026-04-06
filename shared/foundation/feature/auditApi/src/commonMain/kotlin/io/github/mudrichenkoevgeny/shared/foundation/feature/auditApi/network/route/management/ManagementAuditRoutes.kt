package io.github.mudrichenkoevgeny.shared.foundation.feature.auditApi.network.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.auditApi.domain.permissions.AuditPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.auditApi.network.route.base.BaseAuditRoutes

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
     * **Filters** ([AuditFilterValues.AuditEventFilterValues], optional; same key repeated = **OR**; different keys =
     * **AND**): [AuditFilterValues.AuditEventFilterValues.ACTOR_ID], [AuditFilterValues.AuditEventFilterValues.ACTOR_TYPE]
     * ([AuditActorType]), [AuditFilterValues.AuditEventFilterValues.ACTOR_USER_ROLE],
     * [AuditFilterValues.AuditEventFilterValues.ACTION], [AuditFilterValues.AuditEventFilterValues.RESOURCE],
     * [AuditFilterValues.AuditEventFilterValues.RESOURCE_ID], [AuditFilterValues.AuditEventFilterValues.STATUS]
     * ([AuditStatus]), [AuditFilterValues.AuditEventFilterValues.MESSAGE].
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
