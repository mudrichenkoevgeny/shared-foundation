package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.NavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.ParameterizedNavigationRoute
import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.identifier.detail.IdentifierDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.user.session.detail.SessionDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.globallist.ManagementGlobalIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.identifier.userlist.ManagementUserIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.globallist.ManagementGlobalSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.session.userlist.ManagementUserSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.detail.UserDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.management.user.globallist.ManagementUserListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.ManagementUsersRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.identifiers.ManagementIdentifiersRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.security.ManagementUsersSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.sessions.ManagementSessionsRoutes
import kotlinx.serialization.Serializable

/**
 * Navigation screen routes for global management and administrative operations.
 */
@Serializable
sealed interface ManagementUsersNavigation : NavigationRoute {

    /**
     * Global user list management screen.
     *
     * - **Screen Parameters:** [ManagementUserListParams]
     * - **Primary API:** [ManagementUsersRoutes.GET_USERS]
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data class UserList(
        override val params: ManagementUserListParams = ManagementUserListParams()
    ) : ManagementUsersNavigation, ParameterizedNavigationRoute<ManagementUserListParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route

        companion object {
            const val ROUTE = BASE_USERS_ROUTE
        }
    }

    /**
     * User creation screen.
     *
     * - **Screen Parameters:** None.
     * - **Primary API:** [ManagementUsersRoutes.CREATE_USER]
     * - **Secondary Action APIs:** None.
     */
    @Serializable
    data object CreateUser : ManagementUsersNavigation {

        override val route: String
            get() = "$BASE_USERS_ROUTE/create"

        override val parentRoute: String
            get() = UserList.ROUTE
    }

    /**
     * Single user account detail screen.
     *
     * - **Screen Parameters:** [UserDetailParams]
     * - **Primary API:** [ManagementUsersRoutes.GET_USER]
     * - **Secondary Action APIs:**
     *   - [ManagementUsersRoutes.UPDATE_USER]
     *   - [ManagementUsersRoutes.DELETE_USER]
     *   - [ManagementUsersSecurityRoutes.DISABLE_TOTP]
     */
    @Serializable
    data class UserDetail(
        override val params: UserDetailParams
    ) : ManagementUsersNavigation, ParameterizedNavigationRoute<UserDetailParams> {

        override val route: String
            get() = getPath(params.userId)

        override val parentRoute: String
            get() = UserList.ROUTE

        companion object {
            const val ROUTE = "$BASE_USERS_ROUTE/{${UserApiPaths.USER_ID}}"

            fun getPath(userId: UserId): String =
                ROUTE.replace("{${UserApiPaths.USER_ID}}", userId.asHexDashString())

            fun getPath(userId: String): String =
                ROUTE.replace("{${UserApiPaths.USER_ID}}", userId)
        }
    }

    /**
     * Active sessions list screen for a specific target user.
     *
     * - **Screen Parameters:** [ManagementUserSessionListParams]
     * - **Primary API:** [ManagementSessionsRoutes.GET_USER_SESSIONS]
     * - **Secondary Action APIs:**
     *   - [ManagementSessionsRoutes.DELETE_SESSION]
     *   - [ManagementSessionsRoutes.DELETE_ALL_USER_SESSIONS]
     */
    @Serializable
    data class UserSessionList(
        override val params: ManagementUserSessionListParams = ManagementUserSessionListParams()
    ) : ManagementUsersNavigation, ParameterizedNavigationRoute<ManagementUserSessionListParams> {

        override val route: String
            get() = getPath(params.userId)

        override val parentRoute: String
            get() = if (params.userId.isNotBlank()) {
                UserDetail.getPath(params.userId)
            } else {
                UserList.ROUTE
            }

        companion object {
            const val ROUTE = "$BASE_USERS_ROUTE/{${UserApiPaths.USER_ID}}/sessions"

            fun getPath(userId: UserId): String =
                ROUTE.replace("{${UserApiPaths.USER_ID}}", userId.asHexDashString())

            fun getPath(userId: String): String =
                ROUTE.replace("{${UserApiPaths.USER_ID}}", userId)
        }
    }

    /**
     * Credentials/identifiers list screen for a specific target user.
     *
     * - **Screen Parameters:** [ManagementUserIdentifierListParams]
     * - **Primary API:** [ManagementIdentifiersRoutes.GET_USER_IDENTIFIERS]
     * - **Secondary Action APIs:**
     *   - [ManagementIdentifiersRoutes.DELETE_IDENTIFIER]
     *   - [ManagementIdentifiersRoutes.DELETE_IDENTIFIER_PASSWORD]
     */
    @Serializable
    data class UserIdentifierList(
        override val params: ManagementUserIdentifierListParams = ManagementUserIdentifierListParams()
    ) : ManagementUsersNavigation, ParameterizedNavigationRoute<ManagementUserIdentifierListParams> {

        override val route: String
            get() = getPath(params.userId)

        override val parentRoute: String
            get() = if (params.userId.isNotBlank()) {
                UserDetail.getPath(params.userId)
            } else {
                UserList.ROUTE
            }

        companion object {
            const val ROUTE = "$BASE_USERS_ROUTE/{${UserApiPaths.USER_ID}}/identifiers"

            fun getPath(userId: UserId): String =
                ROUTE.replace("{${UserApiPaths.USER_ID}}", userId.asHexDashString())

            fun getPath(userId: String): String =
                ROUTE.replace("{${UserApiPaths.USER_ID}}", userId)
        }
    }

    /**
     * System-wide global active sessions list screen.
     *
     * - **Screen Parameters:** [ManagementGlobalSessionListParams]
     * - **Primary API:** [ManagementSessionsRoutes.GET_SESSIONS]
     * - **Secondary Action APIs:**
     *   - [ManagementSessionsRoutes.DELETE_SESSION]
     */
    @Serializable
    data class GlobalSessionList(
        override val params: ManagementGlobalSessionListParams = ManagementGlobalSessionListParams()
    ) : ManagementUsersNavigation, ParameterizedNavigationRoute<ManagementGlobalSessionListParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route

        companion object {
            const val ROUTE = BASE_SESSIONS_ROUTE
        }
    }

    /**
     * System-wide global credentials/identifiers list screen.
     *
     * - **Screen Parameters:** [ManagementGlobalIdentifierListParams]
     * - **Primary API:** [ManagementIdentifiersRoutes.GET_IDENTIFIERS]
     * - **Secondary Action APIs:**
     *   - [ManagementIdentifiersRoutes.DELETE_IDENTIFIER]
     *   - [ManagementIdentifiersRoutes.DELETE_IDENTIFIER_PASSWORD]
     */
    @Serializable
    data class GlobalIdentifierList(
        override val params: ManagementGlobalIdentifierListParams = ManagementGlobalIdentifierListParams()
    ) : ManagementUsersNavigation, ParameterizedNavigationRoute<ManagementGlobalIdentifierListParams> {

        override val route: String
            get() = ROUTE

        override val parentRoute: String
            get() = ManagementMainNavigation.Main.route

        companion object {
            const val ROUTE = BASE_IDENTIFIERS_ROUTE
        }
    }

    /**
     * Navigation screen for displaying specific [UserIdentifierPrivate] details.
     *
     * - **Screen Parameters:** [IdentifierDetailParams]
     * - **Primary API:** [ManagementIdentifiersRoutes.GET_IDENTIFIER]
     * - **Secondary Action APIs:**
     *   - [ManagementIdentifiersRoutes.DELETE_IDENTIFIER]
     *   - [ManagementIdentifiersRoutes.DELETE_IDENTIFIER_PASSWORD]
     */
    @Serializable
    data class IdentifierDetail(
        override val params: IdentifierDetailParams
    ) : ManagementUsersNavigation, ParameterizedNavigationRoute<IdentifierDetailParams> {

        override val route: String
            get() = getPath(params.identifierId)

        override val parentRoute: String
            get() = if (!params.userId.isNullOrBlank()) {
                UserIdentifierList.getPath(params.userId)
            } else {
                GlobalIdentifierList.ROUTE
            }

        companion object {
            const val ROUTE = "$BASE_IDENTIFIERS_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

            fun getPath(identifierId: UserIdentifierId): String =
                ROUTE.replace("{${UserApiPaths.USER_IDENTIFIER_ID}}", identifierId.asHexDashString())

            fun getPath(identifierId: String): String =
                ROUTE.replace("{${UserApiPaths.USER_IDENTIFIER_ID}}", identifierId)
        }
    }

    /**
     * Navigation screen for displaying specific [UserSessionPrivate] details.
     *
     * - **Screen Parameters:** [SessionDetailParams]
     * - **Primary API:** [ManagementSessionsRoutes.GET_SESSION]
     * - **Secondary Action APIs:**
     *   - [ManagementSessionsRoutes.DELETE_SESSION]
     */
    @Serializable
    data class SessionDetail(
        override val params: SessionDetailParams
    ) : ManagementUsersNavigation, ParameterizedNavigationRoute<SessionDetailParams> {

        override val route: String
            get() = getPath(params.sessionId)

        override val parentRoute: String
            get() = if (!params.userId.isNullOrBlank()) {
                UserSessionList.getPath(params.userId)
            } else {
                GlobalSessionList.ROUTE
            }

        companion object {
            const val ROUTE = "$BASE_SESSIONS_ROUTE/{${UserApiPaths.SESSION_ID}}"

            fun getPath(sessionId: UserSessionId): String =
                ROUTE.replace("{${UserApiPaths.SESSION_ID}}", sessionId.asHexDashString())

            fun getPath(sessionId: String): String =
                ROUTE.replace("{${UserApiPaths.SESSION_ID}}", sessionId)
        }
    }

    companion object {
        const val BASE_USERS_ROUTE = "/users"
        const val BASE_SESSIONS_ROUTE = "/sessions"
        const val BASE_IDENTIFIERS_ROUTE = "/identifiers"
    }
}
