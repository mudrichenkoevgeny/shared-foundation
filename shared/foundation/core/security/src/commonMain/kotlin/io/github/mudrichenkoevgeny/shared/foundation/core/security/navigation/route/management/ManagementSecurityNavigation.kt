package io.github.mudrichenkoevgeny.shared.foundation.core.security.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.ManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.management.security.settings.ManagementSecuritySettingsRoutes
import kotlinx.serialization.Serializable

/**
 * Navigation screen routes for security settings in management operations.
 */
@Serializable
sealed interface ManagementSecurityNavigation : NavigationRoute {

    /**
     * Navigation screen for viewing and editing [ManagementSecuritySettings].
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** [ManagementSecuritySettingsRoutes.GET_MANAGEMENT_SECURITY_SETTINGS]
     *
     * **Secondary Action APIs:**
     * - [ManagementSecuritySettingsRoutes.UPDATE_MANAGEMENT_SECURITY_SETTINGS]
     * - [ManagementSecuritySettingsRoutes.RESET_MANAGEMENT_SECURITY_SETTINGS]
     */
    @Serializable
    data object EditSecuritySettings : ManagementSecurityNavigation {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        /** Route path for editing security settings. */
        const val ROUTE = "/settings/security"

        /** Parent navigation route (main management hub). */
        const val PARENT_ROUTE = ManagementMainNavigation.Main.ROUTE
    }
}
