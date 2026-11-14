package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.contract

/**
 * User-specific naming conventions for navigation route path and query parameters.
 */
public object UserNavigationPaths {
    /**
     * Query parameter key for triggering contextual modal dialogs or sub-flows over a screen.
     */
    public const val ACTION: String = "action"

    /**
     * Query parameter key specifying the return destination URL after authentication.
     */
    public const val REDIRECT_URL: String = "redirect_url"

    /**
     * Query parameter key for the active TOTP step on 2FA screens.
     */
    public const val TOTP_STEP: String = "totp_step"

    /**
     * Query parameter key for the selected account unlock method.
     */
    public const val UNLOCK_METHOD: String = "unlock_method"

    /**
     * Query parameter key for the verification input mode on TOTP authentication screens.
     */
    public const val LOGIN_BY_TOTP_MODE: String = "login_by_totp_mode"
}
