package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditValueSensitivity

interface AuditMetadataKey {
    val serialName: String
    val valueSensitivity: AuditValueSensitivity

    fun parseOrNull(value: String): AuditMetadataKey?
    fun parseOrThrow(value: String): AuditMetadataKey
}
