package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.RefreshTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.refreshtoken.BaseOpenRefreshTokenRoutes

/**
 * Route paths for refresh-token operations in the authentication API.
 */
object OpenRefreshTokenRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [RefreshTokenPayload].
     *
     * Response body: [SessionTokenPayload].
     */
    const val REFRESH_TOKEN = BaseOpenRefreshTokenRoutes.BASE_REFRESH_TOKEN_ROUTE
}