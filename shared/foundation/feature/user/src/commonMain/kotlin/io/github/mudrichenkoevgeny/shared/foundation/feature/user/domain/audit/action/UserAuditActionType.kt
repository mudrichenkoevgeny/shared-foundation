package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier.ManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session.ManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.ManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.register.OpenRegisterRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword.OpenResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.identifier.OpenIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session.OpenSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes

/**
 * User-feature audit actions (management API and open/self-service flows worth auditing).
 */
enum class UserAuditActionType : AuditActionType {
    /** [ManagementAuthSettingsRoutes.UPDATE_AUTH_SETTINGS]. */
    MANAGEMENT_UPDATE_AUTH_SETTINGS,

    /** [ManagementIdentifierRoutes.DELETE_IDENTIFIER]. */
    MANAGEMENT_DELETE_IDENTIFIER,

    /** [ManagementSessionRoutes.DELETE_USER_SESSION]. */
    MANAGEMENT_DELETE_USER_SESSION,

    /** [ManagementSessionRoutes.DELETE_ALL_USER_SESSIONS]. */
    MANAGEMENT_DELETE_ALL_USER_SESSIONS,

    /** [ManagementUserRoutes.UPDATE_USER_ACCOUNT_STATUS]. */
    MANAGEMENT_UPDATE_USER_ACCOUNT_STATUS,

    /** [ManagementUserRoutes.UPDATE_USER_PERMISSIONS]. */
    MANAGEMENT_UPDATE_USER_PERMISSIONS,

    /** [ManagementUserRoutes.DELETE_USER]. */
    MANAGEMENT_DELETE_USER,

    /** [ManagementUserRoutes.CREATE_USER]. */
    MANAGEMENT_CREATE_USER,

    /** [OpenLoginRoutes.LOGIN_BY_EMAIL]. */
    LOGIN_BY_EMAIL,

    /** [OpenLoginRoutes.LOGIN_BY_PHONE]. */
    LOGIN_BY_PHONE,

    /** [OpenLoginRoutes.LOGIN_BY_EXTERNAL_AUTH_PROVIDER]. */
    LOGIN_BY_EXTERNAL_AUTH_PROVIDER,

    /** [OpenRegisterRoutes.REGISTER_BY_EMAIL]. */
    REGISTER_BY_EMAIL,

    /** [OpenResetPasswordRoutes.RESET_EMAIL_PASSWORD]. */
    RESET_PASSWORD,

    /** [OpenSessionRoutes.DELETE_SESSION]. */
    SELF_REVOKE_SESSION,

    /** [OpenSessionRoutes.DELETE_ALL_OTHER_SESSIONS]. */
    SELF_REVOKE_OTHER_SESSIONS,

    /** [OpenIdentifierRoutes.DELETE_IDENTIFIER]. */
    SELF_DELETE_IDENTIFIER,

    /** [OpenIdentifierRoutes.ADD_IDENTIFIER_EMAIL]. */
    ADD_IDENTIFIER_EMAIL,

    /** [OpenIdentifierRoutes.ADD_IDENTIFIER_PHONE]. */
    ADD_IDENTIFIER_PHONE,

    /** [OpenIdentifierRoutes.ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER]. */
    ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER,

    /** [OpenIdentifierRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD]. */
    CHANGE_PASSWORD,

    /** [OpenUserRoutes.DELETE_USER]. */
    SELF_DELETE_USER,

    /** [OpenUserRoutes.RESTORE_USER]. */
    SELF_RESTORE_USER;

    /**
     * String representation of the [UserAuditActionType].
     */
    override val serialName: String
        get() = when (this) {
            MANAGEMENT_UPDATE_AUTH_SETTINGS -> ACTION_MANAGEMENT_UPDATE_AUTH_SETTINGS
            MANAGEMENT_DELETE_IDENTIFIER -> ACTION_MANAGEMENT_DELETE_IDENTIFIER
            MANAGEMENT_DELETE_USER_SESSION -> ACTION_MANAGEMENT_DELETE_USER_SESSION
            MANAGEMENT_DELETE_ALL_USER_SESSIONS -> ACTION_MANAGEMENT_DELETE_ALL_USER_SESSIONS
            MANAGEMENT_UPDATE_USER_ACCOUNT_STATUS -> ACTION_MANAGEMENT_UPDATE_USER_ACCOUNT_STATUS
            MANAGEMENT_UPDATE_USER_PERMISSIONS -> ACTION_MANAGEMENT_UPDATE_USER_PERMISSIONS
            MANAGEMENT_DELETE_USER -> ACTION_MANAGEMENT_DELETE_USER
            MANAGEMENT_CREATE_USER -> ACTION_MANAGEMENT_CREATE_USER
            LOGIN_BY_EMAIL -> ACTION_LOGIN_BY_EMAIL
            LOGIN_BY_PHONE -> ACTION_LOGIN_BY_PHONE
            LOGIN_BY_EXTERNAL_AUTH_PROVIDER -> ACTION_LOGIN_BY_EXTERNAL_AUTH_PROVIDER
            REGISTER_BY_EMAIL -> ACTION_REGISTER_BY_EMAIL
            RESET_PASSWORD -> ACTION_RESET_PASSWORD
            SELF_REVOKE_SESSION -> ACTION_SELF_REVOKE_SESSION
            SELF_REVOKE_OTHER_SESSIONS -> ACTION_SELF_REVOKE_OTHER_SESSIONS
            SELF_DELETE_IDENTIFIER -> ACTION_SELF_DELETE_IDENTIFIER
            ADD_IDENTIFIER_EMAIL -> ACTION_ADD_IDENTIFIER_EMAIL
            ADD_IDENTIFIER_PHONE -> ACTION_ADD_IDENTIFIER_PHONE
            ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER -> ACTION_ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER
            CHANGE_PASSWORD -> ACTION_CHANGE_PASSWORD
            SELF_DELETE_USER -> ACTION_SELF_DELETE_USER
            SELF_RESTORE_USER -> ACTION_SELF_RESTORE_USER
        }

    override fun parseOrNull(value: String): AuditActionType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditActionType = fromValueOrThrow(value)

    companion object {
        const val ACTION_MANAGEMENT_UPDATE_AUTH_SETTINGS = "management_update_auth_settings"
        const val ACTION_MANAGEMENT_DELETE_IDENTIFIER = "management_delete_identifier"
        const val ACTION_MANAGEMENT_DELETE_USER_SESSION = "management_delete_user_session"
        const val ACTION_MANAGEMENT_DELETE_ALL_USER_SESSIONS = "management_delete_all_user_sessions"
        const val ACTION_MANAGEMENT_UPDATE_USER_ACCOUNT_STATUS = "management_update_user_account_status"
        const val ACTION_MANAGEMENT_UPDATE_USER_PERMISSIONS = "management_update_user_permissions"
        const val ACTION_MANAGEMENT_DELETE_USER = "management_delete_user"
        const val ACTION_MANAGEMENT_CREATE_USER = "management_create_user"
        const val ACTION_LOGIN_BY_EMAIL = "login_by_email"
        const val ACTION_LOGIN_BY_PHONE = "login_by_phone"
        const val ACTION_LOGIN_BY_EXTERNAL_AUTH_PROVIDER = "login_by_external_auth_provider"
        const val ACTION_REGISTER_BY_EMAIL = "register_by_email"
        const val ACTION_RESET_PASSWORD = "reset_password"
        const val ACTION_SELF_REVOKE_SESSION = "self_revoke_session"
        const val ACTION_SELF_REVOKE_OTHER_SESSIONS = "self_revoke_other_sessions"
        const val ACTION_SELF_DELETE_IDENTIFIER = "self_delete_identifier"
        const val ACTION_ADD_IDENTIFIER_EMAIL = "add_identifier_email"
        const val ACTION_ADD_IDENTIFIER_PHONE = "add_identifier_phone"
        const val ACTION_ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER = "add_identifier_external_auth_provider"
        const val ACTION_CHANGE_PASSWORD = "change_password"
        const val ACTION_SELF_DELETE_USER = "self_delete_user"
        const val ACTION_SELF_RESTORE_USER = "self_restore_user"

        /**
         * Returns [UserAuditActionType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): UserAuditActionType? =
            runCatching { valueOf(value.uppercase()) }.getOrNull()

        /**
         * Returns [UserAuditActionType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [UserAuditActionType].
         */
        fun fromValueOrThrow(value: String): UserAuditActionType = valueOf(value.uppercase())
    }
}
