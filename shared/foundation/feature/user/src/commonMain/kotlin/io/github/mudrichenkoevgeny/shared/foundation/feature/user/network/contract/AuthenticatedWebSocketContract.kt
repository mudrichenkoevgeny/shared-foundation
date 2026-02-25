package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.WebSocketContract

/**
 * Network path definitions for authenticated WebSocket connections.
 *
 * Provides a method to construct a full URL path including authentication credentials.
 */
object AuthenticatedWebSocketContract {
    /**
     * Constructs a full path for the real-time WebSocket endpoint with a token query parameter.
     *
     * @param token The access token used for authentication.
     * @return A string representing the path with the injected token (e.g., "/ws/realtime?token=abc").
     */
    fun getFullAuthPath(token: String): String {
        return "${WebSocketContract.WS_REALTIME_PATH}?$${UserApiQueryParams.TOKEN}=$token"
    }
}