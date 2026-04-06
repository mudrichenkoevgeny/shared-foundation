package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource

/**
 * Resolves a wire `resource` string by calling [AuditResourceType.parseOrNull] on each configured delegate until one
 * returns non-null.
 *
 * Pass **one** [AuditResourceType] per enum module you support (any enum entry works: they share the same `parseOrNull`
 * for that enum). Order matters only if the same wire value could match more than one delegate.
 *
 * **Initialization** — build a [Set] of representatives, typically `entries.first()` from each enum that implements
 * [AuditResourceType] (for example `UserAuditResourceType` in `feature/user`, `CommonAuditResourceType` in
 * `feature/auditApi`):
 *
 * ```
 * CompositeAuditResourceTypeParser(
 *     setOf(
 *         UserAuditResourceType.entries.first(),
 *         CommonAuditResourceType.entries.first(),
 *         ...
 *     )
 * )
 * ```
 *
 * Provide a single instance from DI / your app module and inject it where you map audit payloads to domain.
 *
 * @param auditResourceTypes Delegates tried in iteration order (typically distinct enum modules).
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
