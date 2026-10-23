package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.RedirectNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.BaseUserNavigationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email.LoginByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword.ResetPasswordParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection.UnlockMethodSelectionParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput.UnlockOtpInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success.UnlockSuccessParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput.UnlockTargetInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.login.SelfManagementLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.resetpassword.SelfManagementResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.unlock.SelfManagementUnlockRoutes
import kotlinx.serialization.Serializable

/**
 * Navigation screen routes for staff and management authentication flows.
 */
@Serializable
sealed interface ManagementAuthNavigation : NavigationRoute {

    /**
     * Virtual entry redirect route forwarding navigation from the auth root to the auth login.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data object Auth : ManagementAuthNavigation, RedirectNavigationRoute {

        override val route: String
            get() = BASE_AUTH_ROUTE

        override val targetRoute: String
            get() = Main.ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route
    }

    /**
     * Entry authentication screen for management.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data object Main : ManagementAuthNavigation {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route

        const val ROUTE = BASE_LOGIN_ROUTE
    }

    /**
     * Management login by email and password screen.
     *
     * - **Screen Parameters:** [LoginByEmailParams]
     * - **Primary API:** [SelfManagementLoginRoutes.LOGIN_BY_EMAIL]
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data class LoginByEmail(
        override val params: LoginByEmailParams = LoginByEmailParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<LoginByEmailParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = "$BASE_LOGIN_ROUTE/email"
        }
    }

    /**
     * Management login TOTP verification screen.
     *
     * - **Screen Parameters:** [LoginByTotpParams]
     * - **Primary API:** [SelfManagementLoginRoutes.LOGIN_BY_TOTP]
     * - **Secondary Action APIs:**
     *   - [SelfManagementLoginRoutes.LOGIN_BY_TOTP_RECOVERY_CODE]
     */
    @Serializable
    data class LoginByTotp(
        override val params: LoginByTotpParams = LoginByTotpParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<LoginByTotpParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = "$BASE_LOGIN_ROUTE/totp"
        }
    }

    /**
     * Management password reset screen.
     *
     * - **Screen Parameters:** [ResetPasswordParams]
     * - **Primary API:** [SelfManagementResetPasswordRoutes.RESET_PASSWORD]
     * - **Secondary Action APIs:**
     *   - [SelfManagementResetPasswordRoutes.SEND_RESET_PASSWORD_CONFIRMATION]
     */
    @Serializable
    data class ResetPassword(
        override val params: ResetPasswordParams = ResetPasswordParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<ResetPasswordParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = "$BASE_AUTH_ROUTE/reset-password"
        }
    }

    /**
     * Management unlock method selection and target input screen.
     *
     * - **Screen Parameters:** [UnlockMethodSelectionParams]
     * - **Primary API:** None.
     * - **Secondary Action APIs:**
     *   - [SelfManagementUnlockRoutes.UNLOCK_BY_EXTERNAL_PROVIDER]
     */
    @Serializable
    data class UnlockMethodSelection(
        override val params: UnlockMethodSelectionParams = UnlockMethodSelectionParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<UnlockMethodSelectionParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = BASE_UNLOCK_ROUTE
        }
    }

    /**
     * Management unlock target email address or phone number input screen.
     *
     * - **Screen Parameters:** [UnlockTargetInputParams]
     * - **Primary API:**
     *   - [SelfManagementUnlockRoutes.SEND_UNLOCK_EMAIL_CONFIRMATION]
     *   - [SelfManagementUnlockRoutes.SEND_UNLOCK_PHONE_CONFIRMATION]
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data class UnlockTargetInput(
        override val params: UnlockTargetInputParams = UnlockTargetInputParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<UnlockTargetInputParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = UnlockMethodSelection.ROUTE

        companion object {
            const val ROUTE = "$BASE_UNLOCK_ROUTE/input"
        }
    }

    /**
     * Unified management account unlock OTP verification screen.
     *
     * - **Screen Parameters:** [UnlockOtpInputParams]
     * - **Primary API:**
     *   - [SelfManagementUnlockRoutes.UNLOCK_BY_EMAIL]
     *   - [SelfManagementUnlockRoutes.UNLOCK_BY_PHONE]
     * - **Secondary Action APIs:**
     *   - [SelfManagementUnlockRoutes.SEND_UNLOCK_EMAIL_CONFIRMATION]
     *   - [SelfManagementUnlockRoutes.SEND_UNLOCK_PHONE_CONFIRMATION]
     */
    @Serializable
    data class UnlockOtpInput(
        override val params: UnlockOtpInputParams = UnlockOtpInputParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<UnlockOtpInputParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = UnlockTargetInput.ROUTE

        companion object {
            const val ROUTE = "$BASE_UNLOCK_ROUTE/otp"
        }
    }

    /**
     * Management unlock success screen.
     *
     * - **Screen Parameters:** [UnlockSuccessParams]
     * - **Primary API:** None.
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data class UnlockSuccess(
        override val params: UnlockSuccessParams = UnlockSuccessParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<UnlockSuccessParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.ROUTE

        companion object {
            const val ROUTE = "$BASE_UNLOCK_ROUTE/success"
        }
    }

    companion object {
        const val BASE_AUTH_ROUTE = BaseUserNavigationRoutes.BASE_USER_AUTH_NAVIGATION_ROUTE
        const val BASE_LOGIN_ROUTE = "$BASE_AUTH_ROUTE/login"
        const val BASE_UNLOCK_ROUTE = "$BASE_AUTH_ROUTE/unlock"
    }
}
