package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.session

object SessionRoutes {
    const val BASE_SESSION_ROUTE = "/session"
    const val LOGOUT_ROUTE = "$BASE_SESSION_ROUTE/logout"
    const val GET_SESSIONS = BASE_SESSION_ROUTE
    const val DELETE_SESSION = "$BASE_SESSION_ROUTE/{id}"
    const val DELETE_ALL_OTHER_SESSIONS = "$BASE_SESSION_ROUTE/delete-others"
}