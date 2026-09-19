package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.refreshtoken

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
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
     * Exchanges a valid refresh token for a new set of session tokens.
     *
     * Request body: [RefreshTokenPayload].
     *
     * Response body: [SessionTokenPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     */
    const val REFRESH_TOKEN = BaseOpenRefreshTokenRoutes.BASE_REFRESH_TOKEN_ROUTE
}