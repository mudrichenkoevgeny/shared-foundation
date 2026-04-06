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
 * @param id Unique event id; defaults to [AuditEventId.generate] when creating new events.
 * @param actorId Optional identifier of the user or system that performed the action.
 * @param actorType Actor category for filtering and access decisions.
 * @param actorUserRole Optional role snapshot when [actorType] is [AuditActorType.USER].
 * @param action Action type of the event.
 * @param resource Resource type of the event.
 * @param resourceId Optional id of the specific resource instance.
 * @param resourceValueSensitivity How [resourceId] should be treated when shown or exported (redaction policy);
 *   defaults to [AuditValueSensitivity.NON_SENSITIVE].
 * @param status Outcome of the action ([AuditStatus]).
 * @param metadata Optional key-value data for filtering or analytics.
 * @param message Optional human-readable message (e.g. error description).
 * @param createdAt Event creation time as a multiplatform [Instant].
 */
data class AuditEvent(
    val id: AuditEventId = AuditEventId.generate(),
    val actorId: String? = null,
    val actorType: AuditActorType,
    val actorUserRole: String? = null,
    val action: AuditActionType,
    val resource: AuditResourceType,
    val resourceId: String? = null,
    val resourceValueSensitivity: AuditValueSensitivity = AuditValueSensitivity.NON_SENSITIVE,
    val status: AuditStatus,
    val metadata: Set<AuditEventMetadata> = emptySet(),
    val message: String? = null,
    val createdAt: Instant
)