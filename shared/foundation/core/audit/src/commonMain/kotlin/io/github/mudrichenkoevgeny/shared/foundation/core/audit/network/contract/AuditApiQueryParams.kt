package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.response.AuditEventResponse
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields

/**
 * Query parameter **names** for audit list-style HTTP endpoints.
 *
 * Allowed **values** for filters are resource-specific (for example [AuditStatus] serial names for [AuditEventQueryParams.STATUS]).
 */
object AuditApiQueryParams {
    /**
     * Optional filters when listing audit events.
     *
     * If a parameter is **absent**, there is **no** filtering on that axis (within the caller’s access scope), before
     * pagination and sort.
     *
     * **Multiple values** for the same filter: repeat the same query key (standard query string, e.g. `k=a&k=b`),
     * joined with `&` alongside other parameters; repeats of the same key mean **OR**. **Different** keys combine as **AND**.
     */
    object AuditEventQueryParams {
        /** Filter by actor id ([AuditEventResponse.actorId]); UUID string, hex with dashes. */
        const val ACTOR_ID = AuditApiFields.ACTOR_ID

        /** Filter by action name ([AuditEventResponse.action]); exact match per repeated value unless the server documents otherwise. */
        const val ACTION = AuditApiFields.ACTION

        /** Filter by resource type ([AuditEventResponse.resource]); exact match per repeated value unless the server documents otherwise. */
        const val RESOURCE = AuditApiFields.RESOURCE

        /** Filter by resource instance id ([AuditEventResponse.resourceId]); exact match per repeated value unless the server documents otherwise. */
        const val RESOURCE_ID = AuditApiFields.RESOURCE_ID

        /** Filter by outcome; wire values are [AuditStatus] JSON names (`SUCCESS`, `FAILED`, `DENIED`). */
        const val STATUS = AuditApiFields.STATUS

        /**
         * Optional text match against [AuditEventResponse.message] (e.g. error or diagnostic text).
         *
         * Typical server semantics: **case-insensitive substring** match; events with a `null` or blank message do not match.
         * Exact-match or other behavior must be documented by the server if it differs.
         */
        const val MESSAGE = CommonApiFields.MESSAGE
    }
}
