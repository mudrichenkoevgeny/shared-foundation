package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths

/**
 * Object containing route definitions for user-related admin API endpoints.
 *
 * @property BASE_ADMIN_USER_ROUTE The base path for user operations in the admin API.
 * @property GET_USERS_LIST `GET` The endpoint to list users with pagination, sorting, and filters.
 * @property GET_USER `GET` The endpoint to retrieve a user by id.
 * @property UPDATE_USER_ACCOUNT_STATUS `PATCH` The endpoint to change a user's account status.
 * @property GET_USER_AUDIT_LOG `GET` The endpoint to retrieve a user's audit log with pagination and filters.
 */
object AdminUserRoutes {
    const val BASE_ADMIN_USER_ROUTE = "/admin/users"

    /**
     * **HTTP method:** `GET`
     *
     * Returns the admin user list endpoint. Filtering, sorting, and pagination
     * are expected via query parameters.
     */
    const val GET_USERS_LIST = BASE_ADMIN_USER_ROUTE
    const val GET_USER = "$BASE_ADMIN_USER_ROUTE/{${UserApiPaths.USER_ID}}"
    const val UPDATE_USER_ACCOUNT_STATUS = "$BASE_ADMIN_USER_ROUTE/{${UserApiPaths.USER_ID}}/status"
    const val GET_USER_AUDIT_LOG = "$BASE_ADMIN_USER_ROUTE/{${UserApiPaths.USER_ID}}/audit"
}