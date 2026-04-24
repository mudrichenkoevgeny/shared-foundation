package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user

/**
 * Shared base path segments for the authenticated user's account in the open API.
 */
object BaseOpenUserRoutes {
    /**
     * Base path for self-service user operations.
     */
    const val BASE_USER_ROUTE = "/user"

    /**
     * Path for retrieving the current user's profile ([BASE_USER_ROUTE]).
     */
    const val GET_USER = BASE_USER_ROUTE

    /**
     * Path for scheduling account deletion.
     */
    const val SCHEDULE_DELETION = "$BASE_USER_ROUTE/schedule-deletion"

    /**
     * Path template for restoring an account in pending-deletion state.
     */
    const val RESTORE_USER = "$BASE_USER_ROUTE/restore"
}
