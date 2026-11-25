package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate

/**
 * A single key-value pair attached to an [AuditEventPrivate].
 * Redaction policy for [value] is defined by [AuditMetadataKey.valueSensitivity] on [key].
 *
 * @property key Metadata key (e.g. for filtering or analytics).
 * @property value Raw value as stored for the audit record.
 */
public data class AuditEventMetadata(
    public val key: AuditMetadataKey,
    public val value: String
)
