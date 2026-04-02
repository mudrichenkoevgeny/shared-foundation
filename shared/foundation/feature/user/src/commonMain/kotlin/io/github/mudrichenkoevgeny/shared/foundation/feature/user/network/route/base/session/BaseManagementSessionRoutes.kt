package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseManagementUserRoutes

/**
 * Shared base path segments for another user’s sessions in the management API (read and delete).
 *
 * Masked and unmasked session read responses use different DTOs; separate URLs keep one wire shape per endpoint.
 */
object BaseManagementSessionRoutes {
    /**
     * Path prefix for session **list** endpoints under management. Target user is supplied via query
     * [UserFilterValues.UserSessionFilterValues.USER_ID], not the path.
     */
    const val BASE_MANAGEMENT_SESSIONS_LIST_ROUTE =
        "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/sessions"

    /**
     * Path template for session operations scoped to a specific user ([UserApiPaths.USER_ID] in path): single-session
     * reads and deletes.
     */
    const val BASE_MANAGEMENT_USER_SESSIONS_ROUTE =
        "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/{${UserApiPaths.USER_ID}}/sessions"

    /**
     * Path for the masked-IP session list (caller must hold a masked-list permission).
     */
    const val GET_USER_SESSIONS_MASKED = "$BASE_MANAGEMENT_SESSIONS_LIST_ROUTE/masked"

    /**
     * Path for the unmasked-IP session list (caller must hold an unmasked-list permission).
     */
    const val GET_USER_SESSIONS_UNMASKED = "$BASE_MANAGEMENT_SESSIONS_LIST_ROUTE/unmasked"

    /**
     * Path template for one session with **masked** IP; path parameters [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     * Caller must hold a masked-list permission (see [SessionPermissionCodes]).
     */
    const val GET_USER_SESSION_MASKED =
        "$BASE_MANAGEMENT_USER_SESSIONS_ROUTE/masked/{${UserApiPaths.SESSION_ID}}"

    /**
     * Path template for one session with **unmasked** IP; path parameters [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     * Caller must hold an unmasked-list permission (see [SessionPermissionCodes]).
     */
    const val GET_USER_SESSION_UNMASKED =
        "$BASE_MANAGEMENT_USER_SESSIONS_ROUTE/unmasked/{${UserApiPaths.SESSION_ID}}"

    /**
     * Path template for terminating one session of a user under management; path parameters [UserApiPaths.USER_ID],
     * [UserApiPaths.SESSION_ID]. Caller must hold a delete permission for the target account role (see
     * [SessionPermissionCodes]).
     */
    const val DELETE_USER_SESSION =
        "$BASE_MANAGEMENT_USER_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * Path template for terminating **all** sessions of a user under management; path parameter [UserApiPaths.USER_ID].
     * Caller must hold a delete permission for the target account role (see [SessionPermissionCodes]).
     */
    const val DELETE_ALL_USER_SESSIONS = BASE_MANAGEMENT_USER_SESSIONS_ROUTE
}
