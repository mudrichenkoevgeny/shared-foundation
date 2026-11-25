package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action

/**
 * Base audit action type contract.
 *
 * Other modules can provide their own action type implementations.
 */
public interface AuditActionType {
    public val value: String
}
