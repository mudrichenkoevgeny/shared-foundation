package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.navigation.screen.management.audit.list

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues.AuditEventSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.list.ManagementAuditEventListParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder

/**
 * Creates a test [ManagementAuditEventListParams] navigation params instance.
 *
 * @param pageNumber 1-based page index.
 * @param pageSize Number of items per page.
 * @param sortBy Sort field ([AuditEventSortBy]).
 * @param sortOrder Sort direction ([SortOrder]).
 * @param actorIds Filter actor IDs list.
 * @param actorTypes Filter actor types list ([AuditActorType]).
 * @param actorUserRoles Filter actor user roles list.
 * @param actions Filter audit actions list.
 * @param resources Filter audit resources list.
 * @param resourceIds Filter resource IDs list.
 * @param statuses Filter audit statuses list ([AuditStatus]).
 * @param messages Filter audit messages list.
 */
fun createTestManagementAuditEventListParams(
    pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    sortBy: AuditEventSortBy = AuditEventSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    actorIds: List<String> = emptyList(),
    actorTypes: List<AuditActorType> = emptyList(),
    actorUserRoles: List<String> = emptyList(),
    actions: List<String> = emptyList(),
    resources: List<String> = emptyList(),
    resourceIds: List<String> = emptyList(),
    statuses: List<AuditStatus> = emptyList(),
    messages: List<String> = emptyList()
): ManagementAuditEventListParams = ManagementAuditEventListParams(
    pageNumber = pageNumber,
    pageSize = pageSize,
    sortBy = sortBy,
    sortOrder = sortOrder,
    actorIds = actorIds,
    actorTypes = actorTypes,
    actorUserRoles = actorUserRoles,
    actions = actions,
    resources = resources,
    resourceIds = resourceIds,
    statuses = statuses,
    messages = messages
)
