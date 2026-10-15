package io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import kotlinx.serialization.Serializable

/**
 * Root navigation route for the staff and management portal.
 */
@Serializable
sealed interface ManagementMainNavigation : NavigationRoute {

    /**
     * Entry main dashboard/hub screen for management.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data object Main : ManagementMainNavigation {

        override val route: String
            get() = "/"

        override val parentRoute: String?
            get() = null
    }
}
