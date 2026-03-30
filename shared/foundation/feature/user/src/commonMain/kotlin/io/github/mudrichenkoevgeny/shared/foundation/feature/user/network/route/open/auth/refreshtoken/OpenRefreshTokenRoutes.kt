package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.RefreshTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.refreshtoken.BaseRefreshTokenRoutes

/**
 * Route paths for refresh-token operations in the authentication API.
 */
object OpenRefreshTokenRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [RefreshTokenPayload].
     *
     * Response body: [AuthDataPayload].
     */
    const val REFRESH_TOKEN = BaseRefreshTokenRoutes.BASE_REFRESH_TOKEN_ROUTE
}