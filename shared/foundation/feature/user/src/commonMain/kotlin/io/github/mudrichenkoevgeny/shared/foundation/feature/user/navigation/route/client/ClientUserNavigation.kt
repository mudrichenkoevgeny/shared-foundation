package io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client

import io.github.mudrichenkoevgeny.shared.foundation.core.common.navigation.route.client.ClientMainNavigation
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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.identifier.OpenIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.security.OpenUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.session.OpenSessionRoutes

/**
 * Navigation screen routes for client personal profile and account self-service management.
 */
object ClientUserNavigation {

    private const val BASE_USER_ROUTE = "/user"
    private const val BASE_IDENTIFIER_ROUTE = "$BASE_USER_ROUTE/identifier"
    private const val BASE_SESSION_ROUTE = "$BASE_USER_ROUTE/session"
    private const val BASE_SECURITY_ROUTE = "$BASE_USER_ROUTE/security"
    private const val BASE_TOTP_ROUTE = "$BASE_SECURITY_ROUTE/totp"

    /**
     * Main user profile dashboard screen.
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** [OpenUserRoutes.GET_USER]
     *
     * **Secondary Action APIs:**
     * - [OpenUserRoutes.SCHEDULE_DELETION]
     */
    object Main {
        /** Route path for main user profile screen. */
        const val ROUTE = BASE_USER_ROUTE

        /** Parent navigation route. */
        const val PARENT_ROUTE: String = ClientMainNavigation.Main.ROUTE
    }

    /**
     * Navigation screen for displaying a paged list of [UserIdentifierSummary].
     *
     * **Screen Parameters:** [SelfIdentifierListParams]
     *
     * **Primary API:** [OpenIdentifierRoutes.GET_IDENTIFIERS]
     *
     * **Secondary Action APIs:**
     * - [OpenIdentifierRoutes.ADD_IDENTIFIER_EMAIL]
     * - [OpenIdentifierRoutes.ADD_IDENTIFIER_PHONE]
     * - [OpenIdentifierRoutes.ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER]
     * - [OpenIdentifierRoutes.SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION]
     * - [OpenIdentifierRoutes.SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION]
     * - [OpenIdentifierRoutes.DELETE_IDENTIFIER]
     */
    object IdentifierList {
        /** Route path for personal identifiers list screen. */
        const val ROUTE = BASE_IDENTIFIER_ROUTE

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = Main.ROUTE
    }

    /**
     * Navigation screen for displaying specific [UserIdentifierPrivate] details.
     *
     * **Screen Parameters:** [IdentifierDetailParams]
     *
     * **Primary API:** [OpenIdentifierRoutes.GET_IDENTIFIER]
     *
     * **Secondary Action APIs:**
     * - [OpenIdentifierRoutes.DELETE_IDENTIFIER]
     * - [OpenIdentifierRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD]
     */
    object IdentifierDetail {
        /** Route template for single identifier detail screen. */
        const val ROUTE = "$BASE_IDENTIFIER_ROUTE/{${UserApiPaths.USER_IDENTIFIER_ID}}"

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
     * **Primary API:** [OpenSessionRoutes.GET_SESSIONS]
     *
     * **Secondary Action APIs:**
     * - [OpenSessionRoutes.DELETE_SESSION]
     * - [OpenSessionRoutes.DELETE_ALL_OTHER_SESSIONS]
     * - [OpenSessionRoutes.LOGOUT]
     */
    object SessionList {
        /** Route path for personal sessions list screen. */
        const val ROUTE = BASE_SESSION_ROUTE

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = Main.ROUTE
    }

    /**
     * Navigation screen for displaying specific [UserSessionPrivate] details.
     *
     * **Screen Parameters:** [SessionDetailParams]
     *
     * **Primary API:** [OpenSessionRoutes.GET_SESSION]
     *
     * **Secondary Action APIs:**
     * - [OpenSessionRoutes.DELETE_SESSION]
     */
    object SessionDetail {
        /** Route template for single session detail screen. */
        const val ROUTE = "$BASE_SESSION_ROUTE/{${UserApiPaths.SESSION_ID}}"

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
     * **Primary API:** [OpenUserSecurityRoutes.SETUP_TOTP]
     *
     * **Secondary Action APIs:**
     * - [OpenUserSecurityRoutes.ENABLE_TOTP]
     * - [OpenUserSecurityRoutes.DISABLE_TOTP]
     */
    object TotpMain {
        /** Route path for personal TOTP 2FA setup screen. */
        const val ROUTE = BASE_TOTP_ROUTE

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = Main.ROUTE
    }

    /**
     * Navigation screen for viewing and regenerating TOTP recovery codes.
     *
     * **Screen Parameters:** None.
     *
     * **Primary API:** [OpenUserSecurityRoutes.GET_RECOVERY_CODES]
     *
     * **Secondary Action APIs:**
     * - [OpenUserSecurityRoutes.REGENERATE_RECOVERY_CODES]
     */
    object TotpRecoveryCodes {
        /** Route path for viewing personal TOTP recovery codes screen. */
        const val ROUTE = "$BASE_TOTP_ROUTE/recovery-codes"

        /** Parent navigation route for fallback navigation. */
        const val PARENT_ROUTE = TotpMain.ROUTE
    }
}