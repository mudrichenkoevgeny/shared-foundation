package io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields

/**
 * Key strings for audit **event** payloads and any matching contract surface.
 */
object AuditEventFields {
    /** Actor who performed the action: user or system id (UUID string, hex with dashes). */
    const val ACTOR_ID = "actor_id"

    /** Actor type; values follow [AuditActorType]. */
    const val ACTOR_TYPE = "actor_type"

    /** Role snapshot for user actor when [ACTOR_TYPE] is `user` (server-defined role key). */
    const val ACTOR_USER_ROLE = "actor_user_role"

    /** Action discriminator (e.g. `login`, `order.create`). */
    const val ACTION = "action"

    /** Resource kind affected (e.g. `user`, `order`). */
    const val RESOURCE = "resource"

    /** Target instance id for that resource type, when applicable (opaque string per resource semantics). */
    const val RESOURCE_ID = "resource_id"

    /** Outcome of the attempt; wire values are [AuditStatus] serial names. */
    const val STATUS = "status"

    /** Key for one entry in the audit event metadata array ([CommonApiFields.METADATA]). */
    const val METADATA_KEY = "key"

    /** Value inside each metadata array object. */
    const val METADATA_VALUE = "value"

    /** Sensitivity hint for [METADATA_VALUE]; wire values are [AuditValueSensitivity] serial names. */
    const val METADATA_VALUE_SENSITIVITY = "value_sensitivity"
}
