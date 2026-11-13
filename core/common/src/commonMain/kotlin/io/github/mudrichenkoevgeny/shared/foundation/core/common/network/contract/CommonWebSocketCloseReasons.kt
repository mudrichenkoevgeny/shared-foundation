package io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract

/**
 * General system-level reasons for closing a WebSocket connection.
 */
public object CommonWebSocketCloseReasons {
    /** Standard connection closure without errors. */
    public const val NORMAL: String = "Normal"

    /** Server is shutting down or restarting for maintenance. */
    public const val SERVER_RESTART: String = "Server restart"

    /** An unexpected internal server error occurred during the session. */
    public const val INTERNAL_SERVER_ERROR: String = "Internal server error"
}
