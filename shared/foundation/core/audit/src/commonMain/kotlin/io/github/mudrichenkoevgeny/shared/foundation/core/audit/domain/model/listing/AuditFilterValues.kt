package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.CommonFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.listing.ListingParamNames

/**
 * Stable **keys** for filtering audit event listings.
 *
 * Keys follow the declaration order of properties in [AuditEventPrivate].
 * Use these constants when building HTTP query strings, parsing list requests, or mapping filters in services and
 * persistence so clients, servers, and documentation stay aligned.
 */
object AuditEventFilterValues {
    /** Filter by one or more specific audit event record IDs. */
    const val EVENT_ID = AuditApiPaths.EVENT_ID

    /** Filter by one or more actor IDs. */
    const val ACTOR_ID = AuditEventFields.ACTOR_ID

    /** Filter by one or more actor types ([AuditActorType]) (OR semantics). */
    const val ACTOR_TYPE = AuditEventFields.ACTOR_TYPE

    /** Filter to exclude specific actor types ([AuditActorType]) (NOT IN semantics). */
    const val EXCLUDE_ACTOR_TYPE = "exclude_actor_type"

    /** Filter by one or more user actor role snapshots (OR semantics). */
    const val ACTOR_USER_ROLE = AuditEventFields.ACTOR_USER_ROLE

    /** Filter by one or more audit action names (OR semantics). */
    const val ACTION = AuditEventFields.ACTION

    /** Filter to exclude specific audit action names (NOT IN semantics). */
    const val EXCLUDE_ACTION = "exclude_action"

    /** Filter by one or more resource types ([AuditResourceType]) (OR semantics). */
    const val RESOURCE = AuditEventFields.RESOURCE

    /** Filter by one or more resource instance IDs (OR semantics). */
    const val RESOURCE_ID = AuditEventFields.RESOURCE_ID

    /** Filter by one or more outcome statuses ([AuditStatus]) (OR semantics). */
    const val STATUS = AuditEventFields.STATUS

    /** Filter to exclude specific outcome statuses ([AuditStatus]) (NOT IN semantics). */
    const val EXCLUDE_STATUS = "exclude_status"

    /** Inclusive lower bound filter key for creation timestamp. */
    const val CREATED_AT_FROM = CommonFilterValues.CREATED_AT_FROM

    /** Inclusive upper bound filter key for creation timestamp. */
    const val CREATED_AT_TO = CommonFilterValues.CREATED_AT_TO

    /**
     * Free-text search query string targeting:
     * - Event human-readable message ([AuditEventPrivate.message]).
     */
    const val SEARCH_QUERY = ListingParamNames.Search.SEARCH_QUERY
}
