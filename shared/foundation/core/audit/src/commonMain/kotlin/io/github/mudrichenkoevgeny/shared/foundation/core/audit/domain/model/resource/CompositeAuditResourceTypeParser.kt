package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource

/**
 * Resolves a wire resource string to [AuditResourceType] by calling [AuditResourceType.parseOrNull] on each
 * configured representative until one returns non-null.
 *
 * The configured set is typically **one** constant per audit-resource enum whose `parseOrNull` parses that
 * whole enum (any constant of that enum is sufficient). Order matters only if a value could be accepted
 * by more than one delegate (unusual).
 */
class CompositeAuditResourceTypeParser(
    private val auditResourceTypes: Set<AuditResourceType>
) {
    /**
     * @param value Raw `resource` wire string (for example from an audit event payload).
     * @return The first non-null [AuditResourceType] returned by a delegate.
     * @throws IllegalStateException if no delegate recognizes [value].
     */
    fun fromValueOrThrow(value: String): AuditResourceType {
        for (auditResourceType in auditResourceTypes) {
            val parsedResourceType = auditResourceType.parseOrNull(value)
            if (parsedResourceType != null) {
                return parsedResourceType
            }
        }

        error("Unknown value of AuditResourceType: '$value'")
    }
}
