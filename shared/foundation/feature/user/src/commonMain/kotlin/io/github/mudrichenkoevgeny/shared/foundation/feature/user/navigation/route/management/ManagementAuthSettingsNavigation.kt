package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.auth.settings.ManagementAuthSettings
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
     * **Screen Parameters:** None.
     *
     * **Primary API:** [ManagementAuthSettingsRoutes.GET_MANAGEMENT_AUTH_SETTINGS]
     *
     * **Secondary Action APIs:**
     * - [ManagementAuthSettingsRoutes.UPDATE_MANAGEMENT_AUTH_SETTINGS]
     * - [ManagementAuthSettingsRoutes.RESET_MANAGEMENT_AUTH_SETTINGS]
     */
    @Serializable
    data object EditAuthSettings : ManagementAuthSettingsNavigation {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        /** Route path for editing authentication settings. */
        const val ROUTE = "/settings/auth"

        /** Parent navigation route (main management hub). */
        const val PARENT_ROUTE = ManagementMainNavigation.Main.ROUTE
    }
}
