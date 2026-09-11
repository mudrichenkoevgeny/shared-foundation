package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import kotlin.time.Instant

/**
 * Immutable record of an audited action in the system.
 *
 * Captures who ([actorId], [actorType], [actorUserRole]) performed which [action] on which [resource]
 * (and optional [resourceId]), with [status] and optional [message] and [metadata] for diagnostics.
 *
 * @property id Unique event id; defaults to [AuditEventId.generate] when creating new events.
 * @property actorId Optional identifier of the user or system that performed the action.
 * @property actorType Actor category for filtering and access decisions.
 * @property actorUserRole Optional role snapshot when [actorType] is [AuditActorType.USER].
 * @property action Action type of the event.
 * @property resource Resource type of the event.
 * @property resourceId Optional id of the specific resource instance.
 * @property resourceValueSensitivity How [resourceId] should be treated when shown or exported (redaction policy).
 * @property status Outcome of the action ([AuditStatus]).
 * @property metadata Optional key-value data for filtering or analytics.
 * @property message Optional human-readable message (e.g. error description).
 * @property createdAt Event creation time as a multiplatform [Instant].
 */
data class AuditEvent(
    val id: AuditEventId = AuditEventId.generate(),
    val actorId: String?,
    val actorType: AuditActorType,
    val actorUserRole: String?,
    val action: AuditActionType,
    val resource: AuditResourceType,
    val resourceId: String?,
    val resourceValueSensitivity: AuditValueSensitivity,
    val status: AuditStatus,
    val metadata: Set<AuditEventMetadata>,
    val message: String?,
    val createdAt: Instant
)