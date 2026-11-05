package io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.list

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditEventFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditEventSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementAuditEventListParamsTest {
    @Test
    fun `parse returns default params when map is empty`() {
        val parsed = ManagementAuditEventListParams.parse(emptyMap())
        assertEquals(ManagementAuditEventListParams(), parsed)
    }

    @Test
    fun `parse extracts typed parameters correctly`() {
        val rawParams =
            mapOf(
                ListingParamNames.Pagination.PAGE_NUMBER to listOf("2"),
                ListingParamNames.Pagination.PAGE_SIZE to listOf("50"),
                ListingParamNames.Sort.SORT_BY to listOf("created_at"),
                ListingParamNames.Sort.SORT_ORDER to listOf("asc"),
                AuditEventFilterValues.EVENT_ID to listOf("evt_123"),
                AuditEventFilterValues.ACTOR_ID to listOf("usr_123"),
                AuditEventFilterValues.ACTOR_TYPE to listOf("user", "system"),
                AuditEventFilterValues.EXCLUDE_ACTOR_TYPE to listOf("service"),
                AuditEventFilterValues.ACTION to listOf("user.login"),
                AuditEventFilterValues.EXCLUDE_ACTION to listOf("cron.tick"),
                AuditEventFilterValues.STATUS to listOf("success", "failed"),
                AuditEventFilterValues.EXCLUDE_STATUS to listOf("denied"),
                AuditEventFilterValues.CREATED_AT_FROM to listOf("2026-01-01T00:00:00Z"),
                AuditEventFilterValues.CREATED_AT_TO to listOf("2026-03-01T00:00:00Z"),
                AuditEventFilterValues.SEARCH_QUERY to listOf("invalid password")
            )

        val parsed = ManagementAuditEventListParams.parse(rawParams)

        assertEquals(2, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(AuditEventSortBy.CREATED_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf("evt_123"), parsed.eventIds)
        assertEquals(listOf("usr_123"), parsed.actorIds)
        assertEquals(listOf(AuditActorType.USER, AuditActorType.SYSTEM), parsed.actorTypes)
        assertEquals(listOf(AuditActorType.SERVICE), parsed.excludeActorTypes)
        assertEquals(listOf("user.login"), parsed.actions)
        assertEquals(listOf("cron.tick"), parsed.excludeActions)
        assertEquals(listOf(AuditStatus.SUCCESS, AuditStatus.FAILED), parsed.statuses)
        assertEquals(listOf(AuditStatus.DENIED), parsed.excludeStatuses)
        assertEquals("2026-01-01T00:00:00Z", parsed.createdAtFrom)
        assertEquals("2026-03-01T00:00:00Z", parsed.createdAtTo)
        assertEquals("invalid password", parsed.searchQuery)
    }

    @Test
    fun `parse extracts single string values from JS style map`() {
        val rawParams =
            mapOf(
                ListingParamNames.Pagination.PAGE_NUMBER to "3",
                ListingParamNames.Pagination.PAGE_SIZE to "25",
                AuditEventFilterValues.ACTOR_ID to "usr_789",
                AuditEventFilterValues.STATUS to "denied"
            )

        val parsed = ManagementAuditEventListParams.parse(rawParams)

        assertEquals(3, parsed.pageNumber)
        assertEquals(25, parsed.pageSize)
        assertEquals(listOf("usr_789"), parsed.actorIds)
        assertEquals(listOf(AuditStatus.DENIED), parsed.statuses)
    }

    @Test
    fun `parse gracefully handles invalid values`() {
        val rawParams =
            mapOf(
                ListingParamNames.Pagination.PAGE_NUMBER to listOf("-1"),
                ListingParamNames.Sort.SORT_BY to listOf("invalid_field"),
                ListingParamNames.Sort.SORT_ORDER to listOf("unknown_order"),
                AuditEventFilterValues.STATUS to listOf("invalid_status", "success")
            )

        val parsed = ManagementAuditEventListParams.parse(rawParams)

        assertEquals(1, parsed.pageNumber)
        assertEquals(AuditEventSortBy.DEFAULT, parsed.sortBy)
        assertEquals(SortOrder.DEFAULT, parsed.sortOrder)
        assertEquals(listOf(AuditStatus.SUCCESS), parsed.statuses)
    }

    @Test
    fun `serialize and parse roundtrip maintains equality`() {
        val original =
            ManagementAuditEventListParams(
                pageNumber = 3,
                pageSize = 30,
                sortBy = AuditEventSortBy.CREATED_AT,
                sortOrder = SortOrder.ASC,
                eventIds = listOf("evt_999"),
                actorIds = listOf("usr_456"),
                actorTypes = listOf(AuditActorType.SERVICE),
                excludeActorTypes = listOf(AuditActorType.SYSTEM),
                actions = listOf("user.delete"),
                excludeActions = listOf("user.get"),
                statuses = listOf(AuditStatus.DENIED),
                excludeStatuses = listOf(AuditStatus.SUCCESS),
                createdAtFrom = "2026-01-01T00:00:00Z",
                createdAtTo = "2026-03-01T00:00:00Z",
                searchQuery = "failed login"
            )

        val serialized = ManagementAuditEventListParams.serialize(original)
        val restored = ManagementAuditEventListParams.parse(serialized)

        assertEquals(original, restored)
    }
}
