package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes

/**
 * Navigation screen routes for authentication policies and user settings in management operations.
 */
object ManagementAuthSettingsNavigation {

    /**
     * Navigation screen for viewing and editing [ManagementAuthSettings].
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** [ManagementAuthSettingsRoutes.GET_MANAGEMENT_AUTH_SETTINGS]
     *
     * **Secondary Action APIs:**
     * - [ManagementAuthSettingsRoutes.UPDATE_MANAGEMENT_AUTH_SETTINGS]
     * - [ManagementAuthSettingsRoutes.RESET_MANAGEMENT_AUTH_SETTINGS]
     */
    object EditAuthSettings {
        /** Route path for editing authentication settings. */
        const val ROUTE = "/settings/auth"

        /** Parent navigation route (main management hub). */
        const val PARENT_ROUTE = ManagementMainNavigation.Main.ROUTE
    }
}