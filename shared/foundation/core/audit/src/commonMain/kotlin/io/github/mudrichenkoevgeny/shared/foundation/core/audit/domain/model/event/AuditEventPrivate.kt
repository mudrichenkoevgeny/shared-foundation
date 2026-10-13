package io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.AuditEventMetadata
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event.AuditEventPrivatePayload
import kotlin.time.Instant

/**
 * Detailed domain representation of an audited action for client inspection and management flows.
 *
 * Includes full actor/resource identifiers, diagnostic messages, and metadata entries.
 * Wire counterpart is [AuditEventPrivatePayload].
 *
 * @property id Unique event id; defaults to [AuditEventId.generate] when creating new instances.
 * @property actorId Optional identifier of the user or system that performed the action.
 * @property actorType Category of the actor who initiated the action ([AuditActorType]).
 * @property actorUserRole Optional role snapshot when [actorType] is [AuditActorType.USER].
 * @property action Action type executed ([AuditActionType]).
 * @property resource Resource type target ([AuditResourceType]).
 * @property resourceId Optional identifier of the specific resource instance.
 * @property status Outcome status of the action ([AuditStatus]).
 * @property metadata Set of diagnostic key-value entries ([AuditEventMetadata]).
 * @property message Optional human-readable message or diagnostic details.
 * @property createdAt Timestamp when the audit event occurred ([Instant]).
 * @property updatedAt Timestamp when the audit event record was last modified ([Instant]), or `null`.
 */
data class AuditEventPrivate(
    val id: AuditEventId = AuditEventId.generate(),
    val actorId: String?,
    val actorType: AuditActorType,
    val actorUserRole: String?,
    val action: AuditActionType,
    val resource: AuditResourceType,
    val resourceId: String?,
    val status: AuditStatus,
    val metadata: Set<AuditEventMetadata>,
    val message: String?,
    val createdAt: Instant,
    val updatedAt: Instant?
)
