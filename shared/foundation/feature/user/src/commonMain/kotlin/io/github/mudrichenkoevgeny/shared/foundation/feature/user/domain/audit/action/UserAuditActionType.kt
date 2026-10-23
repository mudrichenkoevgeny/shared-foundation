package io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.login.SelfManagementLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.resetpassword.SelfManagementResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings.ManagementAuthSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.identifiers.ManagementIdentifiersRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.identifier.SelfManagementIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.sessions.ManagementSessionsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.ManagementUsersRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.users.security.ManagementUsersSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.security.SelfManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login.OpenLoginRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.registration.OpenRegistrationRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword.OpenResetPasswordRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.unlock.OpenUnlockRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.identifier.OpenIdentifierRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.session.OpenSessionRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.OpenUserRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user.security.OpenUserSecurityRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val ACTION_MANAGEMENT_UPDATE_AUTH_SETTINGS = "management_update_auth_settings"
private const val ACTION_MANAGEMENT_RESET_AUTH_SETTINGS = "management_reset_auth_settings"
private const val ACTION_MANAGEMENT_DELETE_IDENTIFIER = "management_delete_identifier"
private const val ACTION_MANAGEMENT_DELETE_IDENTIFIER_PASSWORD = "management_delete_identifier_password"
private const val ACTION_MANAGEMENT_DELETE_SESSION = "management_delete_session"
private const val ACTION_MANAGEMENT_DELETE_ALL_USER_SESSIONS = "management_delete_all_user_sessions"
private const val ACTION_MANAGEMENT_UPDATE_USER = "management_update_user"
private const val ACTION_MANAGEMENT_DELETE_USER = "management_delete_user"
private const val ACTION_MANAGEMENT_CREATE_USER = "management_create_user"
private const val ACTION_MANAGEMENT_DISABLE_USER_TOTP = "management_disable_user_totp"
private const val ACTION_LOGIN_BY_EMAIL = "login_by_email"
private const val ACTION_LOGIN_BY_PHONE = "login_by_phone"
private const val ACTION_LOGIN_BY_EXTERNAL_AUTH_PROVIDER = "login_by_external_auth_provider"
private const val ACTION_LOGIN_BY_TOTP = "login_by_totp"
private const val ACTION_LOGIN_BY_TOTP_RECOVERY_CODE = "login_by_totp_recovery_code"
private const val ACTION_NEW_DEVICE_DETECTED = "new_device_detected"
private const val ACTION_REGISTRATION_BY_EMAIL = "registration_by_email"
private const val ACTION_RESET_PASSWORD = "reset_password"
private const val ACTION_SELF_UNLOCK_ACCOUNT = "self_unlock_account"
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
private const val ACTION_REFRESH_TOKEN_REUSE_DETECTED = "refresh_token_reuse_detected"

/**
 * User-feature audit action kinds for administrative and security-critical user operations.
 *
 * @property value String representation of the [UserAuditActionType].
 */
@Serializable
enum class UserAuditActionType(override val value: String) : AuditActionType {
    /** Administrative update of platform-wide authentication settings via [ManagementAuthSettingsRoutes.UPDATE_MANAGEMENT_AUTH_SETTINGS]. */
    @SerialName(ACTION_MANAGEMENT_UPDATE_AUTH_SETTINGS)
    MANAGEMENT_UPDATE_AUTH_SETTINGS(ACTION_MANAGEMENT_UPDATE_AUTH_SETTINGS),

    /** Administrative reset of authentication settings to default platform values via [ManagementAuthSettingsRoutes.RESET_MANAGEMENT_AUTH_SETTINGS]. */
    @SerialName(ACTION_MANAGEMENT_RESET_AUTH_SETTINGS)
    MANAGEMENT_RESET_AUTH_SETTINGS(ACTION_MANAGEMENT_RESET_AUTH_SETTINGS),

    /** Administrative deletion of a user authentication identifier via [ManagementIdentifiersRoutes.DELETE_IDENTIFIER]. */
    @SerialName(ACTION_MANAGEMENT_DELETE_IDENTIFIER)
    MANAGEMENT_DELETE_IDENTIFIER(ACTION_MANAGEMENT_DELETE_IDENTIFIER),

    /** Administrative deletion or revocation of password credentials from a user identifier via [ManagementIdentifiersRoutes.DELETE_IDENTIFIER_PASSWORD]. */
    @SerialName(ACTION_MANAGEMENT_DELETE_IDENTIFIER_PASSWORD)
    MANAGEMENT_DELETE_IDENTIFIER_PASSWORD(ACTION_MANAGEMENT_DELETE_IDENTIFIER_PASSWORD),

    /** Administrative termination of a specific user session via [ManagementSessionsRoutes.DELETE_SESSION]. */
    @SerialName(ACTION_MANAGEMENT_DELETE_SESSION)
    MANAGEMENT_DELETE_SESSION(ACTION_MANAGEMENT_DELETE_SESSION),

    /** Administrative termination of all active sessions for a target user via [ManagementSessionsRoutes.DELETE_ALL_USER_SESSIONS]. */
    @SerialName(ACTION_MANAGEMENT_DELETE_ALL_USER_SESSIONS)
    MANAGEMENT_DELETE_ALL_USER_SESSIONS(ACTION_MANAGEMENT_DELETE_ALL_USER_SESSIONS),

    /** Administrative update of a user account profile or role assignments via [ManagementUsersRoutes.UPDATE_USER]. */
    @SerialName(ACTION_MANAGEMENT_UPDATE_USER)
    MANAGEMENT_UPDATE_USER(ACTION_MANAGEMENT_UPDATE_USER),

    /** Administrative deletion or permanent purge of a user account via [ManagementUsersRoutes.DELETE_USER]. */
    @SerialName(ACTION_MANAGEMENT_DELETE_USER)
    MANAGEMENT_DELETE_USER(ACTION_MANAGEMENT_DELETE_USER),

    /** Administrative creation of a new user account via [ManagementUsersRoutes.CREATE_USER]. */
    @SerialName(ACTION_MANAGEMENT_CREATE_USER)
    MANAGEMENT_CREATE_USER(ACTION_MANAGEMENT_CREATE_USER),

    /** Administrative disabling of multi-factor authentication (TOTP) for a target user via [ManagementUsersSecurityRoutes.DISABLE_TOTP]. */
    @SerialName(ACTION_MANAGEMENT_DISABLE_USER_TOTP)
    MANAGEMENT_DISABLE_USER_TOTP(ACTION_MANAGEMENT_DISABLE_USER_TOTP),

    /** User authentication attempt performed using email address and password credentials via [OpenLoginRoutes.LOGIN_BY_EMAIL] or [SelfManagementLoginRoutes.LOGIN_BY_EMAIL]. */
    @SerialName(ACTION_LOGIN_BY_EMAIL)
    LOGIN_BY_EMAIL(ACTION_LOGIN_BY_EMAIL),

    /** User authentication attempt performed using phone number confirmation code via [OpenLoginRoutes.LOGIN_BY_PHONE]. */
    @SerialName(ACTION_LOGIN_BY_PHONE)
    LOGIN_BY_PHONE(ACTION_LOGIN_BY_PHONE),

    /** User authentication attempt performed via external identity provider OAuth tokens (e.g., Google, Apple) via [OpenLoginRoutes.LOGIN_BY_EXTERNAL_AUTH_PROVIDER]. */
    @SerialName(ACTION_LOGIN_BY_EXTERNAL_AUTH_PROVIDER)
    LOGIN_BY_EXTERNAL_AUTH_PROVIDER(ACTION_LOGIN_BY_EXTERNAL_AUTH_PROVIDER),

    /** Second-factor authentication verification step using TOTP time-based code via [OpenLoginRoutes.LOGIN_BY_TOTP] or [SelfManagementLoginRoutes.LOGIN_BY_TOTP]. */
    @SerialName(ACTION_LOGIN_BY_TOTP)
    LOGIN_BY_TOTP(ACTION_LOGIN_BY_TOTP),

    /** Emergency authentication recovery step using a one-time backup recovery code via [OpenLoginRoutes.LOGIN_BY_TOTP_RECOVERY_CODE] or [SelfManagementLoginRoutes.LOGIN_BY_TOTP_RECOVERY_CODE]. */
    @SerialName(ACTION_LOGIN_BY_TOTP_RECOVERY_CODE)
    LOGIN_BY_TOTP_RECOVERY_CODE(ACTION_LOGIN_BY_TOTP_RECOVERY_CODE),

    /** Security event triggered when a user logs in from a previously unrecognized device or IP address. */
    @SerialName(ACTION_NEW_DEVICE_DETECTED)
    NEW_DEVICE_DETECTED(ACTION_NEW_DEVICE_DETECTED),

    /** Account registration flow completed using email verification via [OpenRegistrationRoutes.REGISTRATION_BY_EMAIL]. */
    @SerialName(ACTION_REGISTRATION_BY_EMAIL)
    REGISTRATION_BY_EMAIL(ACTION_REGISTRATION_BY_EMAIL),

    /** Password reset flow completed via email confirmation token via [OpenResetPasswordRoutes.RESET_EMAIL_PASSWORD] or [SelfManagementResetPasswordRoutes.RESET_PASSWORD]. */
    @SerialName(ACTION_RESET_PASSWORD)
    RESET_PASSWORD(ACTION_RESET_PASSWORD),

    /** Account self-service unlock completed following lockout via [OpenUnlockRoutes.UNLOCK_BY_EMAIL], [OpenUnlockRoutes.UNLOCK_BY_PHONE], or [OpenUnlockRoutes.UNLOCK_BY_EXTERNAL_PROVIDER]. */
    @SerialName(ACTION_SELF_UNLOCK_ACCOUNT)
    SELF_UNLOCK_ACCOUNT(ACTION_SELF_UNLOCK_ACCOUNT),

    /** Explicit session termination by the user via [OpenSessionRoutes.LOGOUT] or [SelfManagementSessionRoutes.LOGOUT]. */
    @SerialName(ACTION_LOGOUT)
    LOGOUT(ACTION_LOGOUT),

    /** Self-service revocation of a specific active user session via [OpenSessionRoutes.DELETE_SESSION] or [SelfManagementSessionRoutes.DELETE_SESSION]. */
    @SerialName(ACTION_SELF_DELETE_SESSION)
    SELF_DELETE_SESSION(ACTION_SELF_DELETE_SESSION),

    /** Self-service revocation of all other active user sessions except the current one via [OpenSessionRoutes.DELETE_ALL_OTHER_SESSIONS] or [SelfManagementSessionRoutes.DELETE_ALL_OTHER_SESSIONS]. */
    @SerialName(ACTION_SELF_DELETE_OTHER_SESSIONS)
    SELF_DELETE_OTHER_SESSIONS(ACTION_SELF_DELETE_OTHER_SESSIONS),

    /** Step-up session re-authentication required for high-security operations via [OpenSessionRoutes.REAUTHENTICATE_SESSION] or [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION]. */
    @SerialName(ACTION_REAUTHENTICATE_SESSION)
    REAUTHENTICATE_SESSION(ACTION_REAUTHENTICATE_SESSION),

    /** Self-service deletion of a linked authentication identifier via [OpenIdentifierRoutes.DELETE_IDENTIFIER]. */
    @SerialName(ACTION_SELF_DELETE_IDENTIFIER)
    SELF_DELETE_IDENTIFIER(ACTION_SELF_DELETE_IDENTIFIER),

    /** Self-service linking of a new email identifier to an existing account via [OpenIdentifierRoutes.ADD_IDENTIFIER_EMAIL]. */
    @SerialName(ACTION_ADD_IDENTIFIER_EMAIL)
    ADD_IDENTIFIER_EMAIL(ACTION_ADD_IDENTIFIER_EMAIL),

    /** Self-service linking of a new phone identifier to an existing account via [OpenIdentifierRoutes.ADD_IDENTIFIER_PHONE]. */
    @SerialName(ACTION_ADD_IDENTIFIER_PHONE)
    ADD_IDENTIFIER_PHONE(ACTION_ADD_IDENTIFIER_PHONE),

    /** Self-service linking of an external OAuth provider identifier to an account via [OpenIdentifierRoutes.ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER]. */
    @SerialName(ACTION_ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER)
    ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER(ACTION_ADD_IDENTIFIER_EXTERNAL_AUTH_PROVIDER),

    /** Password change operation performed by authenticated user via [OpenIdentifierRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD] or [SelfManagementIdentifierRoutes.IDENTIFIER_EMAIL_CHANGE_PASSWORD]. */
    @SerialName(ACTION_CHANGE_PASSWORD)
    CHANGE_PASSWORD(ACTION_CHANGE_PASSWORD),

    /** Account deletion request scheduled by the user via [OpenUserRoutes.SCHEDULE_DELETION]. */
    @SerialName(ACTION_SELF_SCHEDULE_USER_DELETION)
    SELF_SCHEDULE_USER_DELETION(ACTION_SELF_SCHEDULE_USER_DELETION),

    /** Account cancellation of scheduled deletion via [OpenUserRoutes.RESTORE_USER]. */
    @SerialName(ACTION_SELF_RESTORE_USER)
    SELF_RESTORE_USER(ACTION_SELF_RESTORE_USER),

    /** Multi-factor authentication setup process initiated via [OpenUserSecurityRoutes.SETUP_TOTP] or [SelfManagementUserSecurityRoutes.SETUP_TOTP]. */
    @SerialName(ACTION_SELF_SETUP_TOTP_INITIATED)
    SELF_SETUP_TOTP_INITIATED(ACTION_SELF_SETUP_TOTP_INITIATED),

    /** Multi-factor authentication successfully enabled after code verification via [OpenUserSecurityRoutes.ENABLE_TOTP] or [SelfManagementUserSecurityRoutes.ENABLE_TOTP]. */
    @SerialName(ACTION_SELF_ENABLE_TOTP)
    SELF_ENABLE_TOTP(ACTION_SELF_ENABLE_TOTP),

    /** Multi-factor authentication disabled by user via [OpenUserSecurityRoutes.DISABLE_TOTP] or [SelfManagementUserSecurityRoutes.DISABLE_TOTP]. */
    @SerialName(ACTION_SELF_DISABLE_TOTP)
    SELF_DISABLE_TOTP(ACTION_SELF_DISABLE_TOTP),

    /** Retrieval of emergency TOTP backup recovery codes via [OpenUserSecurityRoutes.GET_RECOVERY_CODES] or [SelfManagementUserSecurityRoutes.GET_RECOVERY_CODES]. */
    @SerialName(ACTION_SELF_GET_RECOVERY_CODES)
    SELF_GET_RECOVERY_CODES(ACTION_SELF_GET_RECOVERY_CODES),

    /** Regeneration of new emergency TOTP backup recovery codes via [OpenUserSecurityRoutes.REGENERATE_RECOVERY_CODES] or [SelfManagementUserSecurityRoutes.REGENERATE_RECOVERY_CODES]. */
    @SerialName(ACTION_SELF_REGENERATE_RECOVERY_CODES)
    SELF_REGENERATE_RECOVERY_CODES(ACTION_SELF_REGENERATE_RECOVERY_CODES),

    /** Security alert event triggered when an already rotated refresh token is presented after grace period expiration. */
    @SerialName(ACTION_REFRESH_TOKEN_REUSE_DETECTED)
    REFRESH_TOKEN_REUSE_DETECTED(ACTION_REFRESH_TOKEN_REUSE_DETECTED);

    companion object {
        /**
         * Returns [UserAuditActionType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): UserAuditActionType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [UserAuditActionType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [UserAuditActionType].
         */
        fun fromValueOrThrow(value: String): UserAuditActionType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for UserAuditActionType: '$value'")
    }
}
