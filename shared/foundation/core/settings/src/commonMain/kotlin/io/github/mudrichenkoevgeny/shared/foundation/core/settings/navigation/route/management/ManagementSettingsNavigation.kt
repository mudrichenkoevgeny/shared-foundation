package io.github.mudrichenkoevgeny.shared.foundation.core.settings.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
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
     * Navigation screen for viewing and editing [ManagementGlobalSettings].
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** [ManagementGlobalSettingsRoutes.GET_MANAGEMENT_GLOBAL_SETTINGS]
     *
     * **Secondary Action APIs:**
     * - [ManagementGlobalSettingsRoutes.UPDATE_MANAGEMENT_GLOBAL_SETTINGS]
     * - [ManagementGlobalSettingsRoutes.RESET_MANAGEMENT_GLOBAL_SETTINGS]
     */
    @Serializable
    data object EditGlobalSettings : ManagementSettingsNavigation {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        /** Route path for editing global platform settings. */
        const val ROUTE = "/settings/global"

        /** Parent navigation route (main management hub). */
        const val PARENT_ROUTE = ManagementMainNavigation.Main.ROUTE
    }
}
