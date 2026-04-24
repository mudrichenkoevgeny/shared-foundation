package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.security

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseManagementUserRoutes

/**
 * Shared base path segments for administrative security management.
 * These routes allow managers to reset security factors for other users.
 */
object BaseManagementUserSecurityRoutes {
    /**
     * Base path for a specific user's security management.
     */
    const val BASE_USER_SECURITY_ROUTE = "${BaseManagementUserRoutes.BASE_MANAGEMENT_USERS_ROUTE}/{${UserApiPaths.USER_ID}}/security"

    /**
     * Base path for TOTP-related operations for a specific user.
     */
    const val BASE_USER_TOTP_ROUTE = "$BASE_USER_SECURITY_ROUTE/totp"

    /**
     * Path for administrative reset (disabling) of a user's TOTP.
     */
    const val RESET_TOTP = "$BASE_USER_TOTP_ROUTE/reset"

    /**
     * Path for administrative invalidation and regeneration of a user's recovery codes.
     */
    const val REGENERATE_RECOVERY_CODES = "$BASE_USER_TOTP_ROUTE/recovery-codes/regenerate"
}