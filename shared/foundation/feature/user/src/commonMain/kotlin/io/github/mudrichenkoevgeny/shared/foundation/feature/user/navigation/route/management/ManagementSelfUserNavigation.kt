package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.management.ManagementMainNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.identifier.UserIdentifierSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionPrivate
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.session.UserSessionSummary
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.identifier.detail.IdentifierDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.identifier.list.SelfIdentifierListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.session.detail.SessionDetailParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.session.list.SelfSessionListParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.screen.base.profile.totp.main.TotpMainParams
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.SelfManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.identifier.SelfManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.security.SelfManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes

/**
 * Navigation screen routes for staff/employee self-service profile and session management.
 */
object ManagementSelfUserNavigation {

    private const val BASE_SELF_USER_ROUTE = "/self/user"
    private const val BASE_SELF_IDENTIFIER_ROUTE = "$BASE_SELF_USER_ROUTE/identifier"
    private const val BASE_SELF_SESSION_ROUTE = "$BASE_SELF_USER_ROUTE/session"
    private const val BASE_SELF_SECURITY_ROUTE = "$BASE_SELF_USER_ROUTE/security"
    private const val BASE_SELF_TOTP_ROUTE = "$BASE_SELF_SECURITY_ROUTE/totp"

    /**
     * Main staff profile dashboard screen.
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** [SelfManagementUserRoutes.GET_USER]
     *
     * **Secondary Action APIs:** None.
     */
    object Main {
        /** Route path for main profile screen. */
        const val ROUTE = BASE_SELF_USER_ROUTE

        /** Parent navigation route. */
        const val PARENT_ROUTE: String = ManagementMainNavigation.Main.ROUTE
    }

    /**
     * Navigation screen for displaying a paged list of [UserIdentifierSummary].
     *
     * **Screen Parameters:** [SelfIdentifierListParams]
     *
     * **Primary API:** [SelfManagementIdentifierRoutes.GET_IDENTIFIERS]
     *
     * **Secondary Action APIs:** None.
     */
    object IdentifierList {
        /** Route path for staff personal identifiers list screen. */
        const val ROUTE = BASE_SELF_IDENTIFIER_ROUTE

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = Main.ROUTE
    }

    /**
     * Navigation screen for displaying specific [UserIdentifierPrivate] details.
     *
     * **Screen Parameters:** [IdentifierDetailParams]
     *
     * **Primary API:** [SelfManagementIdentifierRoutes.GET_IDENTIFIER]
     *
     * **Secondary Action APIs:**
     * - [SelfManagementIdentifierRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD]
     */
    object IdentifierDetail {
        /** Route template for staff single identifier detail screen. */
        const val ROUTE = "$BASE_SELF_IDENTIFIER_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = IdentifierList.ROUTE

        /**
         * Builds a concrete route path for the specified [identifierId].
         *
         * @param identifierId Target identifier ID.
         * @return Formatted URL path replacing parameter placeholder with [identifierId].
         */
        fun getPath(identifierId: UserIdentifierId): String =
            ROUTE.replace("{${UserApiPaths.USER_IDENTIFIER_ID}}", identifierId.asHexDashString())

        /**
         * Builds a concrete route path for the specified string [identifierId].
         *
         * @param identifierId String representation of the target identifier ID.
         * @return Formatted URL path replacing parameter placeholder with [identifierId].
         */
        fun getPath(identifierId: String): String =
            ROUTE.replace("{${UserApiPaths.USER_IDENTIFIER_ID}}", identifierId)
    }

    /**
     * Navigation screen for displaying a paged list of [UserSessionSummary].
     *
     * **Screen Parameters:** [SelfSessionListParams]
     *
     * **Primary API:** [SelfManagementSessionRoutes.GET_SESSIONS]
     *
     * **Secondary Action APIs:**
     * - [SelfManagementSessionRoutes.DELETE_SESSION]
     * - [SelfManagementSessionRoutes.DELETE_ALL_OTHER_SESSIONS]
     * - [SelfManagementSessionRoutes.LOGOUT]
     */
    object SessionList {
        /** Route path for staff personal sessions list screen. */
        const val ROUTE = BASE_SELF_SESSION_ROUTE

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = Main.ROUTE
    }

    /**
     * Navigation screen for displaying specific [UserSessionPrivate] details.
     *
     * **Screen Parameters:** [SessionDetailParams]
     *
     * **Primary API:** [SelfManagementSessionRoutes.GET_SESSION]
     *
     * **Secondary Action APIs:**
     * - [SelfManagementSessionRoutes.DELETE_SESSION]
     */
    object SessionDetail {
        /** Route template for staff single session detail screen. */
        const val ROUTE = "$BASE_SELF_SESSION_ROUTE/{${UserApiPaths.SESSION_ID}}"

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = SessionList.ROUTE

        /**
         * Builds a concrete route path for the specified [sessionId].
         *
         * @param sessionId Target session ID.
         * @return Formatted URL path replacing parameter placeholder with [sessionId].
         */
        fun getPath(sessionId: UserSessionId): String =
            ROUTE.replace("{${UserApiPaths.SESSION_ID}}", sessionId.asHexDashString())

        /**
         * Builds a concrete route path for the specified string [sessionId].
         *
         * @param sessionId String representation of the target session ID.
         * @return Formatted URL path replacing parameter placeholder with [sessionId].
         */
        fun getPath(sessionId: String): String =
            ROUTE.replace("{${UserApiPaths.SESSION_ID}}", sessionId)
    }

    /**
     * Navigation screen for setting up and configuring TOTP 2FA.
     *
     * **Screen Parameters:** [TotpMainParams]
     *
     * **Primary API:** [SelfManagementUserSecurityRoutes.SETUP_TOTP]
     *
     * **Secondary Action APIs:**
     * - [SelfManagementUserSecurityRoutes.ENABLE_TOTP]
     * - [SelfManagementUserSecurityRoutes.DISABLE_TOTP]
     */
    object TotpMain {
        /** Route path for staff personal TOTP 2FA setup screen. */
        const val ROUTE = BASE_SELF_TOTP_ROUTE

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = Main.ROUTE
    }

    /**
     * Navigation screen for viewing and regenerating TOTP recovery codes.
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** [SelfManagementUserSecurityRoutes.GET_RECOVERY_CODES]
     *
     * **Secondary Action APIs:**
     * - [SelfManagementUserSecurityRoutes.REGENERATE_RECOVERY_CODES]
     */
    object TotpRecoveryCodes {
        /** Route path for viewing staff personal TOTP recovery codes screen. */
        const val ROUTE = "$BASE_SELF_TOTP_ROUTE/recovery-codes"

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = TotpMain.ROUTE
    }
}