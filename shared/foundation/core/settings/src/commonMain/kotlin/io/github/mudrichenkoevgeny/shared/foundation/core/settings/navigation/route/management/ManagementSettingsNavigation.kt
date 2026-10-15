package io.github.mudrichenkoevgeny.shared.foundation.core.settings.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.CommonNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.ManagementGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.management.globalsettings.ManagementGlobalSettingsRoutes
import kotlinx.serialization.Serializable

/**
 * Navigation screen routes for global settings in management operations.
 */
@Serializable
sealed interface ManagementSettingsNavigation : NavigationRoute {

    /**
     * Entry main screen for settings.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data object Main : ManagementSettingsNavigation {

        override val route: String
            get() = CommonNavigationRoutes.BASE_SETTINGS_ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route
    }

    /**
     * Navigation screen for viewing and editing [ManagementGlobalSettings].
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** [ManagementGlobalSettingsRoutes.GET_MANAGEMENT_GLOBAL_SETTINGS]
     * - **Secondary Action APIs:**
     *   - [ManagementGlobalSettingsRoutes.UPDATE_MANAGEMENT_GLOBAL_SETTINGS]
     *   - [ManagementGlobalSettingsRoutes.RESET_MANAGEMENT_GLOBAL_SETTINGS]
     */
    @Serializable
    data object GlobalSettings : ManagementSettingsNavigation {

        override val route: String
            get() = "${CommonNavigationRoutes.BASE_SETTINGS_ROUTE}/global"

        override val parentRoute: String
            get() = Main.route
    }
}
