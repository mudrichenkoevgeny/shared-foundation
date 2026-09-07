package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

/**
 * Network path definitions for WebSocket connections.
 *
 * @property WS_OPEN_REALTIME_PATH The relative URL path for the open real-time communication endpoint.
 * @property WS_MANAGEMENT_REALTIME_PATH The relative URL path for the management real-time communication endpoint.
 */
object WebSocketContract {
    const val WS_OPEN_REALTIME_PATH = "/ws/realtime"
    const val WS_MANAGEMENT_REALTIME_PATH = "/management/ws/realtime"
}