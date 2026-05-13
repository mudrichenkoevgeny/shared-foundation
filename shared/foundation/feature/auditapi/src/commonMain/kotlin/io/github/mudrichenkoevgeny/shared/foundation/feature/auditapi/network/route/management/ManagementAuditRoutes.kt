package io.github.mudrichenkoevgeny.shared.foundation.feature.auditapi.network.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.PagedResult
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.auditapi.domain.permissions.AuditPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.auditapi.network.route.base.BaseAuditRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole

/** Management API route constants for audit. */
object ManagementAuditRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Returns a paged list of audit events.
     *
     * **Pagination & sort** (names from [ListingParamNames]):
     * - [ListingParamNames.Pagination.PAGE_NUMBER] — one-based page index (`1` is the first page).
     * - [ListingParamNames.Pagination.PAGE_SIZE] — page size.
     * - [ListingParamNames.Sort.SORT_BY] — exactly one of [AuditSortValues.AuditEventSortBy.CREATED_AT].
     * - [ListingParamNames.Sort.SORT_ORDER] — [CommonApiFields.SortOrder.ASC] or [CommonApiFields.SortOrder.DESC].
     *
     * **Filters** ([AuditFilterValues.AuditEventFilterValues]): all filters are optional.
     * Same key repeated — **OR**; different keys — **AND**.
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
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     * (**OR** semantics).
     * - **Required Permissions:** Based on the event's actor type:
     * - For [AuditActorType.USER]: [AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_MASKED]
     * or [AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_UNMASKED].
     * - For [AuditActorType.SYSTEM]: [AuditPermissionCode.AUDIT_GET_FOR_SYSTEM_ACTOR_MASKED]
     * or [AuditPermissionCode.AUDIT_GET_FOR_SYSTEM_ACTOR_UNMASKED].
     * - For [AuditActorType.SERVICE]: [AuditPermissionCode.AUDIT_GET_FOR_SERVICE_ACTOR_MASKED]
     * or [AuditPermissionCode.AUDIT_GET_FOR_SERVICE_ACTOR_UNMASKED].
     * - For Staff actors: [AuditPermissionCode.AUDIT_GET_FOR_STAFF_ACTOR_MASKED]
     * or [AuditPermissionCode.AUDIT_GET_FOR_STAFF_ACTOR_UNMASKED].
     * - For Admin actors: [AuditPermissionCode.AUDIT_GET_FOR_ADMIN_ACTOR_MASKED]
     * or [AuditPermissionCode.AUDIT_GET_FOR_ADMIN_ACTOR_UNMASKED].
     */
    const val GET_AUDIT_EVENTS = BaseAuditRoutes.GET_AUDIT_EVENTS

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves specific audit event details.
     *
     * Path parameter: [AuditApiPaths.EVENT_ID].
     *
     * Response body: [AuditEventPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     * (**OR** semantics).
     * - **Required Permissions:** Based on the event's actor type:
     * - For [AuditActorType.USER]: [AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_MASKED]
     * or [AuditPermissionCode.AUDIT_GET_FOR_USER_ACTOR_UNMASKED].
     * - For [AuditActorType.SYSTEM]: [AuditPermissionCode.AUDIT_GET_FOR_SYSTEM_ACTOR_MASKED]
     * or [AuditPermissionCode.AUDIT_GET_FOR_SYSTEM_ACTOR_UNMASKED].
     * - For [AuditActorType.SERVICE]: [AuditPermissionCode.AUDIT_GET_FOR_SERVICE_ACTOR_MASKED]
     * or [AuditPermissionCode.AUDIT_GET_FOR_SERVICE_ACTOR_UNMASKED].
     * - For Staff actors: [AuditPermissionCode.AUDIT_GET_FOR_STAFF_ACTOR_MASKED]
     * or [AuditPermissionCode.AUDIT_GET_FOR_STAFF_ACTOR_UNMASKED].
     * - For Admin actors: [AuditPermissionCode.AUDIT_GET_FOR_ADMIN_ACTOR_MASKED]
     * or [AuditPermissionCode.AUDIT_GET_FOR_ADMIN_ACTOR_UNMASKED].
     */
    const val GET_AUDIT_EVENT = BaseAuditRoutes.GET_AUDIT_EVENT
}