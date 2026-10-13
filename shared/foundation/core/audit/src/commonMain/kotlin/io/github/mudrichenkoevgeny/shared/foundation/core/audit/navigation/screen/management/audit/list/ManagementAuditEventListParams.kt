package io.github.mudrichenkoevgeny.shared.foundation.core.audit.navigation.screen.management.audit.list

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing.AuditSortValues.AuditEventSortBy
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingDefaultValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.SortOrder
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.params.NavigationScreenParams
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
) : NavigationScreenParams {

    override fun toQueryMap(): Map<String, List<String>> =
        ManagementAuditEventListParamsParser.serialize(this)

    companion object {
        fun parse(params: Map<String, Any?>): ManagementAuditEventListParams =
            ManagementAuditEventListParamsParser.parse(params)
    }
}