package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.payload.WebSocketInitializePayload
/**
 * Common event types used for maintaining and monitoring the WebSocket connection.
 */
object CommonWebSocketEventTypes {
    /**
     * Sent to verify the connection is still alive.
     * The receiver should respond with a [PONG] event.
     */
    const val PING = "PING"

    /**
     * Response sent back to acknowledge a [PING] event.
     */
    const val PONG = "PONG"

    /**
     * The first message sent by the client after connection to provide metadata.
     * Payload: [WebSocketInitializePayload]
     */
    const val INITIALIZE = "INITIALIZE"

    /**
     * Confirmation sent by the server after successfully processing [INITIALIZE] metadata.
     */
    const val INITIALIZED_SUCCESS = "INITIALIZED_SUCCESS"
}