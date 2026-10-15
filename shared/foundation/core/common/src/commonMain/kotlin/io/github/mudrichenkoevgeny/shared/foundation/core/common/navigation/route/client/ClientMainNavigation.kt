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
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data object Main : ClientMainNavigation {

        override val route: String
            get() = "/"

        override val parentRoute: String?
            get() = null
    }
}
