package io.github.mudrichenkoevgeny.shared.foundation.testing.core.audit.navigation.screen.management.audit.list

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditEventSortBy
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
 * @param eventIds Filter event IDs list.
 * @param actorIds Filter actor IDs list.
 * @param actorTypes Filter actor types list ([AuditActorType]).
 * @param excludeActorTypes Exclude actor types list ([AuditActorType]).
 * @param actorUserRoles Filter actor user roles list.
 * @param actions Filter audit actions list.
 * @param excludeActions Exclude audit actions list.
 * @param resources Filter audit resources list.
 * @param resourceIds Filter resource IDs list.
 * @param statuses Filter audit statuses list ([AuditStatus]).
 * @param excludeStatuses Exclude audit statuses list ([AuditStatus]).
 * @param createdAtFrom Lower bound creation timestamp.
 * @param createdAtTo Upper bound creation timestamp.
 * @param searchQuery Free-text search query string.
 */
public fun createTestManagementAuditEventListParams(
    pageNumber: Int = ListingDefaultValues.Pagination.INITIAL_PAGE_NUMBER,
    pageSize: Int = ListingDefaultValues.Pagination.DEFAULT_PAGE_SIZE,
    sortBy: AuditEventSortBy = AuditEventSortBy.DEFAULT,
    sortOrder: SortOrder = SortOrder.DEFAULT,
    eventIds: List<String> = emptyList(),
    actorIds: List<String> = emptyList(),
    actorTypes: List<AuditActorType> = emptyList(),
    excludeActorTypes: List<AuditActorType> = emptyList(),
    actorUserRoles: List<String> = emptyList(),
    actions: List<String> = emptyList(),
    excludeActions: List<String> = emptyList(),
    resources: List<String> = emptyList(),
    resourceIds: List<String> = emptyList(),
    statuses: List<AuditStatus> = emptyList(),
    excludeStatuses: List<AuditStatus> = emptyList(),
    createdAtFrom: String? = null,
    createdAtTo: String? = null,
    searchQuery: String? = null
): ManagementAuditEventListParams =
    ManagementAuditEventListParams(
        pageNumber = pageNumber,
        pageSize = pageSize,
        sortBy = sortBy,
        sortOrder = sortOrder,
        eventIds = eventIds,
        actorIds = actorIds,
        actorTypes = actorTypes,
        excludeActorTypes = excludeActorTypes,
        actorUserRoles = actorUserRoles,
        actions = actions,
        excludeActions = excludeActions,
        resources = resources,
        resourceIds = resourceIds,
        statuses = statuses,
        excludeStatuses = excludeStatuses,
        createdAtFrom = createdAtFrom,
        createdAtTo = createdAtTo,
        searchQuery = searchQuery
    )
