package io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.network.model.websocket

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.SocketFrame
import io.github.mudrichenkoevgeny.shared.foundation.testing.core.common.TEST_TIME_MILLIS
import kotlinx.serialization.json.JsonElement

/**
 * Creates a test [SocketFrame] WebSocket envelope instance.
 *
 * @param id Frame identifier string.
 * @param type Frame message type string.
 * @param payload Optional JSON element payload.
 * @param metadata Key-value frame metadata.
 * @param timestamp Frame creation timestamp in epoch milliseconds.
 */
fun createTestSocketFrame(
    id: String = "frame_12345",
    type: String = "ping",
    payload: JsonElement? = null,
    metadata: Map<String, String> = emptyMap(),
    timestamp: Long = TEST_TIME_MILLIS
) = SocketFrame(
    id = id,
    type = type,
    payload = payload,
    metadata = metadata,
    timestamp = timestamp
)
