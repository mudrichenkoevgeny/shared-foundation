package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.response.auth.AuthDataResponse
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.refreshtoken.RefreshTokenRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.refreshtoken.BaseRefreshTokenRoutes

/**
 * Route paths for refresh-token operations in the authentication API.
 */
object OpenRefreshTokenRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [RefreshTokenRequest].
     *
     * Response body: [AuthDataResponse].
     */
    const val REFRESH = BaseRefreshTokenRoutes.BASE_REFRESH_TOKEN_ROUTE
}