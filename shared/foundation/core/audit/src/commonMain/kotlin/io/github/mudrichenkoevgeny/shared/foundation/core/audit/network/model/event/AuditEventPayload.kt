package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * A persisted audit event: who did what, to which resource, with what outcome, and optional diagnostics.
 *
 * Shared by HTTP APIs ([Serializable] JSON body and list entries), services, repositories, and clients. Wire keys use
 * snake_case via [CommonApiFields] and [AuditEventFields]. Aligns with domain [AuditEvent].
 *
 * @property id [AuditEvent.id] on the wire: [AuditEventId] as hex-dash string.
 * @property actorId [AuditEvent.actorId].
 * @property actorType [AuditEvent.actorType]; wire values match [AuditActorType.serialName].
 * @property actorUserRole [AuditEvent.actorUserRole].
 * @property action [AuditEvent.action]; wire values match [AuditActionType.serialName].
 * @property resource [AuditEvent.resource]; wire values match [AuditResourceType.serialName].
 * @property resourceId [AuditEvent.resourceId].
 * @property status [AuditEvent.status]; wire values match [AuditStatus.serialName].
 * @property metadata [AuditEvent.metadata] as a JSON array of [AuditEventMetadataPayload] objects under [CommonApiFields.METADATA].
 * @property message [AuditEvent.message].
 * @property createdAt [AuditEvent.createdAt] as Unix epoch milliseconds ([Instant]).
 */
@Serializable
data class AuditEventPayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(AuditEventFields.ACTOR_ID)
    val actorId: String? = null,

    @SerialName(AuditEventFields.ACTOR_TYPE)
    val actorType: String,

    @SerialName(AuditEventFields.ACTOR_USER_ROLE)
    val actorUserRole: String? = null,

    @SerialName(AuditEventFields.ACTION)
    val action: String,

    @SerialName(AuditEventFields.RESOURCE)
    val resource: String,

    @SerialName(AuditEventFields.RESOURCE_ID)
    val resourceId: String? = null,

    @SerialName(AuditEventFields.STATUS)
    val status: String,

    @SerialName(CommonApiFields.METADATA)
    val metadata: List<AuditEventMetadataPayload> = emptyList(),

    @SerialName(CommonApiFields.MESSAGE)
    val message: String? = null,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long
)