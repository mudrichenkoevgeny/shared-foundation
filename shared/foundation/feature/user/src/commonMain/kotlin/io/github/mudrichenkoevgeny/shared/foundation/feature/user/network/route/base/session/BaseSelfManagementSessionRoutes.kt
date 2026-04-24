package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths

/**
 * Shared base path segments for the current authenticated principal's sessions in the management API.
 */
object BaseSelfManagementSessionRoutes {
    /**
     * Base path for self-service session operations in management API.
     */
    const val BASE_SELF_SESSION_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}/self/session"

    /**
     * Path for deleting the current active management session.
     */
    const val LOGOUT = "$BASE_SELF_SESSION_ROUTE/logout"

    /**
     * Path for retrieving active sessions for the current management account.
     */
    const val GET_SESSIONS = BASE_SELF_SESSION_ROUTE

    /**
     * Path template for a single session owned by the current principal; includes [UserApiPaths.SESSION_ID].
     */
    const val SESSION = "$BASE_SELF_SESSION_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * Path template for retrieving a specific session.
     */
    const val GET_SESSION = SESSION

    /**
     * Path template for deleting a specific session.
     */
    const val DELETE_SESSION = SESSION

    /**
     * Path for deleting all sessions except the current one for the management account.
     */
    const val DELETE_ALL_OTHER_SESSIONS = "$BASE_SELF_SESSION_ROUTE/delete-others"

    /**
     * Path for management session re-authentication via TOTP to elevate trust level.
     */
    const val REAUTHENTICATE_SESSION = "$BASE_SELF_SESSION_ROUTE/reauthenticate"
}