package io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus

/**
 * Key strings for audit **event** payloads and any matching contract surface.
 */
object AuditEventFields {
    /** Actor who performed the action: user or system id (UUID string, hex with dashes). */
    const val ACTOR_ID = "actor_id"

    /** Action discriminator (e.g. `login`, `order.create`). */
    const val ACTION = "action"

    /** Resource kind affected (e.g. `user`, `order`). */
    const val RESOURCE = "resource"

    /** Target instance id for that resource type, when applicable (opaque string per resource semantics). */
    const val RESOURCE_ID = "resource_id"

    /** Outcome of the attempt; wire values are [AuditStatus] serial names. */
    const val STATUS = "status"
}
