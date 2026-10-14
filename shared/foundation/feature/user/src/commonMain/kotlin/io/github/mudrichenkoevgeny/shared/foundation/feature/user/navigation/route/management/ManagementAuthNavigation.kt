package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.email.LoginByEmailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.totp.LoginByTotpParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.login.welcome.LoginWelcomeParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.resetpassword.ResetPasswordParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.methodselection.UnlockMethodSelectionParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.otpinput.UnlockOtpInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.success.UnlockSuccessParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.auth.unlock.targetinput.UnlockTargetInputParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes
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
     * Welcome/entry authentication screen for management.
     *
     * **Screen Parameters:** [LoginWelcomeParams]
     * **Primary API:** None.
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class Welcome(
        override val params: LoginWelcomeParams = LoginWelcomeParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<LoginWelcomeParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for management welcome authentication screen. */
            const val ROUTE = "$BASE_LOGIN_ROUTE/welcome"

            /** Parent navigation route (root authentication screen). */
            const val PARENT_ROUTE = ManagementMainNavigation.Main.ROUTE
        }
    }

    /**
     * Management login by email and password screen.
     *
     * **Screen Parameters:** [LoginByEmailParams]
     * **Primary API:** [SelfManagementLoginRoutes.LOGIN_BY_EMAIL]
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class LoginByEmail(
        override val params: LoginByEmailParams = LoginByEmailParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<LoginByEmailParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for management login by email screen. */
            const val ROUTE = "$BASE_LOGIN_ROUTE/email"

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = Welcome.ROUTE
        }
    }

    /**
     * Management login TOTP verification screen.
     *
     * **Screen Parameters:** [LoginByTotpParams]
     * **Primary API:** [SelfManagementLoginRoutes.LOGIN_BY_TOTP]
     * **Secondary Action APIs:**
     * - [SelfManagementLoginRoutes.LOGIN_BY_TOTP_RECOVERY_CODE]
     */
    @Serializable
    data class LoginByTotp(
        override val params: LoginByTotpParams = LoginByTotpParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<LoginByTotpParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for management TOTP verification screen. */
            const val ROUTE = "$BASE_LOGIN_ROUTE/totp"

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = Welcome.ROUTE
        }
    }

    /**
     * Management password reset screen.
     *
     * **Screen Parameters:** [ResetPasswordParams]
     * **Primary API:** [SelfManagementResetPasswordRoutes.RESET_PASSWORD]
     * **Secondary Action APIs:**
     * - [SelfManagementResetPasswordRoutes.SEND_RESET_PASSWORD_CONFIRMATION]
     */
    @Serializable
    data class ResetPassword(
        override val params: ResetPasswordParams = ResetPasswordParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<ResetPasswordParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for management password reset screen. */
            const val ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/reset-password"

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = LoginByEmail.ROUTE
        }
    }

    /**
     * Management unlock method selection and target input screen.
     *
     * **Screen Parameters:** [UnlockMethodSelectionParams]
     * **Primary API:** None.
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class UnlockMethodSelection(
        override val params: UnlockMethodSelectionParams = UnlockMethodSelectionParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<UnlockMethodSelectionParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for management unlock method selection screen. */
            const val ROUTE = BASE_UNLOCK_ROUTE

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = Welcome.ROUTE
        }
    }

    /**
     * Management unlock target email address or phone number input screen.
     *
     * **Screen Parameters:** [UnlockTargetInputParams]
     * **Primary API:**
     * - [SelfManagementUnlockRoutes.SEND_UNLOCK_EMAIL_CONFIRMATION]
     * - [SelfManagementUnlockRoutes.SEND_UNLOCK_PHONE_CONFIRMATION]
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class UnlockTargetInput(
        override val params: UnlockTargetInputParams = UnlockTargetInputParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<UnlockTargetInputParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for management unlock target input screen. */
            const val ROUTE = "$BASE_UNLOCK_ROUTE/input"

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = UnlockMethodSelection.ROUTE
        }
    }

    /**
     * Unified management account unlock OTP verification screen.
     *
     * **Screen Parameters:** [UnlockOtpInputParams]
     * **Primary API:**
     * - [SelfManagementUnlockRoutes.UNLOCK_BY_EMAIL]
     * - [SelfManagementUnlockRoutes.UNLOCK_BY_PHONE]
     * **Secondary Action APIs:**
     * - [SelfManagementUnlockRoutes.SEND_UNLOCK_EMAIL_CONFIRMATION]
     * - [SelfManagementUnlockRoutes.SEND_UNLOCK_PHONE_CONFIRMATION]
     */
    @Serializable
    data class UnlockOtpInput(
        override val params: UnlockOtpInputParams = UnlockOtpInputParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<UnlockOtpInputParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for management unlock OTP verification screen. */
            const val ROUTE = "$BASE_UNLOCK_ROUTE/otp"

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = UnlockTargetInput.ROUTE
        }
    }

    /**
     * Management unlock success screen.
     *
     * **Screen Parameters:** [UnlockSuccessParams]
     * **Primary API:** None.
     * **Secondary Action APIs:** None.
     */
    @Serializable
    data class UnlockSuccess(
        override val params: UnlockSuccessParams = UnlockSuccessParams()
    ) : ManagementAuthNavigation, ParameterizedNavigationRoute<UnlockSuccessParams> {
        override val route: String get() = ROUTE
        override val parentRoute: String get() = PARENT_ROUTE

        companion object {
            /** Route path for management unlock success screen. */
            const val ROUTE = "$BASE_UNLOCK_ROUTE/success"

            /** Parent navigation route for fallback navigation. */
            const val PARENT_ROUTE = Welcome.ROUTE
        }
    }

    companion object {
        /** Base route path for management login screens. */
        const val BASE_LOGIN_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/login"

        /** Base route path for management unlock screens. */
        const val BASE_UNLOCK_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/unlock"
    }
}
