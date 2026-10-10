package io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.CompositeAuditActionTypeParser
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.toAuditEventIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CompositeAuditMetadataKeyParser
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.CompositeAuditResourceTypeParser
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventMetadataPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import kotlin.time.Instant

/**
 * Extension functions mapping private audit event models between domain and network representations.
 */

/**
 * Builds a domain [AuditEventPrivate] from [AuditEventPrivatePayload].
 *
 * Parses [AuditEventPrivatePayload.status] wire string value into [AuditStatus] (throws if invalid).
 * Maps [AuditEventPrivatePayload.metadata] entries using [compositeMetadataKeyParser].
 * Converts [AuditEventPrivatePayload.createdAt] epoch millis into [AuditEventPrivate.createdAt] ([Instant]).
 * Converts [AuditEventPrivatePayload.updatedAt] epoch millis into [AuditEventPrivate.updatedAt] ([Instant]), if present.
 *
 * @param compositeActionTypeParser Resolves payload `action` strings to [AuditActionType].
 * @param compositeResourceTypeParser Resolves payload `resource` strings to [AuditResourceType].
 * @param compositeMetadataKeyParser Resolves each metadata entry `key` string to [AuditMetadataKey].
 * @return domain model with strongly typed [AuditEventPrivate.id], [AuditEventPrivate.action], and [AuditEventPrivate.resource].
 */
fun AuditEventPrivatePayload.toAuditEventPrivate(
    compositeActionTypeParser: CompositeAuditActionTypeParser,
    compositeResourceTypeParser: CompositeAuditResourceTypeParser,
    compositeMetadataKeyParser: CompositeAuditMetadataKeyParser
): AuditEventPrivate = AuditEventPrivate(
    id = id.toAuditEventIdOrThrow(),
    actorId = actorId,
    actorType = AuditActorType.fromValueOrThrow(actorType),
    actorUserRole = actorUserRole,
    action = compositeActionTypeParser.fromValueOrThrow(action),
    resource = compositeResourceTypeParser.fromValueOrThrow(resource),
    resourceId = resourceId,
    status = AuditStatus.fromValueOrThrow(status),
    metadata = metadata.map { it.toAuditEventMetadata(compositeMetadataKeyParser) }.toSet(),
    message = message,
    createdAt = Instant.fromEpochMilliseconds(createdAt),
    updatedAt = updatedAt?.let(Instant::fromEpochMilliseconds)
)

/**
 * Builds a network [AuditEventPrivatePayload] from a domain [AuditEventPrivate].
 *
 * Writes [AuditEventPrivate.status] as a wire string matching [AuditStatus.serialName].
 * Maps [AuditEventPrivate.metadata] entries to [AuditEventMetadataPayload] payloads.
 * Converts [AuditEventPrivate.createdAt] ([Instant]) into epoch millis for [AuditEventPrivatePayload.createdAt].
 * Converts [AuditEventPrivate.updatedAt] ([Instant]) into epoch millis for [AuditEventPrivatePayload.updatedAt], if present.
 *
 * @return payload DTO aligned with the shared [AuditEventPrivatePayload] contract (`snake_case` keys, epoch millis timestamps).
 */
fun AuditEventPrivate.toAuditEventPrivatePayload(): AuditEventPrivatePayload = AuditEventPrivatePayload(
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
    createdAt = createdAt.toEpochMilliseconds(),
    updatedAt = updatedAt?.toEpochMilliseconds()
)
