package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.route.management.ManagementAuditRoutes

/**
 * Cross-cutting audit actions for the audit module.
 */
enum class CommonAuditActionType : AuditActionType {
    /** [ManagementAuditRoutes.GET_AUDIT_EVENTS]. */
    MANAGEMENT_GET_AUDIT_EVENTS,

    /** [ManagementAuditRoutes.GET_AUDIT_EVENT]. */
    MANAGEMENT_GET_AUDIT_EVENT;

    /**
     * String representation of the [CommonAuditActionType].
     */
    override val serialName: String
        get() = when (this) {
            MANAGEMENT_GET_AUDIT_EVENTS -> ACTION_MANAGEMENT_GET_AUDIT_EVENTS
            MANAGEMENT_GET_AUDIT_EVENT -> ACTION_MANAGEMENT_GET_AUDIT_EVENT
        }

    override fun parseOrNull(value: String): AuditActionType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditActionType = fromValueOrThrow(value)

    companion object {
        const val ACTION_MANAGEMENT_GET_AUDIT_EVENTS = "management_get_audit_events"
        const val ACTION_MANAGEMENT_GET_AUDIT_EVENT = "management_get_audit_event"

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
