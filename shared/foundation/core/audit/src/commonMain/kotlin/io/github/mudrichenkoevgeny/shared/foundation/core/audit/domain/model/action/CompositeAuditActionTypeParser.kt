package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action

/**
 * Resolves a wire action string to [AuditActionType] by calling [AuditActionType.parseOrNull] on each
 * configured representative until one returns non-null.
 *
 * The configured set is typically **one** constant per audit-action enum whose `parseOrNull` parses that
 * whole enum (any constant of that enum is sufficient). Order matters only if a value could be accepted
 * by more than one delegate (unusual).
 */
class CompositeAuditActionTypeParser(
    private val auditActionTypes: Set<AuditActionType>
) {
    /**
     * @param value Raw `action` wire string (for example from an audit event payload).
     * @return The first non-null [AuditActionType] returned by a delegate.
     * @throws IllegalStateException if no delegate recognizes [value].
     */
    fun fromValueOrThrow(value: String): AuditActionType {
        for (auditActionType in auditActionTypes) {
            val parsedActionType = auditActionType.parseOrNull(value)
            if (parsedActionType != null) {
                return parsedActionType
            }
        }

        error("Unknown value of AuditActionType: '$value'")
    }
}
