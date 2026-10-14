package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import kotlinx.serialization.Serializable

/**
 * Root navigation route for the client application.
 */
@Serializable
sealed interface ClientMainNavigation : NavigationRoute {

    /**
     * Entry home/main screen for clients.
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** None.
     *
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data object Main : ClientMainNavigation {
        override val route: String get() = ROUTE
        override val parentRoute: String? get() = PARENT_ROUTE

        /** Route path for the client entry home screen. */
        const val ROUTE = "/"

        /** Parent navigation route (root of the client app). */
        val PARENT_ROUTE: String? = null
    }
}
