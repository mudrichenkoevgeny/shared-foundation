package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.RefreshTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.refreshtoken.BaseManagementRefreshTokenRoutes

/**
 * Route paths for refresh-token operations in the management authentication API.
 */
object ManagementRefreshTokenRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Exchanges a management refresh token for a new management session.
     *
     * Request body: [RefreshTokenPayload].
     *
     * Response body: [SessionTokenPayload].
     */
    const val REFRESH_TOKEN = BaseManagementRefreshTokenRoutes.BASE_MANAGEMENT_REFRESH_TOKEN_ROUTE
}