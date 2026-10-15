package io.github.mudrichenkoevgeny.shared.foundation.core.security.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.CommonNavigationRoutes
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
     * - **Screen Parameters:** None.
     * - **Primary API:** [ManagementSecuritySettingsRoutes.GET_MANAGEMENT_SECURITY_SETTINGS]
     * - **Secondary Action APIs:**
     *   - [ManagementSecuritySettingsRoutes.UPDATE_MANAGEMENT_SECURITY_SETTINGS]
     *   - [ManagementSecuritySettingsRoutes.RESET_MANAGEMENT_SECURITY_SETTINGS]
     */
    @Serializable
    data object SecuritySettings : ManagementSecurityNavigation {

        override val route: String
            get() = "${CommonNavigationRoutes.BASE_SETTINGS_ROUTE}/security"

        override val parentRoute: String
            get() = CommonNavigationRoutes.BASE_SETTINGS_ROUTE
    }
}
