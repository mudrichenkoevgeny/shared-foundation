package io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.toAuditEventIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPayload
import kotlin.time.Instant
import kotlin.uuid.Uuid

/**
 * Extension functions mapping audit event models between domain and network representations.
 */

/**
 * Builds a domain [AuditEvent] from [AuditEventPayload].
 *
 * @return domain model with strongly typed [AuditEvent.id] and parsed [Uuid] for [AuditEvent.actorId].
 *
 * Parses [AuditEventPayload.status] wire value into [AuditStatus]. Throws if the wire value is invalid.
 * Converts [AuditEventPayload.createdAt] epoch millis into [AuditEvent.createdAt] ([Instant]).
 */
fun AuditEventPayload.toAuditEvent(): AuditEvent = AuditEvent(
    id = id.toAuditEventIdOrThrow(),
    actorId = actorId?.let { Uuid.parseOrNull(it) },
    action = action,
    resource = resource,
    resourceId = resourceId,
    status = run {
        val wireStatus = status
        AuditStatus.fromValueOrNull(wireStatus)
            ?: error("Unknown audit status wire value: '$wireStatus'")
    },
    metadata = metadata,
    message = message,
    createdAt = Instant.fromEpochMilliseconds(createdAt)
)

/**
 * Builds a network [AuditEventPayload] from a domain [AuditEvent].
 *
 * @return payload DTO aligned with the shared [AuditEventPayload] contract (`snake_case` keys, epoch millis timestamps).
 *
 * Writes [AuditEvent.status] as a wire string matching [AuditStatus] serial names.
 * Converts [AuditEvent.createdAt] ([Instant]) into epoch millis for [AuditEventPayload.createdAt].
 */
fun AuditEvent.toPayload(): AuditEventPayload = AuditEventPayload(
    id = id.asHexDashString(),
    actorId = actorId?.toHexDashString(),
    action = action,
    resource = resource,
    resourceId = resourceId,
    status = status.serialName,
    metadata = metadata,
    message = message,
    createdAt = createdAt.toEpochMilliseconds()
)