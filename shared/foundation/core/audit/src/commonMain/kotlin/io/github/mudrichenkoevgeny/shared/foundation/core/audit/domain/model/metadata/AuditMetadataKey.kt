package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity

/**
 * Base audit metadata key contract.
 */
interface AuditMetadataKey {
    val value: String
    val valueSensitivity: AuditValueSensitivity
}
