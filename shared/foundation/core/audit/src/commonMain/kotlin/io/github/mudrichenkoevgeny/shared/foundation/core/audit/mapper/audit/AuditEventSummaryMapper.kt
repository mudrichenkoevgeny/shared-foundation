package io.github.mudrichenkoevgeny.shared.foundation.core.audit.mapper.audit

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.CompositeAuditActionTypeParser
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.toAuditEventIdOrThrow
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.CompositeAuditResourceTypeParser
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventSummaryPayload
import kotlin.time.Instant

/**
 * Extension functions mapping summary audit event models between domain and network representations.
 */

/**
 * Builds a domain [AuditEventSummary] from [AuditEventSummaryPayload].
 *
 * Parses [AuditEventSummaryPayload.status] wire string value into [AuditStatus] (throws if invalid).
 * Converts [AuditEventSummaryPayload.createdAt] epoch millis into [AuditEventSummary.createdAt] ([Instant]).
 *
 * @param compositeActionTypeParser Resolves payload `action` strings to [AuditActionType].
 * @param compositeResourceTypeParser Resolves payload `resource` strings to [AuditResourceType].
 * @return domain model with strongly typed [AuditEventSummary.id], [AuditEventSummary.action], and [AuditEventSummary.resource].
 */
fun AuditEventSummaryPayload.toAuditEventSummary(
    compositeActionTypeParser: CompositeAuditActionTypeParser,
    compositeResourceTypeParser: CompositeAuditResourceTypeParser
): AuditEventSummary = AuditEventSummary(
    id = id.toAuditEventIdOrThrow(),
    actorType = AuditActorType.fromValueOrThrow(actorType),
    action = compositeActionTypeParser.fromValueOrThrow(action),
    resource = compositeResourceTypeParser.fromValueOrThrow(resource),
    status = AuditStatus.fromValueOrThrow(status),
    createdAt = Instant.fromEpochMilliseconds(createdAt)
)

/**
 * Builds a network [AuditEventSummaryPayload] from a domain [AuditEventSummary].
 *
 * Writes [AuditEventSummary.status] as a wire string matching [AuditStatus.serialName].
 * Converts [AuditEventSummary.createdAt] ([Instant]) into epoch millis for [AuditEventSummaryPayload.createdAt].
 *
 * @return payload DTO aligned with the shared [AuditEventSummaryPayload] contract (`snake_case` keys, epoch millis timestamps).
 */
fun AuditEventSummary.toAuditEventSummaryPayload(): AuditEventSummaryPayload = AuditEventSummaryPayload(
    id = id.asHexDashString(),
    actorType = actorType.serialName,
    action = action.serialName,
    resource = resource.serialName,
    status = status.serialName,
    createdAt = createdAt.toEpochMilliseconds()
)
