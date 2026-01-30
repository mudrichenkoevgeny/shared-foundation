package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.register

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.AuthRoutes

/**
 * Object containing constant route definitions for user registration operations.
 *
 * @property BASE_REGISTER_ROUTE The base path for all account registration endpoints.
 * @property REGISTER_BY_EMAIL The endpoint to create a new user account using an email and password.
 * @property BASE_REGISTER_CONFIRMATION_ROUTE The base path for email registration verification steps.
 * @property SEND_REGISTER_CONFIRMATION_TO_EMAIL The endpoint to request a verification code sent via email for registration.
 */
object RegisterRoutes {
    const val BASE_REGISTER_ROUTE = "${AuthRoutes.BASE_AUTH_ROUTE}/register"
    const val REGISTER_BY_EMAIL = "$BASE_REGISTER_ROUTE/email"
    const val BASE_REGISTER_CONFIRMATION_ROUTE = "${REGISTER_BY_EMAIL}/confirmation"
    const val SEND_REGISTER_CONFIRMATION_TO_EMAIL = "$BASE_REGISTER_CONFIRMATION_ROUTE/send-to-email"
}