package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths

/**
 * Shared base path segments for session management in the open API.
 */
object BaseSessionRoutes {
    /**
     * Base path for session-related operations.
     */
    const val BASE_SESSION_ROUTE = "/session"

    /**
     * Path for terminating the current active session.
     */
    const val LOGOUT = "$BASE_SESSION_ROUTE/logout"

    /**
     * Path for retrieving active sessions for the current user.
     */
    const val GET_SESSIONS = BASE_SESSION_ROUTE

    /**
     * Path template for a single session by id under [BASE_SESSION_ROUTE]; includes [UserApiPaths.SESSION_ID].
     * Use with `GET` ([GET_SESSION_BY_ID]) or `DELETE` ([DELETE_SESSION]) as appropriate.
     */
    const val SESSION_BY_ID = "$BASE_SESSION_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * Path template for retrieving a specific session owned by the current user; includes [UserApiPaths.SESSION_ID].
     * Same path as [DELETE_SESSION]; HTTP method distinguishes operations.
     */
    const val GET_SESSION_BY_ID = SESSION_BY_ID

    /**
     * Path template for terminating a specific session; includes the [UserApiPaths.SESSION_ID] path parameter.
     */
    const val DELETE_SESSION = SESSION_BY_ID

    /**
     * Path for terminating all sessions except the current one.
     */
    const val DELETE_ALL_OTHER_SESSIONS = "$BASE_SESSION_ROUTE/delete-others"
}
