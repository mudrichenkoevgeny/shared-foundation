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
 * @property actorType [AuditEventPrivate.actorType]; wire values match [AuditActorType.serialName].
 * @property actorUserRole [AuditEventPrivate.actorUserRole].
 * @property action [AuditEventPrivate.action]; wire values match [AuditActionType.serialName].
 * @property resource [AuditEventPrivate.resource]; wire values match [AuditResourceType.serialName].
 * @property resourceId [AuditEventPrivate.resourceId].
 * @property status [AuditEventPrivate.status]; wire values match [AuditStatus.serialName].
 * @property metadata [AuditEventPrivate.metadata] as a JSON array of [AuditEventMetadataPayload] objects under [CommonApiFields.METADATA].
 * @property message [AuditEventPrivate.message].
 * @property createdAt [AuditEventPrivate.createdAt] as Unix epoch milliseconds ([Instant]).
 */
@Serializable
data class AuditEventPrivatePayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(AuditEventFields.ACTOR_ID)
    val actorId: String?,

    @SerialName(AuditEventFields.ACTOR_TYPE)
    val actorType: String,

    @SerialName(AuditEventFields.ACTOR_USER_ROLE)
    val actorUserRole: String?,

    @SerialName(AuditEventFields.ACTION)
    val action: String,

    @SerialName(AuditEventFields.RESOURCE)
    val resource: String,

    @SerialName(AuditEventFields.RESOURCE_ID)
    val resourceId: String?,

    @SerialName(AuditEventFields.STATUS)
    val status: String,

    @SerialName(CommonApiFields.METADATA)
    val metadata: List<AuditEventMetadataPayload>,

    @SerialName(CommonApiFields.MESSAGE)
    val message: String?,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long
)
