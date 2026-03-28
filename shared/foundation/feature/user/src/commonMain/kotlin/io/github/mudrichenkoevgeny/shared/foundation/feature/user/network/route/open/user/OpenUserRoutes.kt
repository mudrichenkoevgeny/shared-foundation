package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.user.UserDetailsResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseUserRoutes

/**
 * Route paths for the authenticated user's account in the open API.
 */
object OpenUserRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Response body: [UserDetailsResponse].
     */
    const val GET_USER = BaseUserRoutes.GET_USER

    /**
     * **HTTP method:** `DELETE`
     *
     * Deletes the user account.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     */
    const val DELETE_USER = BaseUserRoutes.DELETE_USER

    /**
     * **HTTP method:** `POST`
     *
     * Restores an account in pending-deletion state (cancels scheduled purge).
     *
     * Path parameter: [UserApiPaths.USER_ID].
     */
    const val RESTORE_USER = BaseUserRoutes.RESTORE_USER
}
