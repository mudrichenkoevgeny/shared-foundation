package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths

/**
 * Shared base path segments for session management in the open API.
 */
object BaseOpenSessionRoutes {
    /**
     * Base path for session-related operations.
     */
    const val BASE_SESSION_ROUTE = "/session"

    /**
     * Path for deleting the current active session.
     */
    const val LOGOUT = "$BASE_SESSION_ROUTE/logout"

    /**
     * Path for retrieving active sessions for the current user.
     */
    const val GET_SESSIONS = BASE_SESSION_ROUTE

    /**
     * Path template for a single session by id under [BASE_SESSION_ROUTE]; includes [UserApiPaths.SESSION_ID].
     * Use with `GET` ([GET_SESSION]) or `DELETE` ([DELETE_SESSION]) as appropriate.
     */
    const val SESSION = "$BASE_SESSION_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * Path template for retrieving a specific session owned by the current user; includes [UserApiPaths.SESSION_ID].
     * Same path as [DELETE_SESSION]; HTTP method distinguishes operations.
     */
    const val GET_SESSION = SESSION

    /**
     * Path template for deleting a specific session; includes the [UserApiPaths.SESSION_ID] path parameter.
     */
    const val DELETE_SESSION = SESSION

    /**
     * Path for deleting all sessions except the current one.
     */
    const val DELETE_ALL_OTHER_SESSIONS = "$BASE_SESSION_ROUTE/delete-others"

    /**
     * Path for session re-authentication via TOTP to elevate trust level (step-up).
     */
    const val REAUTHENTICATE_SESSION = "$BASE_SESSION_ROUTE/reauthenticate"
}
