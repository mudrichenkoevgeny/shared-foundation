package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.client.ClientMainNavigation
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.identifier.OpenIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.security.OpenUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.session.OpenSessionRoutes
import kotlinx.serialization.Serializable

/**
 * Navigation screen routes for client account self-service management.
 */
@Serializable
public sealed interface ClientUserNavigation : NavigationRoute {
    /**
     * Main user dashboard screen.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** [OpenUserRoutes.GET_USER]
     * - **Secondary Action APIs:**
     *   - [OpenUserRoutes.SCHEDULE_DELETION]
     */
    @Serializable
    public data object Main : ClientUserNavigation {
        override val route: String
            get() = BASE_USER_ROUTE

        override val parentRoute: String
            get() = ClientMainNavigation.Main.route
    }

    /**
     * Navigation screen for editing the current user's details.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** [OpenUserRoutes.UPDATE_USER]
     */
    @Serializable
    public data object UserEdit : ClientUserNavigation {
        override val route: String
            get() = "$BASE_USER_ROUTE/edit"

        override val parentRoute: String
            get() = Main.route
    }

    /**
     * Navigation screen for displaying a paged list of [UserIdentifierSummary].
     *
     * - **Screen Parameters:** [SelfIdentifierListParams]
     * - **Primary API:** [OpenIdentifierRoutes.GET_IDENTIFIERS]
     * - **Secondary Action APIs:**
     *   - [OpenIdentifierRoutes.ADD_IDENTIFIER_EMAIL]
     *   - [OpenIdentifierRoutes.ADD_IDENTIFIER_PHONE]
     *   - [OpenIdentifierRoutes.ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER]
     *   - [OpenIdentifierRoutes.SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION]
     *   - [OpenIdentifierRoutes.SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION]
     *   - [OpenIdentifierRoutes.DELETE_IDENTIFIER]
     */
    @Serializable
    public data class IdentifierList(
        override val params: SelfIdentifierListParams = SelfIdentifierListParams()
    ) : ClientUserNavigation,
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
     * - **Primary API:** [OpenIdentifierRoutes.GET_IDENTIFIER]
     * - **Secondary Action APIs:**
     *   - [OpenIdentifierRoutes.DELETE_IDENTIFIER]
     *   - [OpenIdentifierRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD]
     */
    @Serializable
    public data class IdentifierDetail(
        override val params: IdentifierDetailParams
    ) : ClientUserNavigation,
        ParameterizedNavigationRoute<IdentifierDetailParams> {
        override val route: String
            get() = getPath(params.identifierId)

        override val parentRoute: String
            get() = IdentifierList.ROUTE

        public companion object {
            public const val ROUTE: String = "${IdentifierList.ROUTE}/{${UserApiPaths.USER_IDENTIFIER_ID}}"

            public fun getPath(identifierId: UserIdentifierId): String =
                ROUTE.replace("{${UserApiPaths.USER_IDENTIFIER_ID}}", identifierId.asHexDashString())

            public fun getPath(identifierId: String): String =
                ROUTE.replace("{${UserApiPaths.USER_IDENTIFIER_ID}}", identifierId)
        }
    }

    /**
     * Navigation screen for displaying a paged list of [UserSessionSummary].
     *
     * - **Screen Parameters:** [SelfSessionListParams]
     * - **Primary API:** [OpenSessionRoutes.GET_SESSIONS]
     * - **Secondary Action APIs:**
     *   - [OpenSessionRoutes.DELETE_SESSION]
     *   - [OpenSessionRoutes.DELETE_ALL_OTHER_SESSIONS]
     *   - [OpenSessionRoutes.LOGOUT]
     */
    @Serializable
    public data class SessionList(
        override val params: SelfSessionListParams = SelfSessionListParams()
    ) : ClientUserNavigation,
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
     * - **Primary API:** [OpenSessionRoutes.GET_SESSION]
     * - **Secondary Action APIs:**
     *   - [OpenSessionRoutes.DELETE_SESSION]
     */
    @Serializable
    public data class SessionDetail(
        override val params: SessionDetailParams
    ) : ClientUserNavigation,
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
     * - **Primary API:** [OpenUserSecurityRoutes.SETUP_TOTP]
     * - **Secondary Action APIs:**
     *   - [OpenUserSecurityRoutes.ENABLE_TOTP]
     *   - [OpenUserSecurityRoutes.DISABLE_TOTP]
     */
    @Serializable
    public data class TotpMain(
        override val params: TotpMainParams = TotpMainParams()
    ) : ClientUserNavigation,
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
     * - **Primary API:** [OpenUserSecurityRoutes.GET_RECOVERY_CODES]
     * - **Secondary Action APIs:**
     *   - [OpenUserSecurityRoutes.REGENERATE_RECOVERY_CODES]
     */
    @Serializable
    public data object TotpRecoveryCodes : ClientUserNavigation {
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
