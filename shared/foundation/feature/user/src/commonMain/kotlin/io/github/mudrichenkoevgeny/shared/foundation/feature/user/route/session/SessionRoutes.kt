package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.session

/**
 * Object containing route definitions for session management endpoints.
 *
 * @property BASE_SESSION_ROUTE The base path for session-related operations.
 * @property LOGOUT The endpoint to terminate the current active session.
 * @property GET_SESSIONS The endpoint to retrieve a list of all active sessions for the user.
 * @property DELETE_SESSION The endpoint to terminate a specific session by its unique identifier.
 * @property DELETE_ALL_OTHER_SESSIONS The endpoint to terminate all sessions except for the current one.
 */
object SessionRoutes {
    const val BASE_SESSION_ROUTE = "/session"
    const val LOGOUT = "$BASE_SESSION_ROUTE/logout"
    const val GET_SESSIONS = BASE_SESSION_ROUTE
    const val DELETE_SESSION = "$BASE_SESSION_ROUTE/{id}"
    const val DELETE_ALL_OTHER_SESSIONS = "$BASE_SESSION_ROUTE/delete-others"
}