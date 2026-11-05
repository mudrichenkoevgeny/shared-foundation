package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import kotlin.time.Instant

/**
 * Complete server-side domain model and database entity representation for audit events.
 *
 * Adds server-side redaction policy [resourceValueSensitivity] for database storage and internal backend usage.
 * Immutable append-only record. **STRICT PROHIBITION:** MUST NEVER have a corresponding wire payload DTO.
 *
 * @property id Unique event id; defaults to [AuditEventId.generate] when creating new instances.
 * @property actorId Optional identifier of the user or system that performed the action.
 * @property actorType Category of the actor who initiated the action ([AuditActorType]).
 * @property actorUserRole Optional role snapshot when [actorType] is [AuditActorType.USER].
 * @property action Action type executed ([AuditActionType]).
 * @property resource Resource type target ([AuditResourceType]).
 * @property resourceId Optional identifier of the specific resource instance.
 * @property resourceValueSensitivity Redaction sensitivity classification for the target resource payload ([AuditValueSensitivity]).
 * @property status Outcome status of the action ([AuditStatus]).
 * @property metadata Set of diagnostic key-value entries ([AuditEventMetadata]).
 * @property message Optional human-readable message or diagnostic details.
 * @property createdAt Timestamp when the audit event occurred ([Instant]).
 */
public data class AuditEventInternal(
    public val id: AuditEventId = AuditEventId.generate(),
    public val actorId: String?,
    public val actorType: AuditActorType,
    public val actorUserRole: String?,
    public val action: AuditActionType,
    public val resource: AuditResourceType,
    public val resourceId: String?,
    public val resourceValueSensitivity: AuditValueSensitivity,
    val status: AuditStatus,
    public val metadata: Set<AuditEventMetadata>,
    public val message: String?,
    public val createdAt: Instant
)
