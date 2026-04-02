package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.model.event

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.contract.AuditEventFields
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.status.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * A persisted audit event: who did what, to which resource, with what outcome, and optional diagnostics.
 *
 * Shared by HTTP APIs ([Serializable] JSON body and list entries), services, repositories, and clients. Wire keys use
 * snake_case via [CommonApiFields] and [AuditEventFields].
 *
 * @property id Unique event id (UUID string, hex with dashes).
 * @property actorId Optional id of the user or system that performed the action.
 * @property actorType Actor type; values follow [AuditActorType].
 * @property actorUserRole Optional role snapshot for user actor (`actorType = user`).
 * @property action Action name (e.g. `login`, `create_order`).
 * @property resource Resource type (e.g. `order`, `user`).
 * @property resourceId Optional id of the specific resource instance.
 * @property status Outcome of the action as a wire string matching [AuditStatus] serial names.
 * @property metadata Optional structured data for filtering or analytics.
 * @property message Optional human-readable message (e.g. error description).
 * @property createdAt Event creation time, Unix epoch milliseconds.
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
    val metadata: Map<String, JsonElement> = emptyMap(),

    @SerialName(CommonApiFields.MESSAGE)
    val message: String? = null,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long
)