package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.user.UserDetailsResponse

/**
 * Route paths for the authenticated user's account in the public API.
 */
object UserRoutes {
    /**
     * Base path for user operations.
     */
    const val BASE_USER_ROUTE = "/user"

    /**
     * **HTTP method:** `GET`
     *
     * Response body: [UserDetailsResponse].
     */
    const val GET_USER = BASE_USER_ROUTE

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes the user account identified by the `{userId}` path parameter.
     */
    const val DELETE_USER = "$BASE_USER_ROUTE/{${UserApiPaths.USER_ID}}"

    /**
     * **HTTP method:** `POST`
     *
     * Restores an account in pending-deletion state (cancels scheduled purge).
     */
    const val RESTORE_USER = "$BASE_USER_ROUTE/{${UserApiPaths.USER_ID}}/restore"
}