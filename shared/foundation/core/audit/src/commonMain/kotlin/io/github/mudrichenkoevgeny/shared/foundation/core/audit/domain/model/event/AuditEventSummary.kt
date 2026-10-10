package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventSummaryPayload
import kotlin.time.Instant

/**
 * Condensed domain record of an audited action for list views and table entries.
 *
 * Captures core high-level audit information without heavy metadata or diagnostic messages.
 * Wire counterpart is [AuditEventSummaryPayload].
 *
 * @property id Unique event id; defaults to [AuditEventId.generate] when creating new instances.
 * @property actorType Category of the actor who initiated the action ([AuditActorType]).
 * @property action Action type executed ([AuditActionType]).
 * @property resource Resource type target ([AuditResourceType]).
 * @property status Outcome status of the action ([AuditStatus]).
 * @property createdAt Timestamp when the audit event occurred ([Instant]).
 */
data class AuditEventSummary(
    val id: AuditEventId = AuditEventId.generate(),
    val actorType: AuditActorType,
    val action: AuditActionType,
    val resource: AuditResourceType,
    val status: AuditStatus,
    val createdAt: Instant
)
