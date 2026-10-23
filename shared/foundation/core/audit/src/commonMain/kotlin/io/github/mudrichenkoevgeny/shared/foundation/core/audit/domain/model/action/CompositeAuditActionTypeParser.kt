package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action

/**
 * Resolves a wire `action` string by calling configured parser functions until one returns non-null.
 *
 * Pass function references `fromValueOrNull` for each enum module you support:
 *
 * ```
 * CompositeAuditActionTypeParser(
 *     listOf(
 *         SecurityAuditActionType::fromValueOrNull,
 *         StorageAuditActionType::fromValueOrNull,
 *         UserAuditActionType::fromValueOrNull
 *     )
 * )
 * ```
 *
 * @param parsers Delegates tried in iteration order.
 */
class CompositeAuditActionTypeParser(
    private val parsers: List<(String) -> AuditActionType?>
) {

    /**
     * @param value Raw `action` wire string (for example from an audit event payload).
     * @return The first non-null [AuditActionType] returned by a delegate.
     * @throws IllegalArgumentException if no delegate recognizes [value].
     */
    fun fromValueOrThrow(value: String): AuditActionType {
        for (parser in parsers) {
            val parsed = parser(value)
            if (parsed != null) {
                return parsed
            }
        }

        throw IllegalArgumentException("Unknown value of AuditActionType: '$value'")
    }

    /**
     * @param value Raw `action` wire string.
     * @return The first non-null [AuditActionType] returned by a delegate, or `null`.
     */
    fun fromValueOrNull(value: String): AuditActionType? {
        for (parser in parsers) {
            val parsed = parser(value)
            if (parsed != null) {
                return parsed
            }
        }
        return null
    }
}
