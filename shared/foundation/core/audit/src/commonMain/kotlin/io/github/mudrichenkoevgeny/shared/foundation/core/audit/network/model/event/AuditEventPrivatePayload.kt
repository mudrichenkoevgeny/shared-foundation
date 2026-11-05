package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Detailed audit event wire payload for client inspection and management flows.
 *
 * Wire keys use `snake_case` via [CommonApiFields] and [AuditEventFields]. Aligns with domain [AuditEventPrivate].
 *
 * @property id [AuditEventPrivate.id] on the wire: [AuditEventId] as hex-dash string.
 * @property actorId [AuditEventPrivate.actorId].
 * @property actorType [AuditEventPrivate.actorType]; wire values match [AuditActorType.value].
 * @property actorUserRole [AuditEventPrivate.actorUserRole].
 * @property action [AuditEventPrivate.action]; wire values match [AuditActionType.value].
 * @property resource [AuditEventPrivate.resource]; wire values match [AuditResourceType.value].
 * @property resourceId [AuditEventPrivate.resourceId].
 * @property status [AuditEventPrivate.status]; wire values match [AuditStatus.value].
 * @property metadata [AuditEventPrivate.metadata] as a JSON array of [AuditEventMetadataPayload] objects under [CommonApiFields.METADATA].
 * @property message [AuditEventPrivate.message].
 * @property createdAt [AuditEventPrivate.createdAt] as Unix epoch milliseconds ([Instant]).
 */
@Serializable
public data class AuditEventPrivatePayload(
    @SerialName(CommonApiFields.ID)
    public val id: String,
    @SerialName(AuditEventFields.ACTOR_ID)
    public val actorId: String?,
    @SerialName(AuditEventFields.ACTOR_TYPE)
    public val actorType: String,
    @SerialName(AuditEventFields.ACTOR_USER_ROLE)
    public val actorUserRole: String?,
    @SerialName(AuditEventFields.ACTION)
    public val action: String,
    @SerialName(AuditEventFields.RESOURCE)
    public val resource: String,
    @SerialName(AuditEventFields.RESOURCE_ID)
    public val resourceId: String?,
    @SerialName(AuditEventFields.STATUS)
    public val status: String,
    @SerialName(CommonApiFields.METADATA)
    public val metadata: List<AuditEventMetadataPayload>,
    @SerialName(CommonApiFields.MESSAGE)
    public val message: String?,
    @SerialName(CommonApiFields.CREATED_AT)
    public val createdAt: Long
)
