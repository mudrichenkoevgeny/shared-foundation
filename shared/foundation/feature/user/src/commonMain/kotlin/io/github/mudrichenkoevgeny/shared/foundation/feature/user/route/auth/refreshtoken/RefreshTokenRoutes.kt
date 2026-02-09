package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.AuthRoutes

/**
 * Object containing route definitions for token management.
 *
 * @property REFRESH The endpoint used to rotate or renew an expired access token using a valid refresh token.
 */
object RefreshTokenRoutes {
    const val REFRESH = "${AuthRoutes.BASE_AUTH_ROUTE}/refresh"
}