package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

/**
 * User-level reasons for closing a WebSocket connection.
 */
object UserWebSocketCloseReasons {
    /** Failed to authenticate the provided token or token is missing. */
    const val AUTH_FAILED = "Auth failed"

    /** The session was manually revoked. */
    const val SESSION_REVOKED = "Session revoked"

    /** The user account has been banned or deactivated. */
    const val USER_BLOCKED = "User blocked"
}