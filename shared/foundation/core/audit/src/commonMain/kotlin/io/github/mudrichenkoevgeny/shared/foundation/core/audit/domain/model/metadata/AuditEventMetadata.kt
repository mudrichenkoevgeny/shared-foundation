package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent

/**
 * A single key-value pair attached to an [AuditEvent]. Redaction policy for [value] is defined by [AuditMetadataKey.valueSensitivity] on [key].
 *
 * @param key Metadata key (e.g. for filtering or analytics).
 * @param value Raw value as stored for the audit record.
 */
data class AuditEventMetadata(
    val key: AuditMetadataKey,
    val value: String
)