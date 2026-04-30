package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.configuration

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes

/**
 * Shared base routes for user configuration endpoints.
 */
object BaseManagementUserConfigurationRoutes {
    /**
     * Base path segment for aggregated user-related configuration (auth, security, global settings).
     */
    const val BASE_USER_CONFIGURATION_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}/user-configuration"
}