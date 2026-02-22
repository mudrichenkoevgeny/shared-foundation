package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * DTO representing a single message frame exchanged over a WebSocket connection.
 *
 * @property id Unique identifier for the message frame.
 * @property type The type of event or message being transmitted.
 * @property payload Optional data associated with the message, represented as a [JsonElement].
 * @property metadata Additional key-value pairs containing contextual information.
 * @property timestamp The time the message was generated, in milliseconds.
 */
@Serializable
data class SocketFrame(
    @SerialName(CommonApiFields.ID)
    val id: String,

    @SerialName(CommonApiFields.TYPE)
    val type: String,

    @SerialName(CommonApiFields.PAYLOAD)
    val payload: JsonElement? = null,

    @SerialName(CommonApiFields.METADATA)
    val metadata: Map<String, String> = emptyMap(),

    @SerialName(CommonApiFields.TIMESTAMP)
    val timestamp: Long
)