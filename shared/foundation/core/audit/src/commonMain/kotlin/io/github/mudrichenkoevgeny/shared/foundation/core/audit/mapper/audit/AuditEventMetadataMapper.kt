package io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventMetadataValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload

/**
 * Maps a network metadata entry to the domain [AuditEventMetadata].
 */
fun AuditEventMetadataPayload.toAuditEventMetadata(): AuditEventMetadata =
    AuditEventMetadata(
        key = key,
        value = value,
        valueSensitivity = AuditEventMetadataValueSensitivity.fromValueOrThrow(valueSensitivity),
    )

/**
 * Maps a domain metadata entry to the wire [AuditEventMetadataPayload].
 */
fun AuditEventMetadata.toAuditEventMetadataPayload(): AuditEventMetadataPayload =
    AuditEventMetadataPayload(
        key = key,
        value = value,
        valueSensitivity = valueSensitivity.serialName,
    )