package io.github.mudrichenkoevgeny.shared.foundation.core.settings.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.model.globalsettings.ManagementGlobalSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.management.globalsettings.ManagementGlobalSettingsRoutes

/**
 * Navigation screen routes for global settings in management operations.
 */
object ManagementSettingsNavigation {

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
    object EditGlobalSettings {
        /** Route path for editing global platform settings. */
        const val ROUTE = "/settings/global"

        /** Parent navigation route (main management hub). */
        const val PARENT_ROUTE = ManagementMainNavigation.Main.ROUTE
    }
}