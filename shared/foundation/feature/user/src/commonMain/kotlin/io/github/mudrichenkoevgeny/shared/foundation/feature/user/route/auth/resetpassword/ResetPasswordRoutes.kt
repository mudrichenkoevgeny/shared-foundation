package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.AuthRoutes

object ResetPasswordRoutes {
    const val RESET_PASSWORD = "${AuthRoutes.BASE_AUTH_ROUTE}/reset-password"
    const val SEND_RESET_PASSWORD_CONFIRMATION = "$RESET_PASSWORD/send-confirmation"
}