package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

/**
 * Common event types used for maintaining and monitoring the WebSocket connection.
 *
 * @property PING Sent to verify the connection is still alive.
 * @property PONG Response sent back to acknowledge a PING.
 */
object CommonWebSocketEventTypes {
    const val PING = "PING"
    const val PONG = "PONG"
}