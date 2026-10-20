package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.RedirectNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.client.ClientMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.UserNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email.LoginByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.login.phone.LoginByPhoneParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword.ResetPasswordParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection.UnlockMethodSelectionParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput.UnlockOtpInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success.UnlockSuccessParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput.UnlockTargetInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.client.auth.registration.email.ClientRegistrationByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.registration.OpenRegistrationRoutes
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
     * Virtual entry redirect route forwarding navigation from the auth root to the auth login.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data object Auth : ClientAuthNavigation, RedirectNavigationRoute {

        override val route: String
            get() = BASE_AUTH_ROUTE

        override val targetRoute: String
            get() = Main.ROUTE

        override val parentRoute: String
            get() = ClientMainNavigation.Main.route
    }

    /**
     * Entry authentication screen for clients.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:**
     *   - [OpenLoginRoutes.LOGIN_BY_EXTERNAL_AUTH_PROVIDER]
     */
    @Serializable
    data object Main : ClientAuthNavigation {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = ClientMainNavigation.Main.route

        const val ROUTE = BASE_LOGIN_ROUTE
    }

    /**
     * Login by email and password screen.
     *
     * - **Screen Parameters:** [LoginByEmailParams]
     * - **Primary API:** [OpenLoginRoutes.LOGIN_BY_EMAIL]
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data class LoginByEmail(
        override val params: LoginByEmailParams = LoginByEmailParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<LoginByEmailParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = "$BASE_LOGIN_ROUTE/email"
        }
    }

    /**
     * Login by phone number and OTP screen.
     *
     * - **Screen Parameters:** [LoginByPhoneParams]
     * - **Primary API:** [OpenLoginRoutes.LOGIN_BY_PHONE]
     * - **Secondary Action APIs:**
     *   - [OpenLoginRoutes.SEND_LOGIN_CONFIRMATION_TO_PHONE]
     */
    @Serializable
    data class LoginByPhone(
        override val params: LoginByPhoneParams = LoginByPhoneParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<LoginByPhoneParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = "$BASE_LOGIN_ROUTE/phone"
        }
    }

    /**
     * Login TOTP verification screen.
     *
     * - **Screen Parameters:** [LoginByTotpParams]
     * - **Primary API:** [OpenLoginRoutes.LOGIN_BY_TOTP]
     * - **Secondary Action APIs:**
     *   - [OpenLoginRoutes.LOGIN_BY_TOTP_RECOVERY_CODE]
     */
    @Serializable
    data class LoginByTotp(
        override val params: LoginByTotpParams = LoginByTotpParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<LoginByTotpParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = "$BASE_LOGIN_ROUTE/totp"
        }
    }

    /**
     * User registration screen.
     *
     * - **Screen Parameters:** [ClientRegistrationByEmailParams]
     * - **Primary API:** [OpenRegistrationRoutes.REGISTRATION_BY_EMAIL]
     * - **Secondary Action APIs:**
     *   - [OpenRegistrationRoutes.SEND_REGISTRATION_CONFIRMATION_TO_EMAIL]
     */
    @Serializable
    data class Registration(
        override val params: ClientRegistrationByEmailParams = ClientRegistrationByEmailParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<ClientRegistrationByEmailParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = "$BASE_AUTH_ROUTE/registration"
        }
    }

    /**
     * Password reset request screen.
     *
     * - **Screen Parameters:** [ResetPasswordParams]
     * - **Primary API:** [OpenResetPasswordRoutes.RESET_EMAIL_PASSWORD]
     * - **Secondary Action APIs:**
     *   - [OpenResetPasswordRoutes.SEND_RESET_EMAIL_PASSWORD_CONFIRMATION]
     */
    @Serializable
    data class ResetPassword(
        override val params: ResetPasswordParams = ResetPasswordParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<ResetPasswordParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = "$BASE_AUTH_ROUTE/reset-password"
        }
    }

    /**
     * Account pending deletion warning and cancellation screen.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:**
     *   - [OpenUserRoutes.RESTORE_USER]
     *   - [OpenSessionRoutes.LOGOUT]
     */
    @Serializable
    data object PendingDeletion : ClientAuthNavigation {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        const val ROUTE = "$BASE_LOGIN_ROUTE/pending-deletion"
    }

    /**
     * Unlock method selection and target input screen.
     *
     * - **Screen Parameters:** [UnlockMethodSelectionParams]
     * - **Primary API:** None.
     * - **Secondary Action APIs:**
     *   - [OpenUnlockRoutes.UNLOCK_BY_EXTERNAL_PROVIDER]
     */
    @Serializable
    data class UnlockMethodSelection(
        override val params: UnlockMethodSelectionParams = UnlockMethodSelectionParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<UnlockMethodSelectionParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = BASE_UNLOCK_ROUTE
        }
    }

    /**
     * Unlock target email address or phone number input screen.
     *
     * - **Screen Parameters:** [UnlockTargetInputParams]
     * - **Primary API:**
     *   - [OpenUnlockRoutes.SEND_UNLOCK_EMAIL_CONFIRMATION]
     *   - [OpenUnlockRoutes.SEND_UNLOCK_PHONE_CONFIRMATION]
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data class UnlockTargetInput(
        override val params: UnlockTargetInputParams = UnlockTargetInputParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<UnlockTargetInputParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = UnlockMethodSelection.ROUTE

        companion object {
            const val ROUTE = "$BASE_UNLOCK_ROUTE/input"
        }
    }

    /**
     * Unified account unlock OTP verification screen.
     *
     * - **Screen Parameters:** [UnlockOtpInputParams]
     * - **Primary API:**
     *   - [OpenUnlockRoutes.UNLOCK_BY_EMAIL]
     *   - [OpenUnlockRoutes.UNLOCK_BY_PHONE]
     * - **Secondary Action APIs:**
     *   - [OpenUnlockRoutes.SEND_UNLOCK_EMAIL_CONFIRMATION]
     *   - [OpenUnlockRoutes.SEND_UNLOCK_PHONE_CONFIRMATION]
     */
    @Serializable
    data class UnlockOtpInput(
        override val params: UnlockOtpInputParams = UnlockOtpInputParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<UnlockOtpInputParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = UnlockTargetInput.ROUTE

        companion object {
            const val ROUTE = "$BASE_UNLOCK_ROUTE/otp"
        }
    }

    /**
     * Unlock success screen.
     *
     * - **Screen Parameters:** [UnlockSuccessParams]
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data class UnlockSuccess(
        override val params: UnlockSuccessParams = UnlockSuccessParams()
    ) : ClientAuthNavigation, ParameterizedNavigationRoute<UnlockSuccessParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = "$BASE_UNLOCK_ROUTE/success"
        }
    }

    companion object {
        const val BASE_AUTH_ROUTE = UserNavigationRoutes.BASE_AUTH_ROUTE
        const val BASE_LOGIN_ROUTE = "$BASE_AUTH_ROUTE/login"
        const val BASE_UNLOCK_ROUTE = "$BASE_AUTH_ROUTE/unlock"
    }
}
