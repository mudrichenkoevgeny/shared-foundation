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
sealed interface ClientUserNavigation : NavigationRoute {

    /**
     * Main user dashboard screen.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** [OpenUserRoutes.GET_USER]
     * - **Secondary Action APIs:**
     *   - [OpenUserRoutes.SCHEDULE_DELETION]
     */
    @Serializable
    data object Main : ClientUserNavigation {

        override val route: String
            get() = BASE_USER_ROUTE

        override val parentRoute: String
            get() = ClientMainNavigation.Main.route
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
    data class IdentifierList(
        override val params: SelfIdentifierListParams = SelfIdentifierListParams()
    ) : ClientUserNavigation, ParameterizedNavigationRoute<SelfIdentifierListParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.route

        companion object {
            const val ROUTE = "$BASE_USER_ROUTE/identifiers"
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
    data class IdentifierDetail(
        override val params: IdentifierDetailParams
    ) : ClientUserNavigation, ParameterizedNavigationRoute<IdentifierDetailParams> {

        override val route: String
            get() = getPath(params.identifierId)

        override val parentRoute: String
            get() = IdentifierList.ROUTE

        companion object {
            const val ROUTE = "${IdentifierList.ROUTE}/{${UserApiPaths.USER_IDENTIFIER_ID}}"

            fun getPath(identifierId: UserIdentifierId): String =
                ROUTE.replace("{${UserApiPaths.USER_IDENTIFIER_ID}}", identifierId.asHexDashString())

            fun getPath(identifierId: String): String =
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
    data class SessionList(
        override val params: SelfSessionListParams = SelfSessionListParams()
    ) : ClientUserNavigation, ParameterizedNavigationRoute<SelfSessionListParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.route

        companion object {
            const val ROUTE = "$BASE_USER_ROUTE/sessions"
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
    data class SessionDetail(
        override val params: SessionDetailParams
    ) : ClientUserNavigation, ParameterizedNavigationRoute<SessionDetailParams> {

        override val route: String
            get() = getPath(params.sessionId)

        override val parentRoute: String
            get() = SessionList.ROUTE

        companion object {
            const val ROUTE = "${SessionList.ROUTE}/{${UserApiPaths.SESSION_ID}}"

            fun getPath(sessionId: UserSessionId): String =
                ROUTE.replace("{${UserApiPaths.SESSION_ID}}", sessionId.asHexDashString())

            fun getPath(sessionId: String): String =
                ROUTE.replace("{${UserApiPaths.SESSION_ID}}", sessionId)
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
    data class TotpMain(
        override val params: TotpMainParams = TotpMainParams()
    ) : ClientUserNavigation, ParameterizedNavigationRoute<TotpMainParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = Main.route

        companion object {
            const val ROUTE = BASE_TOTP_ROUTE
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
    data object TotpRecoveryCodes : ClientUserNavigation {

        override val route: String
            get() = "$BASE_TOTP_ROUTE/recovery-codes"

        override val parentRoute: String
            get() = TotpMain.ROUTE
    }

    companion object {
        const val BASE_USER_ROUTE = "/user"
        const val BASE_TOTP_ROUTE = "$BASE_USER_ROUTE/totp"
    }
}
