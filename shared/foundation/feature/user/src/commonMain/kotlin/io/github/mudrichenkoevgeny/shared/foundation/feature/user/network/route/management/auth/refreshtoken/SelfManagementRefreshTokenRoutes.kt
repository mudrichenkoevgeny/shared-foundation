package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole.ADMIN
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole.STAFF
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.RefreshTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.token.SessionTokenPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.refreshtoken.BaseSelfManagementRefreshTokenRoutes

/**
 * Route paths for refresh-token operations in the management authentication API.
 */
object SelfManagementRefreshTokenRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Exchanges a management refresh token for a new management session.
     *
     * Request body: [RefreshTokenPayload].
     *
     * Response body: [SessionTokenPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **Note:** The server strictly validates that the provided token belongs to an account
     * with management-level roles ([UserRole.STAFF] or [UserRole.ADMIN]).
     */
    const val REFRESH_TOKEN = BaseSelfManagementRefreshTokenRoutes.BASE_MANAGEMENT_REFRESH_TOKEN_ROUTE
}