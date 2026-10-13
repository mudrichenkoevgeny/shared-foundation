package io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.list

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues.AuditEventFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues.AuditEventSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import kotlin.test.Test
import kotlin.test.assertEquals

class ManagementAuditEventListParamsParserTest {

    @Test
    fun `parse returns default params when map is empty`() {
        val parsed = ManagementAuditEventListParamsParser.parse(emptyMap())
        assertEquals(ManagementAuditEventListParams(), parsed)
    }

    @Test
    fun `parse extracts typed parameters correctly`() {
        val rawParams = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to listOf("2"),
            ListingParamNames.Pagination.PAGE_SIZE to listOf("50"),
            ListingParamNames.Sort.SORT_BY to listOf("updated_at"),
            ListingParamNames.Sort.SORT_ORDER to listOf("asc"),
            AuditEventFilterValues.ACTOR_ID to listOf("usr_123"),
            AuditEventFilterValues.ACTOR_TYPE to listOf("user", "system"),
            AuditEventFilterValues.STATUS to listOf("success", "failed")
        )

        val parsed = ManagementAuditEventListParamsParser.parse(rawParams)

        assertEquals(2, parsed.pageNumber)
        assertEquals(50, parsed.pageSize)
        assertEquals(AuditEventSortBy.UPDATED_AT, parsed.sortBy)
        assertEquals(SortOrder.ASC, parsed.sortOrder)
        assertEquals(listOf("usr_123"), parsed.actorIds)
        assertEquals(listOf(AuditActorType.USER, AuditActorType.SYSTEM), parsed.actorTypes)
        assertEquals(listOf(AuditStatus.SUCCESS, AuditStatus.FAILED), parsed.statuses)
    }

    @Test
    fun `parse extracts single string values from JS style map`() {
        val rawParams = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to "3",
            ListingParamNames.Pagination.PAGE_SIZE to "25",
            AuditEventFilterValues.ACTOR_ID to "usr_789",
            AuditEventFilterValues.STATUS to "denied"
        )

        val parsed = ManagementAuditEventListParamsParser.parse(rawParams)

        assertEquals(3, parsed.pageNumber)
        assertEquals(25, parsed.pageSize)
        assertEquals(listOf("usr_789"), parsed.actorIds)
        assertEquals(listOf(AuditStatus.DENIED), parsed.statuses)
    }

    @Test
    fun `parse gracefully handles invalid values`() {
        val rawParams = mapOf(
            ListingParamNames.Pagination.PAGE_NUMBER to listOf("-1"),
            ListingParamNames.Sort.SORT_BY to listOf("invalid_field"),
            ListingParamNames.Sort.SORT_ORDER to listOf("unknown_order"),
            AuditEventFilterValues.STATUS to listOf("invalid_status", "success")
        )

        val parsed = ManagementAuditEventListParamsParser.parse(rawParams)

        assertEquals(1, parsed.pageNumber)
        assertEquals(AuditEventSortBy.DEFAULT, parsed.sortBy)
        assertEquals(SortOrder.DEFAULT, parsed.sortOrder)
        assertEquals(listOf(AuditStatus.SUCCESS), parsed.statuses)
    }

    @Test
    fun `serialize and parse roundtrip maintains equality`() {
        val original = ManagementAuditEventListParams(
            pageNumber = 3,
            pageSize = 30,
            sortBy = AuditEventSortBy.UPDATED_AT,
            sortOrder = SortOrder.ASC,
            actorIds = listOf("usr_456"),
            actorTypes = listOf(AuditActorType.SERVICE),
            statuses = listOf(AuditStatus.DENIED)
        )

        val serialized = ManagementAuditEventListParamsParser.serialize(original)
        val restored = ManagementAuditEventListParamsParser.parse(serialized)

        assertEquals(original, restored)
    }
}
