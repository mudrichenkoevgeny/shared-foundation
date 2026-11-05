package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.detail.IdentifierDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.list.SelfIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.detail.SessionDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.list.SelfSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.totp.main.TotpMainParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.SelfManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.identifier.SelfManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.security.SelfManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes
import kotlinx.serialization.Serializable

/**
 * Navigation screen routes for manager account self-service management.
 */
@Serializable
public sealed interface ManagementSelfUserNavigation : NavigationRoute {
    /**
     * Main staff user dashboard screen.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** [SelfManagementUserRoutes.GET_USER]
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    public data object Main : ManagementSelfUserNavigation {
        override val route: String
            get() = BASE_USER_ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route
    }

    /**
     * Navigation screen for editing the staff user's own account details.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** [SelfManagementUserRoutes.UPDATE_USER]
     */
    @Serializable
    public data object UserEdit : ManagementSelfUserNavigation {
        override val route: String
            get() = "$BASE_USER_ROUTE/edit"

        override val parentRoute: String
            get() = Main.route
    }

    /**
     * Navigation screen for displaying a paged list of [UserIdentifierSummary].
     *
     * - **Screen Parameters:** [SelfIdentifierListParams]
     * - **Primary API:** [SelfManagementIdentifierRoutes.GET_IDENTIFIERS]
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    public data class IdentifierList(
        override val params: SelfIdentifierListParams = SelfIdentifierListParams()
    ) : ManagementSelfUserNavigation,
        ParameterizedNavigationRoute<SelfIdentifierListParams> {
        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.route

        public companion object {
            public const val ROUTE: String = "$BASE_USER_ROUTE/identifiers"
        }
    }

    /**
     * Navigation screen for displaying specific [UserIdentifierPrivate] details.
     *
     * - **Screen Parameters:** [IdentifierDetailParams]
     * - **Primary API:** [SelfManagementIdentifierRoutes.GET_IDENTIFIER]
     * - **Secondary Action APIs:**
     *   - [SelfManagementIdentifierRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD]
     */
    @Serializable
    public data class IdentifierDetail(
        override val params: IdentifierDetailParams
    ) : ManagementSelfUserNavigation,
        ParameterizedNavigationRoute<IdentifierDetailParams> {
        override val route: String
            get() = getPath(params.identifierId)

        override val parentRoute: String
            get() = IdentifierList.ROUTE

        public companion object {
            public const val ROUTE: String = "${IdentifierList.ROUTE}/{${UserApiPaths.USER_IDENTIFIER_ID}}"

            public fun getPath(identifierId: UserIdentifierId): String =
                ROUTE.replace("{${UserApiPaths.USER_IDENTIFIER_ID}}", identifierId.asHexDashString())

            public fun getPath(identifierId: String): String = ROUTE.replace("{${UserApiPaths.USER_IDENTIFIER_ID}}", identifierId)
        }
    }

    /**
     * Navigation screen for displaying a paged list of [UserSessionSummary].
     *
     * - **Screen Parameters:** [SelfSessionListParams]
     * - **Primary API:** [SelfManagementSessionRoutes.GET_SESSIONS]
     * - **Secondary Action APIs:**
     *   - [SelfManagementSessionRoutes.DELETE_SESSION]
     *   - [SelfManagementSessionRoutes.DELETE_ALL_OTHER_SESSIONS]
     *   - [SelfManagementSessionRoutes.LOGOUT]
     */
    @Serializable
    public data class SessionList(
        override val params: SelfSessionListParams = SelfSessionListParams()
    ) : ManagementSelfUserNavigation,
        ParameterizedNavigationRoute<SelfSessionListParams> {
        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.route

        public companion object {
            public const val ROUTE: String = "$BASE_USER_ROUTE/sessions"
        }
    }

    /**
     * Navigation screen for displaying specific [UserSessionPrivate] details.
     *
     * - **Screen Parameters:** [SessionDetailParams]
     * - **Primary API:** [SelfManagementSessionRoutes.GET_SESSION]
     * - **Secondary Action APIs:**
     *   - [SelfManagementSessionRoutes.DELETE_SESSION]
     */
    @Serializable
    public data class SessionDetail(
        override val params: SessionDetailParams
    ) : ManagementSelfUserNavigation,
        ParameterizedNavigationRoute<SessionDetailParams> {
        override val route: String
            get() = getPath(params.sessionId)

        override val parentRoute: String
            get() = SessionList.ROUTE

        public companion object {
            public const val ROUTE: String = "${SessionList.ROUTE}/{${UserApiPaths.SESSION_ID}}"

            public fun getPath(sessionId: UserSessionId): String =
                ROUTE.replace("{${UserApiPaths.SESSION_ID}}", sessionId.asHexDashString())

            public fun getPath(sessionId: String): String = ROUTE.replace("{${UserApiPaths.SESSION_ID}}", sessionId)
        }
    }

    /**
     * Navigation screen for setting up and configuring TOTP 2FA.
     *
     * - **Screen Parameters:** [TotpMainParams]
     * - **Primary API:** [SelfManagementUserSecurityRoutes.SETUP_TOTP]
     * - **Secondary Action APIs:**
     *   - [SelfManagementUserSecurityRoutes.ENABLE_TOTP]
     *   - [SelfManagementUserSecurityRoutes.DISABLE_TOTP]
     */
    @Serializable
    public data class TotpMain(
        override val params: TotpMainParams = TotpMainParams()
    ) : ManagementSelfUserNavigation,
        ParameterizedNavigationRoute<TotpMainParams> {
        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.route

        public companion object {
            public const val ROUTE: String = BASE_TOTP_ROUTE
        }
    }

    /**
     * Navigation screen for viewing and regenerating TOTP recovery codes.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** [SelfManagementUserSecurityRoutes.GET_RECOVERY_CODES]
     * - **Secondary Action APIs:**
     *   - [SelfManagementUserSecurityRoutes.REGENERATE_RECOVERY_CODES]
     */
    @Serializable
    public data object TotpRecoveryCodes : ManagementSelfUserNavigation {
        override val route: String
            get() = "$BASE_TOTP_ROUTE/recovery-codes"

        override val parentRoute: String
            get() = TotpMain.ROUTE
    }

    public companion object {
        public const val BASE_USER_ROUTE: String = "/user"
        public const val BASE_TOTP_ROUTE: String = "$BASE_USER_ROUTE/totp"
    }
}
