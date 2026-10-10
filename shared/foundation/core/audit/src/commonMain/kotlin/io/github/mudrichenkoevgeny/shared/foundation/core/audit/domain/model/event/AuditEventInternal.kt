package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import kotlin.time.Instant

/**
 * Internal audit event row for server-side storage and business logic.
 *
 * Adds server-side redaction policy [resourceValueSensitivity] and database row modification timestamp [updatedAt]
 * to [AuditEventPrivate]-equivalent fields. These internal fields must not be exposed directly via public API payloads.
 *
 * @property id Unique event id; defaults to [AuditEventId.generate] when creating new instances.
 * @property actorId Optional identifier of the user or system that performed the action.
 * @property actorType Category of the actor who initiated the action ([AuditActorType]).
 * @property actorUserRole Optional role snapshot when [actorType] is [AuditActorType.USER].
 * @property action Action type executed ([AuditActionType]).
 * @property resource Resource type target ([AuditResourceType]).
 * @property resourceId Optional identifier of the specific resource instance.
 * @property resourceValueSensitivity Redaction sensitivity policy for [resourceId] ([AuditValueSensitivity]).
 * @property status Outcome status of the action ([AuditStatus]).
 * @property metadata Set of diagnostic key-value entries ([AuditEventMetadata]).
 * @property message Optional human-readable message or diagnostic details.
 * @property createdAt Timestamp when the audit event was created ([Instant]).
 * @property updatedAt Timestamp when the audit event row was last modified ([Instant]), or `null` if unchanged.
 */
data class AuditEventInternal(
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
    val createdAt: Instant,
    val updatedAt: Instant?
)
