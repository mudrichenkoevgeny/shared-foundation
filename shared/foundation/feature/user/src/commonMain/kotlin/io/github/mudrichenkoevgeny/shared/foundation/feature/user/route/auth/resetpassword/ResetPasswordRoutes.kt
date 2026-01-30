package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.AuthRoutes

/**
 * Object containing constant route definitions for password recovery operations.
 *
 * @property RESET_PASSWORD The endpoint to set a new password using a verification code.
 * @property SEND_RESET_PASSWORD_CONFIRMATION The endpoint to request a password reset verification code.
 */
object ResetPasswordRoutes {
    const val RESET_PASSWORD = "${AuthRoutes.BASE_AUTH_ROUTE}/reset-password"
    const val SEND_RESET_PASSWORD_CONFIRMATION = "$RESET_PASSWORD/send-confirmation"
}