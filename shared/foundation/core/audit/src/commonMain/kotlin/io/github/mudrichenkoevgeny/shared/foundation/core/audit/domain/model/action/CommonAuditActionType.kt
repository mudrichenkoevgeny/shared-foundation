package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes

/**
 * Cross-cutting audit actions for the audit module.
 */
enum class CommonAuditActionType : AuditActionType {
    /** [ManagementAuditRoutes.GET_AUDIT_EVENTS_MASKED]. */
    MANAGEMENT_GET_AUDIT_EVENTS_MASKED,

    /** [ManagementAuditRoutes.GET_AUDIT_EVENTS_UNMASKED]. */
    MANAGEMENT_GET_AUDIT_EVENTS_UNMASKED,

    /** [ManagementAuditRoutes.GET_AUDIT_EVENT_MASKED]. */
    MANAGEMENT_GET_AUDIT_EVENT_MASKED,

    /** [ManagementAuditRoutes.GET_AUDIT_EVENT_UNMASKED]. */
    MANAGEMENT_GET_AUDIT_EVENT_UNMASKED;

    /**
     * String representation of the [CommonAuditActionType].
     */
    override val serialName: String
        get() = when (this) {
            MANAGEMENT_GET_AUDIT_EVENTS_MASKED -> ACTION_MANAGEMENT_GET_AUDIT_EVENTS_MASKED
            MANAGEMENT_GET_AUDIT_EVENTS_UNMASKED -> ACTION_MANAGEMENT_GET_AUDIT_EVENTS_UNMASKED
            MANAGEMENT_GET_AUDIT_EVENT_MASKED -> ACTION_MANAGEMENT_GET_AUDIT_EVENT_MASKED
            MANAGEMENT_GET_AUDIT_EVENT_UNMASKED -> ACTION_MANAGEMENT_GET_AUDIT_EVENT_UNMASKED
        }

    override fun parseOrNull(value: String): AuditActionType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditActionType = fromValueOrThrow(value)

    companion object {
        const val ACTION_MANAGEMENT_GET_AUDIT_EVENTS_MASKED = "management_get_audit_events_masked"
        const val ACTION_MANAGEMENT_GET_AUDIT_EVENTS_UNMASKED = "management_get_audit_events_unmasked"
        const val ACTION_MANAGEMENT_GET_AUDIT_EVENT_MASKED = "management_get_audit_event_masked"
        const val ACTION_MANAGEMENT_GET_AUDIT_EVENT_UNMASKED = "management_get_audit_event_unmasked"

        /**
         * Returns [CommonAuditActionType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): CommonAuditActionType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [CommonAuditActionType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [CommonAuditActionType].
         */
        fun fromValueOrThrow(value: String): CommonAuditActionType = valueOf(value.uppercase())
    }
}
