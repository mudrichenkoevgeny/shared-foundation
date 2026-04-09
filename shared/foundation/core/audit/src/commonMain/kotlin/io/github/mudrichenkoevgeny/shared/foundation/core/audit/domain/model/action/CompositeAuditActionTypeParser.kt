package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action

/**
 * Resolves a wire `action` string by calling [AuditActionType.parseOrNull] on each configured delegate until one returns
 * non-null.
 *
 * Pass **one** [AuditActionType] per enum module you support (any enum entry works: they share the same `parseOrNull` for
 * that enum). Order matters only if the same wire value could match more than one delegate.
 *
 * **Initialization** — build a [Set] of representatives, typically `entries.first()` from each enum that implements
 * [AuditActionType] (for example `UserAuditActionType` in `feature/user`, `SecurityAuditActionType` in `feature/security/api`):
 *
 * ```
 * CompositeAuditActionTypeParser(
 *     setOf(
 *         UserAuditActionType.entries.first(),
 *         SecurityAuditActionType.entries.first(),
 *         ...
 *     )
 * )
 * ```
 *
 * Provide a single instance from DI / your app module and inject it where you map audit payloads to domain.
 *
 * @param auditActionTypes Delegates tried in iteration order (typically distinct enum modules).
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
