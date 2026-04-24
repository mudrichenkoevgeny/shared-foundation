package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Shared base path segments for login endpoints.
 */
object BaseOpenLoginRoutes {
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
     * Finalize the login process by providing a TOTP code.
     * Used as a second factor regardless of the initial login method.
     */
    const val LOGIN_BY_TOTP = "$BASE_LOGIN_ROUTE/totp"

    /**
     * Finalize the login process by providing a backup recovery code.
     * Used as a fallback second factor when the TOTP device is unavailable.
     */
    const val LOGIN_BY_TOTP_RECOVERY_CODE = "$BASE_LOGIN_ROUTE/totp-recovery-code"

    /**
     * Request a login verification code sent to the phone.
     */
    const val SEND_LOGIN_CONFIRMATION_TO_PHONE = "$BASE_LOGIN_CONFIRMATION_ROUTE/send-to-phone"
}
