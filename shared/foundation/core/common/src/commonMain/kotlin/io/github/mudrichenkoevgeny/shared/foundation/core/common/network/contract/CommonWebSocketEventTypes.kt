package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.model.websocket.WebSocketInitializePayload

/**
 * Common event types used for maintaining and monitoring the WebSocket connection.
 */
public object CommonWebSocketEventTypes {
    /**
     * Sent to verify the connection is still alive.
     * The receiver should respond with a [PONG] event.
     */
    public const val PING: String = "PING"

    /**
     * Response sent back to acknowledge a [PING] event.
     */
    public const val PONG: String = "PONG"

    /**
     * The first message sent by the client after connection to provide metadata.
     * Payload: [WebSocketInitializePayload]
     */
    public const val INITIALIZE: String = "INITIALIZE"

    /**
     * Confirmation sent by the server after successfully processing [INITIALIZE] metadata.
     */
    public const val INITIALIZED_SUCCESS: String = "INITIALIZED_SUCCESS"
}
