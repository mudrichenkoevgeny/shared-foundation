package io.github.mudrichenkoevgeny.shared.foundation.feature.auditApi.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.auditApi.network.route.management.ManagementAuditRoutes

/**
 * Cross-cutting resource kinds for events recorded by the audit module.
 */
enum class CommonAuditResourceType : AuditResourceType {
    /** [ManagementAuditRoutes.GET_AUDIT_EVENT]. */
    AUDIT_EVENT,

    /** [ManagementAuditRoutes.GET_AUDIT_EVENTS]. */
    AUDIT_EVENTS;

    /**
     * String representation of the [CommonAuditResourceType].
     */
    override val serialName: String
        get() = when (this) {
            AUDIT_EVENT -> RESOURCE_AUDIT_EVENT
            AUDIT_EVENTS -> RESOURCE_AUDIT_EVENTS
        }

    override fun parseOrNull(value: String): AuditResourceType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditResourceType = fromValueOrThrow(value)

    companion object {
        const val RESOURCE_AUDIT_EVENT = "audit_event"
        const val RESOURCE_AUDIT_EVENTS = "audit_events"

        /**
         * Returns [CommonAuditResourceType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): CommonAuditResourceType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [CommonAuditResourceType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [CommonAuditResourceType].
         */
        fun fromValueOrThrow(value: String): CommonAuditResourceType = valueOf(value.uppercase())
    }
}
