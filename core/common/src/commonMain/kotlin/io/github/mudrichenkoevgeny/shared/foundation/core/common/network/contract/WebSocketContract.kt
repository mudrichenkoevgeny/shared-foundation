package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

/**
 * Network path definitions for WebSocket connections.
 *
 * @property WS_OPEN_REALTIME_PATH The relative URL path for the open real-time communication endpoint.
 * @property WS_MANAGEMENT_REALTIME_PATH The relative URL path for the management real-time communication endpoint.
 */
public object WebSocketContract {
    public const val WS_OPEN_REALTIME_PATH: String = "/ws/realtime"
    public const val WS_MANAGEMENT_REALTIME_PATH: String = "/management/ws/realtime"
}
