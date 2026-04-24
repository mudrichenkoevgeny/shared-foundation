package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base path segments for password recovery endpoints in the management API.
 */
object BaseManagementResetPasswordRoutes {
    /**
     * Base path for management password reset operations.
     */
    const val BASE_MANAGEMENT_RESET_PASSWORD_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseAuthRoutes.BASE_AUTH_ROUTE}/reset-password"

    /**
     * Complete password reset for a management account.
     */
    const val RESET_PASSWORD = BASE_MANAGEMENT_RESET_PASSWORD_ROUTE

    /**
     * Request a password reset verification code for a management account.
     */
    const val SEND_RESET_PASSWORD_CONFIRMATION = "$BASE_MANAGEMENT_RESET_PASSWORD_ROUTE/send-confirmation"
}