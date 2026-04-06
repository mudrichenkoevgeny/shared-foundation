package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata

/**
 * Resolves a wire metadata `key` string by calling [AuditMetadataKey.parseOrNull] on each configured delegate until one returns
 * non-null.
 *
 * Pass **one** [AuditMetadataKey] per enum module you support (any enum entry works: they share the same `parseOrNull` for
 * that enum). Order matters only if the same wire value could match more than one delegate.
 *
 * **Initialization** — build a [Set] of representatives, typically `entries.first()` from each enum that implements
 * [AuditMetadataKey] (for example [CommonAuditMetadataKey]):
 *
 * ```
 * CompositeAuditMetadataKeyParser(
 *     setOf(
 *         CommonAuditMetadataKey.entries.first(),
 *     )
 * )
 * ```
 *
 * Provide a single instance from DI / your app module and inject it where you map audit payloads to domain.
 *
 * @param metadataKeys Delegates tried in iteration order (typically distinct enum modules).
 */
class CompositeAuditMetadataKeyParser(
    private val metadataKeys: Set<AuditMetadataKey>
) {
    /**
     * @param value Raw metadata `key` wire string (for example from an audit event payload).
     * @return The first non-null [AuditMetadataKey] returned by a delegate.
     * @throws IllegalStateException if no delegate recognizes [value].
     */
    fun fromValueOrThrow(value: String): AuditMetadataKey {
        for (metadataKey in metadataKeys) {
            val parsedMetadataKey = metadataKey.parseOrNull(value)
            if (parsedMetadataKey != null) {
                return parsedMetadataKey
            }
        }

        error("Unknown value of AuditMetadataKey: '$value'")
    }
}
