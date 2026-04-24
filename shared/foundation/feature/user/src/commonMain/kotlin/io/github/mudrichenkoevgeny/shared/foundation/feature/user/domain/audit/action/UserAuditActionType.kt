package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.login.ManagementLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.resetpassword.ManagementResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier.ManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.identifier.SelfManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session.ManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.session.SelfManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.ManagementUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.security.ManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.security.SelfManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.register.OpenRegisterRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword.OpenResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.identifier.OpenIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session.OpenSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.security.OpenUserSecurityRoutes

/**
 * User-feature audit actions (management API and open/self-service flows worth auditing).
 */
enum class UserAuditActionType : AuditActionType {
    /** [ManagementAuthSettingsRoutes.UPDATE_AUTH_SETTINGS]. */
    MANAGEMENT_UPDATE_AUTH_SETTINGS,

    /** [ManagementIdentifierRoutes.DELETE_IDENTIFIER]. */
    MANAGEMENT_DELETE_IDENTIFIER,

    /** [ManagementSessionRoutes.DELETE_SESSION]. */
    MANAGEMENT_DELETE_SESSION,

    /** [ManagementSessionRoutes.DELETE_ALL_USER_SESSIONS]. */
    MANAGEMENT_DELETE_ALL_USER_SESSIONS,

    /** [ManagementUserRoutes.UPDATE_USER]. */
    MANAGEMENT_UPDATE_USER,

    /** [ManagementUserRoutes.DELETE_USER]. */
    MANAGEMENT_DELETE_USER,

    /** [ManagementUserRoutes.CREATE_USER]. */
    MANAGEMENT_CREATE_USER,

    /** [ManagementUserSecurityRoutes.RESET_TOTP]. */
    MANAGEMENT_RESET_USER_TOTP,

    /** [ManagementUserSecurityRoutes.REGENERATE_RECOVERY_CODES]. */
    MANAGEMENT_REGENERATE_USER_RECOVERY_CODES,

    /** [ManagementLoginRoutes.LOGIN_BY_EMAIL], [OpenLoginRoutes.LOGIN_BY_EMAIL]. */
    LOGIN_BY_EMAIL,

    /** [OpenLoginRoutes.LOGIN_BY_PHONE]. */
    LOGIN_BY_PHONE,

    /** [OpenLoginRoutes.LOGIN_BY_EXTERNAL_AUTH_PROVIDER]. */
    LOGIN_BY_EXTERNAL_AUTH_PROVIDER,

    /** [ManagementLoginRoutes.LOGIN_BY_TOTP], [OpenLoginRoutes.LOGIN_BY_TOTP]. */
    LOGIN_BY_TOTP,

    /** [ManagementLoginRoutes.LOGIN_BY_TOTP_RECOVERY_CODE], [OpenLoginRoutes.LOGIN_BY_TOTP_RECOVERY_CODE]. */
    LOGIN_BY_TOTP_RECOVERY_CODE,

    /** [OpenRegisterRoutes.REGISTER_BY_EMAIL]. */
    REGISTER_BY_EMAIL,

    /** [ManagementResetPasswordRoutes.RESET_PASSWORD], [OpenResetPasswordRoutes.RESET_EMAIL_PASSWORD]. */
    RESET_PASSWORD,

    /** [SelfManagementSessionRoutes.LOGOUT], [OpenSessionRoutes.LOGOUT]. */
    LOGOUT,

    /** [SelfManagementSessionRoutes.DELETE_SESSION], [OpenSessionRoutes.DELETE_SESSION]. */
    SELF_DELETE_SESSION,

    /** [SelfManagementSessionRoutes.DELETE_ALL_OTHER_SESSIONS], [OpenSessionRoutes.DELETE_ALL_OTHER_SESSIONS]. */
    SELF_DELETE_OTHER_SESSIONS,

    /** [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION], [OpenSessionRoutes.REAUTHENTICATE_SESSION]. */
    REAUTHENTICATE_SESSION,

    /** [OpenIdentifierRoutes.DELETE_IDENTIFIER]. */
    SELF_DELETE_IDENTIFIER,

    /** [OpenIdentifierRoutes.ADD_IDENTIFIER_EMAIL]. */
    ADD_IDENTIFIER_EMAIL,

    /** [OpenIdentifierRoutes.ADD_IDENTIFIER_PHONE]. */
    ADD_IDENTIFIER_PHONE,

    /** [OpenIdentifierRoutes.ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER]. */
    ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER,

    /** [SelfManagementIdentifierRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD], [OpenIdentifierRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD]. */
    CHANGE_PASSWORD,

    /** [OpenUserRoutes.SCHEDULE_DELETION]. */
    SELF_SCHEDULE_USER_DELETION,

    /** [OpenUserRoutes.RESTORE_USER]. */
    SELF_RESTORE_USER,

    /** [SelfManagementUserSecurityRoutes.SETUP_TOTP], [OpenUserSecurityRoutes.SETUP_TOTP]. */
    SELF_SETUP_TOTP_INITIATED,

    /** [SelfManagementUserSecurityRoutes.ENABLE_TOTP], [OpenUserSecurityRoutes.ENABLE_TOTP]. */
    SELF_ENABLE_TOTP,

    /** [SelfManagementUserSecurityRoutes.DISABLE_TOTP], [OpenUserSecurityRoutes.DISABLE_TOTP]. */
    SELF_DISABLE_TOTP,

    /** [SelfManagementUserSecurityRoutes.GET_RECOVERY_CODES], [OpenUserSecurityRoutes.GET_RECOVERY_CODES]. */
    SELF_GET_RECOVERY_CODES,

    /** [SelfManagementUserSecurityRoutes.REGENERATE_RECOVERY_CODES], [OpenUserSecurityRoutes.REGENERATE_RECOVERY_CODES]. */
    SELF_REGENERATE_RECOVERY_CODES;

    /**
     * String representation of the [UserAuditActionType].
     */
    override val serialName: String
        get() = when (this) {
            MANAGEMENT_UPDATE_AUTH_SETTINGS -> ACTION_MANAGEMENT_UPDATE_AUTH_SETTINGS
            MANAGEMENT_DELETE_IDENTIFIER -> ACTION_MANAGEMENT_DELETE_IDENTIFIER
            MANAGEMENT_DELETE_SESSION -> ACTION_MANAGEMENT_DELETE_SESSION
            MANAGEMENT_DELETE_ALL_USER_SESSIONS -> ACTION_MANAGEMENT_DELETE_ALL_USER_SESSIONS
            MANAGEMENT_UPDATE_USER -> ACTION_MANAGEMENT_UPDATE_USER
            MANAGEMENT_DELETE_USER -> ACTION_MANAGEMENT_DELETE_USER
            MANAGEMENT_CREATE_USER -> ACTION_MANAGEMENT_CREATE_USER
            MANAGEMENT_RESET_USER_TOTP -> ACTION_MANAGEMENT_RESET_USER_TOTP
            MANAGEMENT_REGENERATE_USER_RECOVERY_CODES -> ACTION_MANAGEMENT_REGENERATE_USER_RECOVERY_CODES
            LOGIN_BY_EMAIL -> ACTION_LOGIN_BY_EMAIL
            LOGIN_BY_PHONE -> ACTION_LOGIN_BY_PHONE
            LOGIN_BY_EXTERNAL_AUTH_PROVIDER -> ACTION_LOGIN_BY_EXTERNAL_AUTH_PROVIDER
            LOGIN_BY_TOTP -> ACTION_LOGIN_BY_TOTP
            LOGIN_BY_TOTP_RECOVERY_CODE -> ACTION_LOGIN_BY_TOTP_RECOVERY_CODE
            REGISTER_BY_EMAIL -> ACTION_REGISTER_BY_EMAIL
            RESET_PASSWORD -> ACTION_RESET_PASSWORD
            LOGOUT -> ACTION_LOGOUT
            SELF_DELETE_SESSION -> ACTION_SELF_DELETE_SESSION
            SELF_DELETE_OTHER_SESSIONS -> ACTION_SELF_DELETE_OTHER_SESSIONS
            REAUTHENTICATE_SESSION -> ACTION_REAUTHENTICATE_SESSION
            SELF_DELETE_IDENTIFIER -> ACTION_SELF_DELETE_IDENTIFIER
            ADD_IDENTIFIER_EMAIL -> ACTION_ADD_IDENTIFIER_EMAIL
            ADD_IDENTIFIER_PHONE -> ACTION_ADD_IDENTIFIER_PHONE
            ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER -> ACTION_ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER
            CHANGE_PASSWORD -> ACTION_CHANGE_PASSWORD
            SELF_SCHEDULE_USER_DELETION -> ACTION_SELF_SCHEDULE_USER_DELETION
            SELF_RESTORE_USER -> ACTION_SELF_RESTORE_USER
            SELF_SETUP_TOTP_INITIATED -> ACTION_SELF_SETUP_TOTP_INITIATED
            SELF_ENABLE_TOTP -> ACTION_SELF_ENABLE_TOTP
            SELF_DISABLE_TOTP -> ACTION_SELF_DISABLE_TOTP
            SELF_GET_RECOVERY_CODES -> ACTION_SELF_GET_RECOVERY_CODES
            SELF_REGENERATE_RECOVERY_CODES -> ACTION_SELF_REGENERATE_RECOVERY_CODES
        }

    override fun parseOrNull(value: String): AuditActionType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditActionType = fromValueOrThrow(value)

    companion object {
        private const val ACTION_MANAGEMENT_UPDATE_AUTH_SETTINGS = "management_update_auth_settings"
        private const val ACTION_MANAGEMENT_DELETE_IDENTIFIER = "management_delete_identifier"
        private const val ACTION_MANAGEMENT_DELETE_SESSION = "management_delete_session"
        private const val ACTION_MANAGEMENT_DELETE_ALL_USER_SESSIONS = "management_delete_all_user_sessions"
        private const val ACTION_MANAGEMENT_UPDATE_USER = "management_update_user"
        private const val ACTION_MANAGEMENT_DELETE_USER = "management_delete_user"
        private const val ACTION_MANAGEMENT_CREATE_USER = "management_create_user"
        private const val ACTION_MANAGEMENT_RESET_USER_TOTP = "management_reset_user_totp"
        private const val ACTION_MANAGEMENT_REGENERATE_USER_RECOVERY_CODES = "management_regenerate_user_recovery_codes"
        private const val ACTION_LOGIN_BY_EMAIL = "login_by_email"
        private const val ACTION_LOGIN_BY_PHONE = "login_by_phone"
        private const val ACTION_LOGIN_BY_EXTERNAL_AUTH_PROVIDER = "login_by_external_auth_provider"
        private const val ACTION_LOGIN_BY_TOTP = "login_by_totp"
        private const val ACTION_LOGIN_BY_TOTP_RECOVERY_CODE = "login_by_totp_recovery_code"
        private const val ACTION_REGISTER_BY_EMAIL = "register_by_email"
        private const val ACTION_RESET_PASSWORD = "reset_password"
        private const val ACTION_LOGOUT = "logout"
        private const val ACTION_SELF_DELETE_SESSION = "self_delete_session"
        private const val ACTION_SELF_DELETE_OTHER_SESSIONS = "self_delete_other_sessions"
        private const val ACTION_REAUTHENTICATE_SESSION = "reauthenticate_session"
        private const val ACTION_SELF_DELETE_IDENTIFIER = "self_delete_identifier"
        private const val ACTION_ADD_IDENTIFIER_EMAIL = "add_identifier_email"
        private const val ACTION_ADD_IDENTIFIER_PHONE = "add_identifier_phone"
        private const val ACTION_ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER = "add_identifier_external_auth_provider"
        private const val ACTION_CHANGE_PASSWORD = "change_password"
        private const val ACTION_SELF_SCHEDULE_USER_DELETION = "self_schedule_user_deletion"
        private const val ACTION_SELF_RESTORE_USER = "self_restore_user"
        private const val ACTION_SELF_SETUP_TOTP_INITIATED = "self_setup_totp_initiated"
        private const val ACTION_SELF_ENABLE_TOTP = "self_enable_totp"
        private const val ACTION_SELF_DISABLE_TOTP = "self_disable_totp"
        private const val ACTION_SELF_GET_RECOVERY_CODES = "self_get_recovery_codes"
        private const val ACTION_SELF_REGENERATE_RECOVERY_CODES = "self_regenerate_recovery_codes"

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
