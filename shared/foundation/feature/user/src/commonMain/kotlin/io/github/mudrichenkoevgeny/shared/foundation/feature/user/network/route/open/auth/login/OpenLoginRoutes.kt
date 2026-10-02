package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Route paths for login in the open API.
 */
object OpenLoginRoutes {
    /** Base path for open login operations. */
    private const val BASE_LOGIN_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/login"

    /** Base path for phone login operations. */
    private const val BASE_LOGIN_PHONE_ROUTE = "$BASE_LOGIN_ROUTE/phone"
    /**
     * **HTTP method:** `POST`
     *
     * Authenticates a user using email and password credentials.
     *
     * Request body: [LoginByEmailRequest].
     *
     * Response body: [AuthDataPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. If MFA is enabled, returns [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED]
     * and a challenge token. Process must be completed via [LOGIN_BY_TOTP] or [LOGIN_BY_TOTP_RECOVERY_CODE].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.LOGIN_BY_EMAIL]. (If the sign-in is from a new,
     * unrecognized device, an additional audit event with action
     * [UserAuditActionType.NEW_DEVICE_DETECTED] is persisted).
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful authentication.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success; leave unset for failed attempts.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address from the request.
     */
    const val LOGIN_BY_EMAIL = "$BASE_LOGIN_ROUTE/email"

    /**
     * **HTTP method:** `POST`
     *
     * Authenticates a user using phone number and a verification code.
     *
     * Request body: [LoginByPhoneRequest].
     *
     * Response body: [AuthDataPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. If MFA is enabled, returns [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED]
     * and a challenge token. Process must be completed via [LOGIN_BY_TOTP] or [LOGIN_BY_TOTP_RECOVERY_CODE].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.LOGIN_BY_PHONE]. (If the sign-in is from a new,
     * unrecognized device, an additional audit event with action
     * [UserAuditActionType.NEW_DEVICE_DETECTED] is persisted).
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful authentication.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success; leave unset for failed attempts.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.PHONE_NUMBER] — phone number from the request.
     */
    const val LOGIN_BY_PHONE = BASE_LOGIN_PHONE_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Authenticates a user via an external authentication provider.
     *
     * Request body: [LoginByExternalAuthProviderRequest].
     *
     * Response body: [AuthDataPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. If MFA is enabled, returns [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED]
     * and a challenge token. Process must be completed via [LOGIN_BY_TOTP] or [LOGIN_BY_TOTP_RECOVERY_CODE].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.LOGIN_BY_EXTERNAL_AUTH_PROVIDER]. (If the sign-in is
     * from a new, unrecognized device, an additional audit event with action
     * [UserAuditActionType.NEW_DEVICE_DETECTED] is persisted).
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful authentication.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success; leave unset for failed attempts.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.EXTERNAL_ID] — external subject identifier.
     * 3. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address if provided by the external provider.
     */
    const val LOGIN_BY_EXTERNAL_AUTH_PROVIDER = "$BASE_LOGIN_ROUTE/external-auth-provider"

    /**
     * **HTTP method:** `POST`
     *
     * Completes the login process by verifying a TOTP code (second factor).
     *
     * Request body: [VerifyTotpPayload].
     *
     * Response body: [AuthDataPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.LOGIN_BY_TOTP]. (If the sign-in is from a new,
     * unrecognized device, an additional audit event with action
     * [UserAuditActionType.NEW_DEVICE_DETECTED] is persisted).
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] upon success.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val LOGIN_BY_TOTP = "$BASE_LOGIN_ROUTE/totp"

    /**
     * **HTTP method:** `POST`
     *
     * Completes the login process by verifying a backup recovery code.
     *
     * Request body: [VerifyTotpPayload].
     *
     * Response body: [AuthDataPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.LOGIN_BY_TOTP_RECOVERY_CODE]. (If the sign-in is from a
     * new, unrecognized device, an additional audit event with action
     * [UserAuditActionType.NEW_DEVICE_DETECTED] is persisted).
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] upon success.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val LOGIN_BY_TOTP_RECOVERY_CODE = "$BASE_LOGIN_ROUTE/totp-recovery-code"

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code (OTP) to be sent to the specified phone number for login.
     *
     * Request body: [SendConfirmationToPhoneRequest].
     *
     * Response body: [OtpConfirmationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     */
    const val SEND_LOGIN_CONFIRMATION_TO_PHONE = "$BASE_LOGIN_PHONE_ROUTE/confirmation/send-to-phone"
}