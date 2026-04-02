package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action

/**
 * Base audit action type contract.
 *
 * Other modules can provide their own action type implementations.
 */
interface AuditActionType {
    val serialName: String

    fun parseOrNull(value: String): AuditActionType?
    fun parseOrThrow(value: String): AuditActionType
}
