package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base routes for refresh-token endpoints in the management API.
 */
object BaseManagementRefreshTokenRoutes {
    /**
     * Base path for management-specific refresh token operations.
     */
    const val BASE_MANAGEMENT_REFRESH_TOKEN_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseAuthRoutes.BASE_AUTH_ROUTE}/refresh"
}