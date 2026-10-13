package io.github.mudrichenkoevgeny.shared.foundation.core.security.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.model.securitysettings.ManagementSecuritySettings
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.management.security.settings.ManagementSecuritySettingsRoutes

/**
 * Navigation screen routes for security settings in management operations.
 */
object ManagementSecurityNavigation {

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
    object EditSecuritySettings {
        /** Route path for editing security settings. */
        const val ROUTE = "/settings/security"

        /** Parent navigation route (main management hub). */
        const val PARENT_ROUTE = ManagementMainNavigation.Main.ROUTE
    }
}