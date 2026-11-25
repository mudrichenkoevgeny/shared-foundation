package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity

/**
 * Base audit metadata key contract.
 */
public interface AuditMetadataKey {
    public val value: String
    public val valueSensitivity: AuditValueSensitivity
}
