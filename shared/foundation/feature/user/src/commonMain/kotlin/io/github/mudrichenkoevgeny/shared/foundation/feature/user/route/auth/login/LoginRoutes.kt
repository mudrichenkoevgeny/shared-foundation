package io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.route.auth.AuthRoutes

/**
 * Object containing constant route definitions for login operations.
 *
 * @property BASE_LOGIN_ROUTE The base path for all login-related endpoints.
 * @property LOGIN_BY_EMAIL The endpoint for authenticating using an email and password.
 * @property LOGIN_BY_PHONE The endpoint for authenticating using a phone number and a confirmation code.
 * @property LOGIN_BY_EXTERNAL_AUTH_PROVIDER The endpoint for authenticating via third-party providers (e.g., Google).
 * @property BASE_LOGIN_CONFIRMATION_ROUTE The base path for phone login verification steps.
 * @property SEND_LOGIN_CONFIRMATION_TO_PHONE The endpoint to request a verification code for login.
 */
object LoginRoutes {
    const val BASE_LOGIN_ROUTE = "${AuthRoutes.BASE_AUTH_ROUTE}/login"
    const val LOGIN_BY_EMAIL = "$BASE_LOGIN_ROUTE/email"
    const val LOGIN_BY_PHONE = "$BASE_LOGIN_ROUTE/phone"
    const val LOGIN_BY_EXTERNAL_AUTH_PROVIDER = "$BASE_LOGIN_ROUTE/external-auth-provider"
    const val BASE_LOGIN_CONFIRMATION_ROUTE = "${LOGIN_BY_PHONE}/confirmation"
    const val SEND_LOGIN_CONFIRMATION_TO_PHONE = "$BASE_LOGIN_CONFIRMATION_ROUTE/send-to-phone"
}