package io.github.mudrichenkoevgeny.shared.foundation.feature.audit.api.domain.permissions

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode

/**
 * Permission codes for reading audit log entries in the management API.
 *
 * Values are the raw strings stored in [PermissionCode]; servers enforce them when serving audit list/detail data.
 * Masked grants limit sensitive fields in responses; unmasked grants expose full payloads where policy allows.
 * The actor segment in each code (`user`, `staff`, `admin`, `system`, `service`) selects which actor scope the caller may inspect.
 */

object AuditPermissionCode {
    /** Read audit events in the user actor scope ([AuditActorType.USER]); masked sensitive fields. */
    val AUDIT_GET_FOR_USER_ACTOR_MASKED = PermissionCode("audit.get.for.user.actor.masked")

    /** Read audit events in the user actor scope ([AuditActorType.USER]); unmasked sensitive fields. */
    val AUDIT_GET_FOR_USER_ACTOR_UNMASKED = PermissionCode("audit.get.for.user.actor.unmasked")

    /** Read audit events in the staff actor scope; masked sensitive fields. */
    val AUDIT_GET_FOR_STAFF_ACTOR_MASKED = PermissionCode("audit.get.for.staff.actor.masked")

    /** Read audit events in the staff actor scope; unmasked sensitive fields. */
    val AUDIT_GET_FOR_STAFF_ACTOR_UNMASKED = PermissionCode("audit.get.for.staff.actor.unmasked")

    /** Read audit events in the admin actor scope; masked sensitive fields. */
    val AUDIT_GET_FOR_ADMIN_ACTOR_MASKED = PermissionCode("audit.get.for.admin.actor.masked")

    /** Read audit events in the admin actor scope; unmasked sensitive fields. */
    val AUDIT_GET_FOR_ADMIN_ACTOR_UNMASKED = PermissionCode("audit.get.for.admin.actor.unmasked")

    /** Read audit events in the system actor scope ([AuditActorType.SYSTEM]); masked sensitive fields. */
    val AUDIT_GET_FOR_SYSTEM_ACTOR_MASKED = PermissionCode("audit.get.for.system.actor.masked")

    /** Read audit events in the system actor scope ([AuditActorType.SYSTEM]); unmasked sensitive fields. */
    val AUDIT_GET_FOR_SYSTEM_ACTOR_UNMASKED = PermissionCode("audit.get.for.system.actor.unmasked")

    /** Read audit events in the service actor scope ([AuditActorType.SERVICE]); masked sensitive fields. */
    val AUDIT_GET_FOR_SERVICE_ACTOR_MASKED = PermissionCode("audit.get.for.service.actor.masked")

    /** Read audit events in the service actor scope ([AuditActorType.SERVICE]); unmasked sensitive fields. */
    val AUDIT_GET_FOR_SERVICE_ACTOR_UNMASKED = PermissionCode("audit.get.for.service.actor.unmasked")
}
