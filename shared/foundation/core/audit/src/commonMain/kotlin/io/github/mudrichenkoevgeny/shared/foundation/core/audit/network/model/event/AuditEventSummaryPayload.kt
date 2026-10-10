package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventId
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventSummary
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Condensed audit event wire payload for list views and table entries.
 *
 * Wire keys use `snake_case` via [CommonApiFields] and [AuditEventFields]. Aligns with domain [AuditEventSummary].
 *
 * @property id [AuditEventSummary.id] on the wire: [AuditEventId] as hex-dash string.
 * @property actorType [AuditEventSummary.actorType]; wire values match [AuditActorType.serialName].
 * @property action [AuditEventSummary.action]; wire values match [AuditActionType.serialName].
 * @property resource [AuditEventSummary.resource]; wire values match [AuditResourceType.serialName].
 * @property status [AuditEventSummary.status]; wire values match [AuditStatus.serialName].
 * @property createdAt [AuditEventSummary.createdAt] as Unix epoch milliseconds ([Instant]).
 */
@Serializable
data class AuditEventSummaryPayload(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(AuditEventFields.ACTOR_TYPE)
    val actorType: String,

    @SerialName(AuditEventFields.ACTION)
    val action: String,

    @SerialName(AuditEventFields.RESOURCE)
    val resource: String,

    @SerialName(AuditEventFields.STATUS)
    val status: String,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long
)
