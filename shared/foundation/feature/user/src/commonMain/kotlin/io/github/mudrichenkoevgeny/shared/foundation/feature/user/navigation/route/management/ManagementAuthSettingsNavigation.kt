package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.CommonNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.BaseUserNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes
import kotlinx.serialization.Serializable

/**
 * Navigation screen routes for authentication policies and user settings in management operations.
 */
@Serializable
sealed interface ManagementAuthSettingsNavigation : NavigationRoute {

    /**
     * Navigation screen for viewing and editing [ManagementAuthSettings].
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** [ManagementAuthSettingsRoutes.GET_MANAGEMENT_AUTH_SETTINGS]
     * - **Secondary Action APIs:**
     *   - [ManagementAuthSettingsRoutes.UPDATE_MANAGEMENT_AUTH_SETTINGS]
     *   - [ManagementAuthSettingsRoutes.RESET_MANAGEMENT_AUTH_SETTINGS]
     */
    @Serializable
    data object AuthSettings : ManagementAuthSettingsNavigation {

        override val route: String
            get() = "${CommonNavigationRoutes.BASE_SETTINGS_NAVIGATION_ROUTE}${BaseUserNavigationRoutes.BASE_USER_AUTH_NAVIGATION_ROUTE}"

        override val parentRoute: String
            get() = CommonNavigationRoutes.BASE_SETTINGS_NAVIGATION_ROUTE
    }
}
