package io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields

/**
 * Key strings for audit **event** payloads and any matching contract surface.
 */
public object AuditEventFields {
    /** Actor who performed the action: user or system id (UUID string, hex with dashes). */
    public const val ACTOR_ID: String = "actor_id"

    /** Actor type; values follow [AuditActorType]. */
    public const val ACTOR_TYPE: String = "actor_type"

    /** Role snapshot for user actor when [ACTOR_TYPE] is `user` (server-defined role key). */
    public const val ACTOR_USER_ROLE: String = "actor_user_role"

    /** Action discriminator (e.g. `login`, `order.create`). */
    public const val ACTION: String = "action"

    /** Resource kind affected (e.g. `user`, `order`). */
    public const val RESOURCE: String = "resource"

    /** Target instance id for that resource type, when applicable (opaque string per resource semantics). */
    public const val RESOURCE_ID: String = "resource_id"

    /** Outcome of the attempt; wire values are [AuditStatus] serial names. */
    public const val STATUS: String = "status"

    /** Key for one entry in the audit event metadata array ([CommonApiFields.METADATA]). */
    public const val METADATA_KEY: String = "key"

    /** Value inside each metadata array object. */
    public const val METADATA_VALUE: String = "value"

    /** Sensitivity hint for [METADATA_VALUE]; wire values are [AuditValueSensitivity] serial names. */
    public const val METADATA_VALUE_SENSITIVITY: String = "value_sensitivity"
}
