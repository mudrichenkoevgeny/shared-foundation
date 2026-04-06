package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.session

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.contract.CommonApiFields
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.SessionPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.listing.UserFilterValues
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
     * Path prefix for session **list** under management. Target user: query
     * [UserFilterValues.UserSessionFilterValues.USER_ID].
     */
    const val BASE_MANAGEMENT_SESSIONS_LIST_ROUTE =
        "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/sessions"

    /**
     * Paginated session list; masking is per item.
     */
    const val GET_USER_SESSIONS = BASE_MANAGEMENT_SESSIONS_LIST_ROUTE

    /**
     * Path template for session ops for a user: [UserApiPaths.USER_ID] in path.
     */
    const val BASE_MANAGEMENT_USER_SESSIONS_ROUTE =
        "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/{${UserApiPaths.USER_ID}}/sessions"

    /**
     * One session row; path parameters [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     */
    const val GET_USER_SESSION =
        "$BASE_MANAGEMENT_USER_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

    /**
     * Terminate one session; path parameters [UserApiPaths.USER_ID], [UserApiPaths.SESSION_ID].
     */
    const val DELETE_USER_SESSION = GET_USER_SESSION

    /**
     * Terminate all sessions for a user; path parameter [UserApiPaths.USER_ID].
     */
    const val DELETE_ALL_USER_SESSIONS = BASE_MANAGEMENT_USER_SESSIONS_ROUTE
}
