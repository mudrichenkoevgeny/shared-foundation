package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource

/**
 * Base audit resource type contract.
 *
 * Other modules can provide their own resource type implementations.
 */
interface AuditResourceType {
    val serialName: String

    fun parseOrNull(value: String): AuditResourceType?
    fun parseOrThrow(value: String): AuditResourceType
}
