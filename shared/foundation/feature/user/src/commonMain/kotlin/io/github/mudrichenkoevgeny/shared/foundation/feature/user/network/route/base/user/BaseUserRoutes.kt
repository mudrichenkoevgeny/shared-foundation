package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths

/**
 * Shared base path segments for the authenticated user's account in the open API.
 */
object BaseUserRoutes {
    /**
     * Base path for self-service user operations.
     */
    const val BASE_USER_ROUTE = "/user"

    /**
     * Path for retrieving the current user's profile ([BASE_USER_ROUTE]).
     */
    const val GET_USER = BASE_USER_ROUTE

    /**
     * Path template for deleting a user account; includes the [UserApiPaths.USER_ID] path parameter.
     */
    const val DELETE_USER = "$BASE_USER_ROUTE/{${UserApiPaths.USER_ID}}"

    /**
     * Path template for restoring an account in pending-deletion state; includes the [UserApiPaths.USER_ID] path parameter.
     */
    const val RESTORE_USER = "$BASE_USER_ROUTE/{${UserApiPaths.USER_ID}}/restore"
}
