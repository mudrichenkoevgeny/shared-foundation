package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val RESOURCE_AUDIT_EVENT = "audit_event"
private const val RESOURCE_AUDIT_EVENTS = "audit_events"

/**
 * Cross-cutting resource kinds for events recorded by the audit module.
 *
 * @property value String representation of the [CommonAuditResourceType].
 */
@Serializable
enum class CommonAuditResourceType(override val value: String) : AuditResourceType {
    /** Single audit event log record ([ManagementAuditRoutes.GET_AUDIT_EVENT]). */
    @SerialName(RESOURCE_AUDIT_EVENT)
    AUDIT_EVENT(RESOURCE_AUDIT_EVENT),

    /** Collection or list of audit event log records ([ManagementAuditRoutes.GET_AUDIT_EVENTS]). */
    @SerialName(RESOURCE_AUDIT_EVENTS)
    AUDIT_EVENTS(RESOURCE_AUDIT_EVENTS);

    companion object {
        /**
         * Returns [CommonAuditResourceType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): CommonAuditResourceType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [CommonAuditResourceType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [CommonAuditResourceType].
         */
        fun fromValueOrThrow(value: String): CommonAuditResourceType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for CommonAuditResourceType: '$value'")
    }
}
