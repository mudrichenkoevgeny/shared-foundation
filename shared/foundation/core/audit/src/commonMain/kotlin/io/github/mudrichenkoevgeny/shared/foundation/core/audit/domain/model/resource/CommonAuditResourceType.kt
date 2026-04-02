package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes

/**
 * Cross-cutting resource kinds for events recorded by the audit module.
 */
enum class CommonAuditResourceType : AuditResourceType {
    /** [ManagementAuditRoutes.GET_AUDIT_EVENT_MASKED]. */
    AUDIT_EVENT_MASKED,

    /** [ManagementAuditRoutes.GET_AUDIT_EVENT_UNMASKED]. */
    AUDIT_EVENT_UNMASKED,

    /** [ManagementAuditRoutes.GET_AUDIT_EVENTS_MASKED]. */
    AUDIT_EVENTS_MASKED,

    /** [ManagementAuditRoutes.GET_AUDIT_EVENTS_UNMASKED]. */
    AUDIT_EVENTS_UNMASKED;

    /**
     * String representation of the [CommonAuditResourceType].
     */
    override val serialName: String
        get() = when (this) {
            AUDIT_EVENT_MASKED -> RESOURCE_AUDIT_EVENT_MASKED
            AUDIT_EVENT_UNMASKED -> RESOURCE_AUDIT_EVENT_UNMASKED
            AUDIT_EVENTS_MASKED -> RESOURCE_AUDIT_EVENTS_MASKED
            AUDIT_EVENTS_UNMASKED -> RESOURCE_AUDIT_EVENTS_UNMASKED
        }

    override fun parseOrNull(value: String): AuditResourceType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditResourceType = fromValueOrThrow(value)

    companion object {
        const val RESOURCE_AUDIT_EVENT_MASKED = "audit_event_masked"
        const val RESOURCE_AUDIT_EVENT_UNMASKED = "audit_event_unmasked"
        const val RESOURCE_AUDIT_EVENTS_MASKED = "audit_events_masked"
        const val RESOURCE_AUDIT_EVENTS_UNMASKED = "audit_events_unmasked"

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
