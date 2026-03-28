package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base path segments for password recovery endpoints.
 */
object BaseResetPasswordRoutes {
    /**
     * Complete password reset after verification.
     */
    const val RESET_PASSWORD = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/reset-password"

    /**
     * Request a password reset verification code.
     */
    const val SEND_RESET_PASSWORD_CONFIRMATION = "$RESET_PASSWORD/send-confirmation"
}
