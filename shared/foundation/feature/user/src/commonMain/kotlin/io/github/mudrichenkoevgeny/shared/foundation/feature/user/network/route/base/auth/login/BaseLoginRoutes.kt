package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base path segments for login endpoints.
 */
object BaseLoginRoutes {
    /**
     * Base path for login operations, relative to [BaseAuthRoutes.BASE_AUTH_ROUTE].
     */
    const val BASE_LOGIN_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/login"

    /**
     * Login by email and password.
     */
    const val LOGIN_BY_EMAIL = "$BASE_LOGIN_ROUTE/email"

    /**
     * Login by phone number and confirmation code.
     */
    const val LOGIN_BY_PHONE = "$BASE_LOGIN_ROUTE/phone"

    /**
     * Login via a third-party auth provider.
     */
    const val LOGIN_BY_EXTERNAL_AUTH_PROVIDER = "$BASE_LOGIN_ROUTE/external-auth-provider"

    /**
     * Base path for phone login verification steps.
     */
    const val BASE_LOGIN_CONFIRMATION_ROUTE = "${LOGIN_BY_PHONE}/confirmation"

    /**
     * Request a login verification code sent to the phone.
     */
    const val SEND_LOGIN_CONFIRMATION_TO_PHONE = "$BASE_LOGIN_CONFIRMATION_ROUTE/send-to-phone"
}
