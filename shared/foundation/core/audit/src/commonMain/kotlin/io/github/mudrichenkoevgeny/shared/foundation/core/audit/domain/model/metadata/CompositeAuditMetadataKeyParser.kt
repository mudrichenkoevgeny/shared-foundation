package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata

/**
 * Resolves a wire metadata `key` string by calling configured parser functions until one returns non-null.
 *
 * Pass function references `fromValueOrNull` for each enum module you support:
 *
 * ```
 * CompositeAuditMetadataKeyParser(
 *     listOf(
 *         CommonAuditMetadataKey::fromValueOrNull,
 *         UserAuditMetadataKey::fromValueOrNull
 *     )
 * )
 * ```
 *
 * @param parsers Delegates tried in iteration order.
 */
class CompositeAuditMetadataKeyParser(
    private val parsers: List<(String) -> AuditMetadataKey?>
) {

    /**
     * @param value Raw metadata `key` wire string (for example from an audit event payload).
     * @return The first non-null [AuditMetadataKey] returned by a delegate.
     * @throws IllegalArgumentException if no delegate recognizes [value].
     */
    fun fromValueOrThrow(value: String): AuditMetadataKey {
        for (parser in parsers) {
            val parsed = parser(value)
            if (parsed != null) {
                return parsed
            }
        }

        throw IllegalArgumentException("Unknown value of AuditMetadataKey: '$value'")
    }

    /**
     * @param value Raw metadata `key` wire string.
     * @return The first non-null [AuditMetadataKey] returned by a delegate, or `null`.
     */
    fun fromValueOrNull(value: String): AuditMetadataKey? {
        for (parser in parsers) {
            val parsed = parser(value)
            if (parsed != null) {
                return parsed
            }
        }
        return null
    }
}
