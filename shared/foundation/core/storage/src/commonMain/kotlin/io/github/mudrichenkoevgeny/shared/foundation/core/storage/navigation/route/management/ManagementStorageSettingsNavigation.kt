package io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.CommonNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.model.storagesettings.ManagementStorageSettings
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.BaseStorageNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.ManagementStorageSettingsRoutes
import kotlinx.serialization.Serializable

/**
 * Navigation screen routes for storage settings in management operations.
 */
@Serializable
sealed interface ManagementStorageSettingsNavigation : NavigationRoute {

    /**
     * Navigation screen for viewing and editing [ManagementStorageSettings].
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** [ManagementStorageSettingsRoutes.GET_MANAGEMENT_STORAGE_SETTINGS]
     * - **Secondary Action APIs:**
     *   - [ManagementStorageSettingsRoutes.UPDATE_MANAGEMENT_STORAGE_SETTINGS]
     */
    @Serializable
    data object StorageSettings : ManagementStorageSettingsNavigation {

        override val route: String
            get() = "${CommonNavigationRoutes.BASE_SETTINGS_NAVIGATION_ROUTE}${BaseStorageNavigationRoutes.BASE_STORAGE_NAVIGATION_ROUTE}"

        override val parentRoute: String
            get() = CommonNavigationRoutes.BASE_SETTINGS_NAVIGATION_ROUTE
    }
}
