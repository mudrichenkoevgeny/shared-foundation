package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.listing

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields

/**
 * Stable **keys** for filtering audit event listings.
 *
 * Use these constants when building HTTP query strings, parsing list requests, or mapping filters in services and
 * persistence so clients, servers, and documentation stay aligned.
 */
object AuditFilterValues {

    /**
     * Optional filters when listing audit events.
     *
     * If a parameter is **absent**, there is **no** filtering on that axis (within the caller’s access scope), before
     * pagination and sort.
     *
     * **Multiple values** for the same filter: repeat the same query key (standard query string, e.g. `k=a&k=b`),
     * joined with `&` alongside other parameters; repeats of the same key mean **OR**. **Different** keys combine as **AND**.
     *
     * Allowed **values** for filters are resource-specific (for example [AuditActorType] serial names for [ACTOR_TYPE],
     * [AuditStatus] serial names for [STATUS]).
     */
    object AuditEventFilterValues {
        /** Filter by actor id ([AuditEventPrivatePayload.actorId]); UUID string, hex with dashes. */
        const val ACTOR_ID = AuditEventFields.ACTOR_ID

        /** Filter by actor type ([AuditEventPrivatePayload.actorType]); wire values are [AuditActorType] serial names. */
        const val ACTOR_TYPE = AuditEventFields.ACTOR_TYPE

        /** Filter by user actor role snapshot ([AuditEventPrivatePayload.actorUserRole]). */
        const val ACTOR_USER_ROLE = AuditEventFields.ACTOR_USER_ROLE

        /** Filter by action name ([AuditEventPrivatePayload.action]). */
        const val ACTION = AuditEventFields.ACTION

        /** Filter by resource type ([AuditEventPrivatePayload.resource]). */
        const val RESOURCE = AuditEventFields.RESOURCE

        /** Filter by resource instance id ([AuditEventPrivatePayload.resourceId]). */
        const val RESOURCE_ID = AuditEventFields.RESOURCE_ID

        /** Filter by outcome; wire values are [AuditStatus] JSON names (`SUCCESS`, `FAILED`, `DENIED`). */
        const val STATUS = AuditEventFields.STATUS

        /** Filter by [AuditEventPrivatePayload.message] (typically case-insensitive substring). */
        const val MESSAGE = CommonApiFields.MESSAGE
    }
}
