package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.security

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.BaseSelfManagementUserRoutes

/**
 * Shared base path segments for self-service security management (TOTP, Recovery Codes).
 */
object BaseSelfManagementUserSecurityRoutes {
    /**
     * Base path for the current authenticated principal's security settings.
     */
    const val BASE_SELF_SECURITY_ROUTE = "${BaseSelfManagementUserRoutes.BASE_SELF_MANAGEMENT_USER_ROUTE}/security"

    /**
     * Base path for TOTP-related operations.
     */
    const val BASE_SELF_TOTP_ROUTE = "$BASE_SELF_SECURITY_ROUTE/totp"

    /**
     * Initiation of TOTP setup.
     */
    const val TOTP_SETUP = "$BASE_SELF_TOTP_ROUTE/setup"

    /**
     * Final activation of TOTP after verification of the first code.
     */
    const val TOTP_ENABLE = "$BASE_SELF_TOTP_ROUTE/enable"

    /**
     * Disabling TOTP for the current account.
     */
    const val TOTP_DISABLE = "$BASE_SELF_TOTP_ROUTE/disable"

    /**
     * Base path for recovery codes.
     */
    const val BASE_SELF_RECOVERY_CODES_ROUTE = "$BASE_SELF_TOTP_ROUTE/recovery-codes"

    /**
     * Retrieval of current recovery codes.
     */
    const val GET_RECOVERY_CODES = BASE_SELF_RECOVERY_CODES_ROUTE

    /**
     * Invalidation and regeneration of recovery codes.
     */
    const val REGENERATE_RECOVERY_CODES = "$BASE_SELF_RECOVERY_CODES_ROUTE/regenerate"
}