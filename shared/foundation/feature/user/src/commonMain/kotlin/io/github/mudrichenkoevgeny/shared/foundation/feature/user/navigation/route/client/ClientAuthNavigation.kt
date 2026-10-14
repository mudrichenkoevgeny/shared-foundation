package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.client.ClientMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email.LoginByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.phone.LoginByPhoneParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.welcome.LoginWelcomeParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword.ResetPasswordParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection.UnlockMethodSelectionParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput.UnlockOtpInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success.UnlockSuccessParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput.UnlockTargetInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.register.email.ClientRegistrationByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.register.OpenRegisterRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword.OpenResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.unlock.OpenUnlockRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.session.OpenSessionRoutes
import kotlinx.serialization.Serializable

/**
 * Navigation screen routes for client authentication and registration flows.
 */
@Serializable
sealed interface ClientAuthNavigation : NavigationRoute {

    /**
     * Welcome/entry authentication screen for clients.
     *
     * **Screen Parameters:** [LoginWelcomeParams]
     *
     * **Primary API:** None.
     *
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class Welcome(
        override val params: LoginWelcomeParams = LoginWelcomeParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<LoginWelcomeParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for welcome authentication screen. */
            const val ROUTE = "${OpenLoginRoutes.BASE_LOGIN_ROUTE}/welcome"

            /** Parent navigation route (root authentication screen). */
            const val PARENT_ROUTE = ClientMainNavigation.Main.ROUTE
        }
    }

    /**
     * Login by email and password screen.
     *
     * **Screen Parameters:** [LoginByEmailParams]
     *
     * **Primary API:** [OpenLoginRoutes.LOGIN_BY_EMAIL]
     *
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class LoginByEmail(
        override val params: LoginByEmailParams = LoginByEmailParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<LoginByEmailParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for login by email screen. */
            const val ROUTE = OpenLoginRoutes.LOGIN_BY_EMAIL

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = Welcome.ROUTE
        }
    }

    /**
     * Login by phone number and OTP screen.
     *
     * **Screen Parameters:** [LoginByPhoneParams]
     *
     * **Primary API:** [OpenLoginRoutes.LOGIN_BY_PHONE]
     *
     * **Secondary Action APIs:**
     * - [OpenLoginRoutes.SEND_LOGIN_CONFIRMATION_TO_PHONE]
     */
    @Serializable
    data class LoginByPhone(
        override val params: LoginByPhoneParams = LoginByPhoneParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<LoginByPhoneParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for login by phone screen. */
            const val ROUTE = OpenLoginRoutes.LOGIN_BY_PHONE

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = Welcome.ROUTE
        }
    }

    /**
     * Login TOTP verification screen.
     *
     * **Screen Parameters:** [LoginByTotpParams]
     *
     * **Primary API:** [OpenLoginRoutes.LOGIN_BY_TOTP]
     *
     * **Secondary Action APIs:**
     * - [OpenLoginRoutes.LOGIN_BY_TOTP_RECOVERY_CODE]
     */
    @Serializable
    data class LoginByTotp(
        override val params: LoginByTotpParams = LoginByTotpParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<LoginByTotpParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for TOTP verification screen. */
            const val ROUTE = OpenLoginRoutes.LOGIN_BY_TOTP

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = Welcome.ROUTE
        }
    }

    /**
     * User registration screen.
     *
     * **Screen Parameters:** [ClientRegistrationByEmailParams]
     *
     * **Primary API:** [OpenRegisterRoutes.REGISTER_BY_EMAIL]
     *
     * **Secondary Action APIs:**
     * - [OpenRegisterRoutes.SEND_REGISTER_CONFIRMATION_TO_EMAIL]
     */
    @Serializable
    data class Registration(
        override val params: ClientRegistrationByEmailParams = ClientRegistrationByEmailParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<ClientRegistrationByEmailParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for user registration screen. */
            const val ROUTE = OpenRegisterRoutes.REGISTER_BY_EMAIL

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = Welcome.ROUTE
        }
    }

    /**
     * Password reset request screen.
     *
     * **Screen Parameters:** [ResetPasswordParams]
     *
     * **Primary API:** [OpenResetPasswordRoutes.RESET_EMAIL_PASSWORD]
     *
     * **Secondary Action APIs:**
     * - [OpenResetPasswordRoutes.SEND_RESET_EMAIL_PASSWORD_CONFIRMATION]
     */
    @Serializable
    data class ResetPassword(
        override val params: ResetPasswordParams = ResetPasswordParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<ResetPasswordParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for password reset screen. */
            const val ROUTE = OpenResetPasswordRoutes.RESET_EMAIL_PASSWORD

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = LoginByEmail.ROUTE
        }
    }

    /**
     * Account pending deletion warning and cancellation screen.
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** None.
     *
     * **Secondary Action APIs:**
     * - [OpenUserRoutes.RESTORE_USER]
     * - [OpenSessionRoutes.LOGOUT]
     */
    @Serializable
    data object PendingDeletion : ClientAuthNavigation {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        /** Route path for account pending deletion screen. */
        const val ROUTE = "${OpenLoginRoutes.BASE_LOGIN_ROUTE}/pending-deletion"

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = Welcome.ROUTE
    }

    /**
     * Unlock method selection and target input screen.
     *
     * **Screen Parameters:** [UnlockMethodSelectionParams]
     *
     * **Primary API:** None.
     *
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class UnlockMethodSelection(
        override val params: UnlockMethodSelectionParams = UnlockMethodSelectionParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<UnlockMethodSelectionParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for unlock method selection screen. */
            const val ROUTE = BASE_UNLOCK_ROUTE

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = Welcome.ROUTE
        }
    }

    /**
     * Unlock target email address or phone number input screen.
     *
     * **Screen Parameters:** [UnlockTargetInputParams]
     *
     * **Primary API:**
     * - [OpenUnlockRoutes.SEND_UNLOCK_EMAIL_CONFIRMATION]
     * - [OpenUnlockRoutes.SEND_UNLOCK_PHONE_CONFIRMATION]
     *
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class UnlockTargetInput(
        override val params: UnlockTargetInputParams = UnlockTargetInputParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<UnlockTargetInputParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for unlock target input screen. */
            const val ROUTE = "$BASE_UNLOCK_ROUTE/input"

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = UnlockMethodSelection.ROUTE
        }
    }

    /**
     * Unified account unlock OTP verification screen.
     *
     * **Screen Parameters:** [UnlockOtpInputParams]
     *
     * **Primary API:**
     * - [OpenUnlockRoutes.UNLOCK_BY_EMAIL]
     * - [OpenUnlockRoutes.UNLOCK_BY_PHONE]
     *
     * **Secondary Action APIs:**
     * - [OpenUnlockRoutes.SEND_UNLOCK_EMAIL_CONFIRMATION]
     * - [OpenUnlockRoutes.SEND_UNLOCK_PHONE_CONFIRMATION]
     */
    @Serializable
    data class UnlockOtpInput(
        override val params: UnlockOtpInputParams = UnlockOtpInputParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<UnlockOtpInputParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for unlock OTP verification screen. */
            const val ROUTE = "$BASE_UNLOCK_ROUTE/otp"

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = UnlockTargetInput.ROUTE
        }
    }

    /**
     * Unlock success screen.
     *
     * **Screen Parameters:** [UnlockSuccessParams]
     *
     * **Primary API:** None.
     *
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class UnlockSuccess(
        override val params: UnlockSuccessParams = UnlockSuccessParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<UnlockSuccessParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for unlock success screen. */
            const val ROUTE = "$BASE_UNLOCK_ROUTE/success"

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = Welcome.ROUTE
        }
    }

    companion object {
        /** Base route path for login navigation. */
        const val BASE_LOGIN_ROUTE = "${OpenLoginRoutes.BASE_LOGIN_ROUTE}/login"

        /** Base route path for unlock navigation. */
        const val BASE_UNLOCK_ROUTE = OpenUnlockRoutes.BASE_OPEN_UNLOCK_ROUTE
    }
}
