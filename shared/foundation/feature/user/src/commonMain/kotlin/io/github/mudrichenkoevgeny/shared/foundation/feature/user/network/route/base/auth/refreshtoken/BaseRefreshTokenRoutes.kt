package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base routes for refresh-token endpoints.
 */
object BaseRefreshTokenRoutes {
    /**
     * Base path for exchanging a refresh token for a new session.
     */
    const val BASE_REFRESH_TOKEN_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/refresh"
}