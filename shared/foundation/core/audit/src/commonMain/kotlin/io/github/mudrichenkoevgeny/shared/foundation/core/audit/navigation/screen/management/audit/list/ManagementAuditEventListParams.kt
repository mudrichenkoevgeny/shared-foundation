package io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.list

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues.AuditEventFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues.AuditEventSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.NavigationScreenParamsParser
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.parser.normalizeQueryParams
import kotlinx.serialization.Serializable

/**
 * Strongly typed parsed parameters for the management audit events list screen and API query.
 *
 * @property pageNumber One-based page index; wire key is [ListingParamNames.Pagination.PAGE_NUMBER].
 * @property pageSize Maximum items returned per page; wire key is [ListingParamNames.Pagination.PAGE_SIZE].
 * @property sortBy Field to sort listing results by; wire key is [ListingParamNames.Sort.SORT_BY].
 * @property sortOrder Direction of sorting; wire key is [ListingParamNames.Sort.SORT_ORDER].
 * @property actorIds List of actor IDs to filter by; wire key is [AuditFilterValues.AuditEventFilterValues.ACTOR_ID].
 * @property actorTypes List of [AuditActorType] items to filter by; wire key is [AuditFilterValues.AuditEventFilterValues.ACTOR_TYPE].
 * @property actorUserRoles List of actor user roles to filter by; wire key is [AuditFilterValues.AuditEventFilterValues.ACTOR_USER_ROLE].
 * @property actions List of audit action names to filter by; wire key is [AuditFilterValues.AuditEventFilterValues.ACTION].
 * @property resources List of audit resource names to filter by; wire key is [AuditFilterValues.AuditEventFilterValues.RESOURCE].
 * @property resourceIds List of resource IDs to filter by; wire key is [AuditFilterValues.AuditEventFilterValues.RESOURCE_ID].
 * @property statuses List of [AuditStatus] items to filter by; wire key is [AuditFilterValues.AuditEventFilterValues.STATUS].
 * @property messages List of free-text message filters; wire key is [AuditFilterValues.AuditEventFilterValues.MESSAGE].
 */
@Serializable
data class ManagementAuditEventListParams(
    val pageNumber: Int = ListingDefaultValues.Pagination.PAGE_NUMBER,
    val pageSize: Int = ListingDefaultValues.Pagination.PAGE_SIZE,
    val sortBy: AuditEventSortBy = AuditEventSortBy.DEFAULT,
    val sortOrder: SortOrder = SortOrder.DEFAULT,
    val actorIds: List<String> = emptyList(),
    val actorTypes: List<AuditActorType> = emptyList(),
    val actorUserRoles: List<String> = emptyList(),
    val actions: List<String> = emptyList(),
    val resources: List<String> = emptyList(),
    val resourceIds: List<String> = emptyList(),
    val statuses: List<AuditStatus> = emptyList(),
    val messages: List<String> = emptyList()
) : NavigationScreenParams<ManagementAuditEventListParams> {

    override val parser: NavigationScreenParamsParser<ManagementAuditEventListParams>
        get() = Companion

    companion object : NavigationScreenParamsParser<ManagementAuditEventListParams> {

        /**
         * Parses raw URL query parameters into typed [ManagementAuditEventListParams].
         *
         * Accepts both canonical `Map<String, List<String>>` and flexible `Map<String, Any?>`
         * (e.g. single strings or arrays from JS/TS query objects).
         *
         * @param params Map of query parameter keys to raw parameter values (single strings, lists, arrays, or nulls).
         * @return Validated [ManagementAuditEventListParams] instance with defaults applied.
         */
        override fun parse(params: Map<String, Any?>): ManagementAuditEventListParams {
            val normalized = params.normalizeQueryParams()

            val pageNumber = normalized[ListingParamNames.Pagination.PAGE_NUMBER]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it > 0 }
                ?: ListingDefaultValues.Pagination.PAGE_NUMBER

            val pageSize = normalized[ListingParamNames.Pagination.PAGE_SIZE]
                ?.firstOrNull()
                ?.toIntOrNull()
                ?.takeIf { it > 0 }
                ?: ListingDefaultValues.Pagination.PAGE_SIZE

            val sortBy = normalized[ListingParamNames.Sort.SORT_BY]
                ?.firstOrNull()
                ?.let { AuditEventSortBy.fromValueOrNull(it) }
                ?: AuditEventSortBy.DEFAULT

            val sortOrder = normalized[ListingParamNames.Sort.SORT_ORDER]
                ?.firstOrNull()
                ?.let { SortOrder.fromValueOrNull(it) }
                ?: SortOrder.DEFAULT

            val actorIds = normalized[AuditEventFilterValues.ACTOR_ID]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val actorTypes = normalized[AuditEventFilterValues.ACTOR_TYPE]
                ?.mapNotNull { AuditActorType.fromValueOrNull(it) }
                ?: emptyList()

            val actorUserRoles = normalized[AuditEventFilterValues.ACTOR_USER_ROLE]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val actions = normalized[AuditEventFilterValues.ACTION]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val resources = normalized[AuditEventFilterValues.RESOURCE]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val resourceIds = normalized[AuditEventFilterValues.RESOURCE_ID]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            val statuses = normalized[AuditEventFilterValues.STATUS]
                ?.mapNotNull { AuditStatus.fromValueOrNull(it) }
                ?: emptyList()

            val messages = normalized[AuditEventFilterValues.MESSAGE]
                ?.filter { it.isNotBlank() }
                ?: emptyList()

            return ManagementAuditEventListParams(
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
        }

        /**
         * Serializes [ManagementAuditEventListParams] into a map of raw URL query parameter strings.
         *
         * @param params Typed [ManagementAuditEventListParams] to serialize.
         * @return Map of query parameter names to string lists suitable for building URL search strings.
         */
        override fun serialize(params: ManagementAuditEventListParams): Map<String, List<String>> {
            val result = mutableMapOf<String, List<String>>()

            if (params.pageNumber != ListingDefaultValues.Pagination.PAGE_NUMBER) {
                result[ListingParamNames.Pagination.PAGE_NUMBER] = listOf(params.pageNumber.toString())
            }
            if (params.pageSize != ListingDefaultValues.Pagination.PAGE_SIZE) {
                result[ListingParamNames.Pagination.PAGE_SIZE] = listOf(params.pageSize.toString())
            }
            if (params.sortBy != AuditEventSortBy.DEFAULT) {
                result[ListingParamNames.Sort.SORT_BY] = listOf(params.sortBy.value)
            }
            if (params.sortOrder != SortOrder.DEFAULT) {
                result[ListingParamNames.Sort.SORT_ORDER] = listOf(params.sortOrder.value)
            }
            if (params.actorIds.isNotEmpty()) {
                result[AuditEventFilterValues.ACTOR_ID] = params.actorIds
            }
            if (params.actorTypes.isNotEmpty()) {
                result[AuditEventFilterValues.ACTOR_TYPE] = params.actorTypes.map { it.value }
            }
            if (params.actorUserRoles.isNotEmpty()) {
                result[AuditEventFilterValues.ACTOR_USER_ROLE] = params.actorUserRoles
            }
            if (params.actions.isNotEmpty()) {
                result[AuditEventFilterValues.ACTION] = params.actions
            }
            if (params.resources.isNotEmpty()) {
                result[AuditEventFilterValues.RESOURCE] = params.resources
            }
            if (params.resourceIds.isNotEmpty()) {
                result[AuditEventFilterValues.RESOURCE_ID] = params.resourceIds
            }
            if (params.statuses.isNotEmpty()) {
                result[AuditEventFilterValues.STATUS] = params.statuses.map { it.value }
            }
            if (params.messages.isNotEmpty()) {
                result[AuditEventFilterValues.MESSAGE] = params.messages
            }

            return result
        }
    }
}