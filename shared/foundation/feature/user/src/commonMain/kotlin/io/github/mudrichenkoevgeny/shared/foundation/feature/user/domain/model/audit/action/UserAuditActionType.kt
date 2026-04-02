package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.audit.action

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

    /** [ManagementIdentifierRoutes.GET_IDENTIFIERS_MASKED]. */
    MANAGEMENT_GET_IDENTIFIERS_MASKED,

    /** [ManagementIdentifierRoutes.GET_IDENTIFIERS_UNMASKED]. */
    MANAGEMENT_GET_IDENTIFIERS_UNMASKED,

    /** [ManagementIdentifierRoutes.GET_IDENTIFIER_MASKED]. */
    MANAGEMENT_GET_IDENTIFIER_MASKED,

    /** [ManagementIdentifierRoutes.GET_IDENTIFIER_UNMASKED]. */
    MANAGEMENT_GET_IDENTIFIER_UNMASKED,

    /** [ManagementIdentifierRoutes.DELETE_IDENTIFIER]. */
    MANAGEMENT_DELETE_IDENTIFIER,

    /** [ManagementSessionRoutes.GET_USER_SESSIONS_MASKED]. */
    MANAGEMENT_GET_USER_SESSIONS_MASKED,

    /** [ManagementSessionRoutes.GET_USER_SESSIONS_UNMASKED]. */
    MANAGEMENT_GET_USER_SESSIONS_UNMASKED,

    /** [ManagementSessionRoutes.GET_USER_SESSION_MASKED]. */
    MANAGEMENT_GET_USER_SESSION_MASKED,

    /** [ManagementSessionRoutes.GET_USER_SESSION_UNMASKED]. */
    MANAGEMENT_GET_USER_SESSION_UNMASKED,

    /** [ManagementSessionRoutes.DELETE_USER_SESSION]. */
    MANAGEMENT_DELETE_USER_SESSION,

    /** [ManagementSessionRoutes.DELETE_ALL_USER_SESSIONS]. */
    MANAGEMENT_DELETE_ALL_USER_SESSIONS,

    /** [ManagementUserRoutes.GET_USERS]. */
    MANAGEMENT_GET_USERS,

    /** [ManagementUserRoutes.GET_USER]. */
    MANAGEMENT_GET_USER,

    /** [ManagementUserRoutes.UPDATE_USER_ACCOUNT_STATUS]. */
    MANAGEMENT_UPDATE_USER_ACCOUNT_STATUS,

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

    /** [OpenLoginRoutes.SEND_LOGIN_CONFIRMATION_TO_PHONE]. */
    SEND_LOGIN_CONFIRMATION_TO_PHONE,

    /** [OpenRegisterRoutes.REGISTER_BY_EMAIL]. */
    REGISTER_BY_EMAIL,

    /** [OpenRegisterRoutes.SEND_REGISTER_CONFIRMATION_TO_EMAIL]. */
    SEND_REGISTER_CONFIRMATION_TO_EMAIL,

    /** [OpenResetPasswordRoutes.RESET_EMAIL_PASSWORD]. */
    RESET_PASSWORD,

    /** [OpenResetPasswordRoutes.SEND_RESET_EMAIL_PASSWORD_CONFIRMATION]. */
    SEND_RESET_PASSWORD_CONFIRMATION,

    /** [OpenSessionRoutes.LOGOUT]. */
    LOGOUT,

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

    /** [OpenIdentifierRoutes.SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION]. */
    SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION,

    /** [OpenIdentifierRoutes.SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION]. */
    SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION,

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
            MANAGEMENT_GET_IDENTIFIERS_MASKED -> ACTION_MANAGEMENT_GET_IDENTIFIERS_MASKED
            MANAGEMENT_GET_IDENTIFIERS_UNMASKED -> ACTION_MANAGEMENT_GET_IDENTIFIERS_UNMASKED
            MANAGEMENT_GET_IDENTIFIER_MASKED -> ACTION_MANAGEMENT_GET_IDENTIFIER_MASKED
            MANAGEMENT_GET_IDENTIFIER_UNMASKED -> ACTION_MANAGEMENT_GET_IDENTIFIER_UNMASKED
            MANAGEMENT_DELETE_IDENTIFIER -> ACTION_MANAGEMENT_DELETE_IDENTIFIER
            MANAGEMENT_GET_USER_SESSIONS_MASKED -> ACTION_MANAGEMENT_GET_USER_SESSIONS_MASKED
            MANAGEMENT_GET_USER_SESSIONS_UNMASKED -> ACTION_MANAGEMENT_GET_USER_SESSIONS_UNMASKED
            MANAGEMENT_GET_USER_SESSION_MASKED -> ACTION_MANAGEMENT_GET_USER_SESSION_MASKED
            MANAGEMENT_GET_USER_SESSION_UNMASKED -> ACTION_MANAGEMENT_GET_USER_SESSION_UNMASKED
            MANAGEMENT_DELETE_USER_SESSION -> ACTION_MANAGEMENT_DELETE_USER_SESSION
            MANAGEMENT_DELETE_ALL_USER_SESSIONS -> ACTION_MANAGEMENT_DELETE_ALL_USER_SESSIONS
            MANAGEMENT_GET_USERS -> ACTION_MANAGEMENT_GET_USERS
            MANAGEMENT_GET_USER -> ACTION_MANAGEMENT_GET_USER
            MANAGEMENT_UPDATE_USER_ACCOUNT_STATUS -> ACTION_MANAGEMENT_UPDATE_USER_ACCOUNT_STATUS
            MANAGEMENT_DELETE_USER -> ACTION_MANAGEMENT_DELETE_USER
            MANAGEMENT_CREATE_USER -> ACTION_MANAGEMENT_CREATE_USER
            LOGIN_BY_EMAIL -> ACTION_LOGIN_BY_EMAIL
            LOGIN_BY_PHONE -> ACTION_LOGIN_BY_PHONE
            LOGIN_BY_EXTERNAL_AUTH_PROVIDER -> ACTION_LOGIN_BY_EXTERNAL_AUTH_PROVIDER
            SEND_LOGIN_CONFIRMATION_TO_PHONE -> ACTION_SEND_LOGIN_CONFIRMATION_TO_PHONE
            REGISTER_BY_EMAIL -> ACTION_REGISTER_BY_EMAIL
            SEND_REGISTER_CONFIRMATION_TO_EMAIL -> ACTION_SEND_REGISTER_CONFIRMATION_TO_EMAIL
            RESET_PASSWORD -> ACTION_RESET_PASSWORD
            SEND_RESET_PASSWORD_CONFIRMATION -> ACTION_SEND_RESET_PASSWORD_CONFIRMATION
            LOGOUT -> ACTION_LOGOUT
            SELF_REVOKE_SESSION -> ACTION_SELF_REVOKE_SESSION
            SELF_REVOKE_OTHER_SESSIONS -> ACTION_SELF_REVOKE_OTHER_SESSIONS
            SELF_DELETE_IDENTIFIER -> ACTION_SELF_DELETE_IDENTIFIER
            ADD_IDENTIFIER_EMAIL -> ACTION_ADD_IDENTIFIER_EMAIL
            ADD_IDENTIFIER_PHONE -> ACTION_ADD_IDENTIFIER_PHONE
            ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER -> ACTION_ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER
            SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION -> ACTION_SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION
            SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION -> ACTION_SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION
            CHANGE_PASSWORD -> ACTION_CHANGE_PASSWORD
            SELF_DELETE_USER -> ACTION_SELF_DELETE_USER
            SELF_RESTORE_USER -> ACTION_SELF_RESTORE_USER
        }

    override fun parseOrNull(value: String): AuditActionType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditActionType = fromValueOrThrow(value)

    companion object {
        const val ACTION_MANAGEMENT_UPDATE_AUTH_SETTINGS = "management_update_auth_settings"
        const val ACTION_MANAGEMENT_GET_IDENTIFIERS_MASKED = "management_get_identifiers_masked"
        const val ACTION_MANAGEMENT_GET_IDENTIFIERS_UNMASKED = "management_get_identifiers_unmasked"
        const val ACTION_MANAGEMENT_GET_IDENTIFIER_MASKED = "management_get_identifier_masked"
        const val ACTION_MANAGEMENT_GET_IDENTIFIER_UNMASKED = "management_get_identifier_unmasked"
        const val ACTION_MANAGEMENT_DELETE_IDENTIFIER = "management_delete_identifier"
        const val ACTION_MANAGEMENT_GET_USER_SESSIONS_MASKED = "management_get_user_sessions_masked"
        const val ACTION_MANAGEMENT_GET_USER_SESSIONS_UNMASKED = "management_get_user_sessions_unmasked"
        const val ACTION_MANAGEMENT_GET_USER_SESSION_MASKED = "management_get_user_session_masked"
        const val ACTION_MANAGEMENT_GET_USER_SESSION_UNMASKED = "management_get_user_session_unmasked"
        const val ACTION_MANAGEMENT_DELETE_USER_SESSION = "management_delete_user_session"
        const val ACTION_MANAGEMENT_DELETE_ALL_USER_SESSIONS = "management_delete_all_user_sessions"
        const val ACTION_MANAGEMENT_GET_USERS = "management_get_users"
        const val ACTION_MANAGEMENT_GET_USER = "management_get_user"
        const val ACTION_MANAGEMENT_UPDATE_USER_ACCOUNT_STATUS = "management_update_user_account_status"
        const val ACTION_MANAGEMENT_DELETE_USER = "management_delete_user"
        const val ACTION_MANAGEMENT_CREATE_USER = "management_create_user"
        const val ACTION_LOGIN_BY_EMAIL = "login_by_email"
        const val ACTION_LOGIN_BY_PHONE = "login_by_phone"
        const val ACTION_LOGIN_BY_EXTERNAL_AUTH_PROVIDER = "login_by_external_auth_provider"
        const val ACTION_SEND_LOGIN_CONFIRMATION_TO_PHONE = "send_login_confirmation_to_phone"
        const val ACTION_REGISTER_BY_EMAIL = "register_by_email"
        const val ACTION_SEND_REGISTER_CONFIRMATION_TO_EMAIL = "send_register_confirmation_to_email"
        const val ACTION_RESET_PASSWORD = "reset_password"
        const val ACTION_SEND_RESET_PASSWORD_CONFIRMATION = "send_reset_password_confirmation"
        const val ACTION_LOGOUT = "logout"
        const val ACTION_SELF_REVOKE_SESSION = "self_revoke_session"
        const val ACTION_SELF_REVOKE_OTHER_SESSIONS = "self_revoke_other_sessions"
        const val ACTION_SELF_DELETE_IDENTIFIER = "self_delete_identifier"
        const val ACTION_ADD_IDENTIFIER_EMAIL = "add_identifier_email"
        const val ACTION_ADD_IDENTIFIER_PHONE = "add_identifier_phone"
        const val ACTION_ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER = "add_identifier_external_auth_provider"
        const val ACTION_SEND_ADD_EMAIL_IDENTIFIER_CONFIRMATION = "send_add_email_identifier_confirmation"
        const val ACTION_SEND_ADD_PHONE_IDENTIFIER_CONFIRMATION = "send_add_phone_identifier_confirmation"
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
