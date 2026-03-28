package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields

/**
 * JSON field names for audit API payloads (snake_case on the wire).
 *
 * Shared keys (e.g. [CommonApiFields.ID], [CommonApiFields.CREATED_AT], [CommonApiFields.METADATA], [CommonApiFields.MESSAGE])
 * are defined on [CommonApiFields].
 */
object AuditApiFields {
    /** JSON field name for the actor user/system identifier (UUID string). */
    const val ACTOR_ID = "actor_id"

    /** JSON field name for the action name (e.g. login, create_order). */
    const val ACTION = "action"

    /** JSON field name for the resource type (e.g. order, user). */
    const val RESOURCE = "resource"

    /** JSON field name for the specific resource instance identifier, when applicable. */
    const val RESOURCE_ID = "resource_id"

    /** JSON field name for the outcome ([AuditStatus]). */
    const val STATUS = "status"
}
