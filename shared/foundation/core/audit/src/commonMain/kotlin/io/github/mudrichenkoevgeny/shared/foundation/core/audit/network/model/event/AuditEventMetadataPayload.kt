package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventMetadataValueSensitivity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * One metadata entry on the audit event wire model ([AuditEventPayload.metadata]), aligned with domain [AuditEventMetadata].
 *
 * @property key Logical key for filtering or analytics.
 * @property value Raw value as stored for the audit record.
 * @property valueSensitivity Wire string matching [AuditEventMetadataValueSensitivity.serialName]; defaults to non-sensitive.
 */
@Serializable
data class AuditEventMetadataPayload(
    @SerialName(AuditEventFields.METADATA_KEY)
    val key: String,

    @SerialName(AuditEventFields.METADATA_VALUE)
    val value: String,

    @SerialName(AuditEventFields.METADATA_VALUE_SENSITIVITY)
    val valueSensitivity: String = AuditEventMetadataValueSensitivity.SENSITIVITY_NON_SENSITIVE,
)
