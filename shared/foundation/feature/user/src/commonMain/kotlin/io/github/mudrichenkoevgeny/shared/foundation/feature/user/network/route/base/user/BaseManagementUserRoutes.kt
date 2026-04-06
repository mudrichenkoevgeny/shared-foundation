package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths

/**
 * Shared base path segments for user management endpoints.
 */
object BaseManagementUserRoutes {
    /**
     * Base path for user management operations under [ManagementRoutes.BASE_MANAGEMENT_ROUTE].
     */
    const val BASE_MANAGEMENT_USERS_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}/users"

    /**
     * Path for creating a user (`POST`); same URL as [GET_USERS], different HTTP method.
     */
    const val CREATE_USER = BASE_MANAGEMENT_USERS_ROUTE

    /**
     * Path for listing users (filtering, sorting, and pagination via query parameters).
     */
    const val GET_USERS = BASE_MANAGEMENT_USERS_ROUTE

    /**
     * Path template for retrieving a user; includes the [UserApiPaths.USER_ID] path parameter.
     */
    const val GET_USER = "$BASE_MANAGEMENT_USERS_ROUTE/{${UserApiPaths.USER_ID}}"

    /**
     * Path template for updating a user's account status; includes the [UserApiPaths.USER_ID] path parameter.
     */
    const val UPDATE_USER_ACCOUNT_STATUS = "$BASE_MANAGEMENT_USERS_ROUTE/{${UserApiPaths.USER_ID}}/status"

    /**
     * Path template for replacing a user's explicit permission grants; includes the [UserApiPaths.USER_ID] path parameter.
     */
    const val UPDATE_USER_PERMISSIONS = "$BASE_MANAGEMENT_USERS_ROUTE/{${UserApiPaths.USER_ID}}/permissions"

    /**
     * Path template for deleting a user; includes the [UserApiPaths.USER_ID] path parameter.
     */
    const val DELETE_USER = "$BASE_MANAGEMENT_USERS_ROUTE/{${UserApiPaths.USER_ID}}"
}
