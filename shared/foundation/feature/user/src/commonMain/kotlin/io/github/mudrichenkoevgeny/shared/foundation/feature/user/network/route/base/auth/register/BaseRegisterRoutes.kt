package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.register

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base path segments for registration endpoints.
 */
object BaseRegisterRoutes {
    /**
     * Base path for registration operations, relative to [BaseAuthRoutes.BASE_AUTH_ROUTE].
     */
    const val BASE_REGISTER_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/register"

    /**
     * Register a new account using email, password, and confirmation code.
     */
    const val REGISTER_BY_EMAIL = "$BASE_REGISTER_ROUTE/email"

    /**
     * Base path for email registration verification steps.
     */
    const val BASE_REGISTER_CONFIRMATION_ROUTE = "${REGISTER_BY_EMAIL}/confirmation"

    /**
     * Request a registration verification code sent to the email.
     */
    const val SEND_REGISTER_CONFIRMATION_TO_EMAIL = "$BASE_REGISTER_CONFIRMATION_ROUTE/send-to-email"
}
