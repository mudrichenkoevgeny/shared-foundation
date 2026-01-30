package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.user

/**
 * Object containing constant route definitions for user-related API endpoints.
 *
 * @property BASE_USER_ROUTE The base path for all user operations.
 * @property GET_USER The endpoint to retrieve the current user data.
 * @property DELETE_USER The endpoint to delete a specific user account by its unique identifier.
 */
object UserRoutes {
    const val BASE_USER_ROUTE = "/user"
    const val GET_USER = BASE_USER_ROUTE
    const val DELETE_USER = "$BASE_USER_ROUTE/{id}"
}