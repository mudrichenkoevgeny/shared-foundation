package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource

/**
 * Resolves a wire `resource` string by calling configured parser functions until one returns non-null.
 *
 * Pass function references `fromValueOrNull` for each enum module you support:
 *
 * ```
 * CompositeAuditResourceTypeParser(
 *     listOf(
 *         CommonAuditResourceType::fromValueOrNull,
 *         SecurityAuditResourceType::fromValueOrNull,
 *         StorageAuditResourceType::fromValueOrNull,
 *         UserAuditResourceType::fromValueOrNull
 *     )
 * )
 * ```
 *
 * @param parsers Delegates tried in iteration order.
 */
class CompositeAuditResourceTypeParser(
    private val parsers: List<(String) -> AuditResourceType?>
) {

    /**
     * @param value Raw `resource` wire string (for example from an audit event payload).
     * @return The first non-null [AuditResourceType] returned by a delegate.
     * @throws IllegalArgumentException if no delegate recognizes [value].
     */
    fun fromValueOrThrow(value: String): AuditResourceType {
        for (parser in parsers) {
            val parsed = parser(value)
            if (parsed != null) {
                return parsed
            }
        }

        throw IllegalArgumentException("Unknown value of AuditResourceType: '$value'")
    }

    /**
     * @param value Raw `resource` wire string.
     * @return The first non-null [AuditResourceType] returned by a delegate, or `null`.
     */
    fun fromValueOrNull(value: String): AuditResourceType? {
        for (parser in parsers) {
            val parsed = parser(value)
            if (parsed != null) {
                return parsed
            }
        }
        return null
    }
}
