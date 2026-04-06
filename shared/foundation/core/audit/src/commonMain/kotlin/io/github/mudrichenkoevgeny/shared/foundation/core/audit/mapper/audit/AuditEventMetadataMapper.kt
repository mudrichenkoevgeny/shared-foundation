package io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CompositeAuditMetadataKeyParser
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload

/**
 * Maps a network metadata entry to the domain [AuditEventMetadata].
 *
 * @param compositeMetadataKeyParser Resolves payload `key` strings to [AuditMetadataKey].
 */
fun AuditEventMetadataPayload.toAuditEventMetadata(
    compositeMetadataKeyParser: CompositeAuditMetadataKeyParser
): AuditEventMetadata =
    AuditEventMetadata(
        key = compositeMetadataKeyParser.fromValueOrThrow(key),
        value = value,
    )

/**
 * Maps a domain metadata entry to the wire [AuditEventMetadataPayload].
 *
 * [AuditEventMetadataPayload.valueSensitivity] is taken from [AuditEventMetadata.key] so it stays aligned with the key contract.
 */
fun AuditEventMetadata.toAuditEventMetadataPayload(): AuditEventMetadataPayload =
    AuditEventMetadataPayload(
        key = key.serialName,
        value = value,
        valueSensitivity = key.valueSensitivity.serialName,
    )