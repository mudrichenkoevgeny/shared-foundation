package io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventMetadataValueSensitivity
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.CompositeAuditActionTypeParser
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.CompositeAuditResourceTypeParser
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.toAuditEventIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPayload
import kotlin.time.Instant

/**
 * Extension functions mapping audit event models between domain and network representations.
 */

/**
 * Builds a domain [AuditEvent] from [AuditEventPayload].
 *
 * @param compositeActionTypeParser Resolves payload `action` strings to [AuditActionType].
 * @param compositeResourceTypeParser Resolves payload `resource` strings to [AuditResourceType].
 * @return domain model with strongly typed [AuditEvent.id], [AuditEvent.action], and [AuditEvent.resource].
 *
 * Parses [AuditEventPayload.status] wire value into [AuditStatus]. Throws if the wire value is invalid.
 * Parses each [AuditEventMetadataPayload.valueSensitivity] into [AuditEventMetadataValueSensitivity]; throws if invalid.
 * Converts [AuditEventPayload.createdAt] epoch millis into [AuditEvent.createdAt] ([Instant]).
 */
fun AuditEventPayload.toAuditEvent(
    compositeActionTypeParser: CompositeAuditActionTypeParser,
    compositeResourceTypeParser: CompositeAuditResourceTypeParser
): AuditEvent = AuditEvent(
    id = id.toAuditEventIdOrThrow(),
    actorId = actorId,
    actorType = AuditActorType.fromValueOrThrow(actorType),
    actorUserRole = actorUserRole,
    action = compositeActionTypeParser.fromValueOrThrow(action),
    resource = compositeResourceTypeParser.fromValueOrThrow(resource),
    resourceId = resourceId,
    status = AuditStatus.fromValueOrThrow(status),
    metadata = metadata.map { it.toAuditEventMetadata() }.toSet(),
    message = message,
    createdAt = Instant.fromEpochMilliseconds(createdAt)
)

/**
 * Builds a network [AuditEventPayload] from a domain [AuditEvent].
 *
 * @return payload DTO aligned with the shared [AuditEventPayload] contract (`snake_case` keys, epoch millis timestamps).
 *
 * Writes [AuditEvent.status] as a wire string matching [AuditStatus] serial names.
 * Maps [AuditEvent.metadata] to [AuditEventMetadataPayload] entries (stable order by key, then value, then sensitivity).
 * Converts [AuditEvent.createdAt] ([Instant]) into epoch millis for [AuditEventPayload.createdAt].
 */
fun AuditEvent.toAuditEventPayload(): AuditEventPayload = AuditEventPayload(
    id = id.asHexDashString(),
    actorId = actorId,
    actorType = actorType.serialName,
    actorUserRole = actorUserRole,
    action = action.serialName,
    resource = resource.serialName,
    resourceId = resourceId,
    status = status.serialName,
    metadata = metadata.map { it.toAuditEventMetadataPayload() },
    message = message,
    createdAt = createdAt.toEpochMilliseconds()
)