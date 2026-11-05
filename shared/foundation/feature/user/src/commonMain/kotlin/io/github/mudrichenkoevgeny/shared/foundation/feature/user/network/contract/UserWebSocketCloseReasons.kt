package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract

/**
 * User-level reasons for closing a WebSocket connection.
 */
public object UserWebSocketCloseReasons {
    /** Failed to authenticate the provided token or token is missing. */
    public const val AUTH_FAILED: String = "Auth failed"

    /** The session or access token has reached its expiration time. */
    public const val SESSION_EXPIRED: String = "Session expired"

    /** The session was manually deleted. */
    public const val SESSION_DELETED: String = "Session deleted"

    /** The user account has been banned or deactivated. */
    public const val USER_BANNED: String = "User banned"
}
