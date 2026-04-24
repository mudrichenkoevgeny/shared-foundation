package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.session.UserSessionPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseManagementUserRoutes

/**
 * Shared base path segments for another user’s sessions in the management API (read and delete).
 *
 * Response items are [UserSessionPayload]; [CommonApiFields.IS_SENSITIVE_VALUES_MASKED] reflects what the caller may see
 * ([SessionPermissionCode]).
 */
object BaseManagementSessionRoutes {
    /**
     * Base path for managing user sessions
     */
    const val BASE_MANAGEMENT_SESSIONS_ROUTE =
        "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/sessions"

    /**
     * Paginated session list.
     */
    const val GET_SESSIONS = BASE_MANAGEMENT_SESSIONS_ROUTE

    /**
     * One session row; path parameter [UserApiPaths.SESSION_ID].
     */
    const val GET_SESSION = "$BASE_MANAGEMENT_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * Delete one session; path parameter [UserApiPaths.SESSION_ID].
     */
    const val DELETE_SESSION = "$BASE_MANAGEMENT_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * Delete all sessions for a user; path parameter [UserApiPaths.USER_ID].
     */
    const val DELETE_ALL_USER_SESSIONS = "$BASE_MANAGEMENT_SESSIONS_ROUTE/delete-all-for-user/{${UserApiPaths.USER_ID}}"
}
