package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.security

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseOpenUserRoutes

/**
 * Shared base path segments for the authenticated user's security management in the open API.
 */
object BaseOpenUserSecurityRoutes {
    /**
     * Base path for the current user's security settings.
     */
    const val BASE_SECURITY_ROUTE = "${BaseOpenUserRoutes.BASE_USER_ROUTE}/security"

    /**
     * Base path for TOTP-related operations.
     */
    const val BASE_TOTP_ROUTE = "$BASE_SECURITY_ROUTE/totp"

    /**
     * Initiation of TOTP setup.
     */
    const val TOTP_SETUP = "$BASE_TOTP_ROUTE/setup"

    /**
     * Final activation of TOTP after verification of the first code.
     */
    const val TOTP_ENABLE = "$BASE_TOTP_ROUTE/enable"

    /**
     * Disabling TOTP for the account.
     */
    const val TOTP_DISABLE = "$BASE_TOTP_ROUTE/disable"

    /**
     * Base path for recovery codes.
     */
    const val BASE_RECOVERY_CODES_ROUTE = "$BASE_TOTP_ROUTE/recovery-codes"

    /**
     * Retrieval of current recovery codes.
     */
    const val GET_RECOVERY_CODES = BASE_RECOVERY_CODES_ROUTE

    /**
     * Invalidation and regeneration of recovery codes.
     */
    const val REGENERATE_RECOVERY_CODES = "$BASE_RECOVERY_CODES_ROUTE/regenerate"
}