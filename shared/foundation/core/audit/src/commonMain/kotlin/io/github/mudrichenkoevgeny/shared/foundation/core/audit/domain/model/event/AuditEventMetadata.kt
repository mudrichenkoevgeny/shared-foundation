package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event

/**
 * A single key-value pair attached to an [AuditEvent], with an explicit sensitivity hint for the [value].
 *
 * @param key Metadata key (e.g. for filtering or analytics).
 * @param value Raw value as stored for the audit record.
 * @param valueSensitivity Whether and how the value should be redacted when displayed or exported;
 * [AuditEventMetadataValueSensitivity.NON_SENSITIVE] when the value is safe to show as-is.
 */
data class AuditEventMetadata(
    val key: String,
    val value: String,
    val valueSensitivity: AuditEventMetadataValueSensitivity =
        AuditEventMetadataValueSensitivity.NON_SENSITIVE
)
