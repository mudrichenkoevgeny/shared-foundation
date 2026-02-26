package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

/**
 * General system-level reasons for closing a WebSocket connection.
 */
object CommonWebSocketCloseReasons {
    /** Standard connection closure without errors. */
    const val NORMAL = "Normal"

    /** Server is shutting down or restarting for maintenance. */
    const val SERVER_RESTART = "Server restart"

    /** An unexpected internal server error occurred during the session. */
    const val INTERNAL_SERVER_ERROR = "Internal server error"
}