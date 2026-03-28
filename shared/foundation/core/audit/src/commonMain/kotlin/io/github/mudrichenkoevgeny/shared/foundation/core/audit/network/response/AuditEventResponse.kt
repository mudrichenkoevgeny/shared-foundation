package io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.response

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.AuditStatus
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.network.contract.AuditApiFields
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * JSON DTO for a persisted audit event (e.g. management listing).
 *
 * Aligns with the backend audit event record: actor, action, resource, outcome, optional diagnostics,
 * and event identifier. Wire keys use snake_case via [CommonApiFields] and [AuditApiFields].
 *
 * @property id Unique event id (UUID string, hex with dashes).
 * @property actorId Optional id of the user or system that performed the action (UUID string).
 * @property action Action name (e.g. login, create_order).
 * @property resource Resource type (e.g. order, user).
 * @property resourceId Optional id of the specific resource instance.
 * @property status Outcome of the action.
 * @property metadata Optional structured data for filtering or analytics.
 * @property message Optional human-readable message (e.g. error description).
 * @property createdAt Event creation time, Unix epoch milliseconds.
 */
@Serializable
data class AuditEventResponse(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(AuditApiFields.ACTOR_ID)
    val actorId: String? = null,

    @SerialName(AuditApiFields.ACTION)
    val action: String,

    @SerialName(AuditApiFields.RESOURCE)
    val resource: String,

    @SerialName(AuditApiFields.RESOURCE_ID)
    val resourceId: String? = null,

    @SerialName(AuditApiFields.STATUS)
    val status: AuditStatus,

    @SerialName(CommonApiFields.METADATA)
    val metadata: Map<String, JsonElement> = emptyMap(),

    @SerialName(CommonApiFields.MESSAGE)
    val message: String? = null,

    @SerialName(CommonApiFields.CREATED_AT)
    val createdAt: Long
)
