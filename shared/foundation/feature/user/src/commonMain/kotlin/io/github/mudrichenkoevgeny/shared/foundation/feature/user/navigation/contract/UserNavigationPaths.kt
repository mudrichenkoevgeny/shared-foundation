package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract

/**
 * User-specific naming conventions for navigation route path and query parameters.
 */
object UserNavigationPaths {

    /**
     * Query parameter key for triggering contextual modal dialogs or sub-flows over a screen.
     */
    const val ACTION = "action"

    /**
     * Query parameter key specifying the return destination URL after authentication.
     */
    const val REDIRECT_URL = "redirect_url"

    /**
     * Query parameter key for the active TOTP step on 2FA screens.
     */
    const val TOTP_STEP = "totp_step"

    /**
     * Query parameter key for the selected account unlock method.
     */
    const val UNLOCK_METHOD = "unlock_method"

    /**
     * Query parameter key for the verification input mode on TOTP authentication screens.
     */
    const val LOGIN_BY_TOTP_MODE = "login_by_totp_mode"
}