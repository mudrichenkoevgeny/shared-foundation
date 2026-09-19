package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.base.globalsettings

/**
 * Shared base routes for global settings endpoints.
 */
object BaseGlobalSettingsRoutes {
    /**
     * Base path for global settings operations.
     */
    const val BASE_GLOBAL_SETTINGS_ROUTE = "/global-settings"

    /**
     * Path template for resetting global settings to default values.
     */
    const val RESET_GLOBAL_SETTINGS_ROUTE = "$BASE_GLOBAL_SETTINGS_ROUTE/reset"
}
