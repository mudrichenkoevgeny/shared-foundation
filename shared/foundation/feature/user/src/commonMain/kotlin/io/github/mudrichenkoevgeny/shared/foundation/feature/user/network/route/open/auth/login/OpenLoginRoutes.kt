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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.login.BaseOpenLoginRoutes

/**
 * Route paths for login in the open API.
 */
object OpenLoginRoutes {
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
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. If MFA is enabled, returns [SecurityErrorCodes.TOTP_CONFIRMATION_REQUIRED]
     * and a challenge token. Process must be completed via [LOGIN_BY_TOTP] or [LOGIN_BY_TOTP_RECOVERY_CODE].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.LOGIN_BY_EMAIL].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful authentication.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success; leave unset for failed attempts.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address from the request.
     */
    const val LOGIN_BY_EMAIL = BaseOpenLoginRoutes.LOGIN_BY_EMAIL

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
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. If MFA is enabled, returns [SecurityErrorCodes.TOTP_CONFIRMATION_REQUIRED]
     * and a challenge token. Process must be completed via [LOGIN_BY_TOTP] or [LOGIN_BY_TOTP_RECOVERY_CODE].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.LOGIN_BY_PHONE].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful authentication.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success; leave unset for failed attempts.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.PHONE_NUMBER] — phone number from the request.
     */
    const val LOGIN_BY_PHONE = BaseOpenLoginRoutes.LOGIN_BY_PHONE

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
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. If MFA is enabled, returns [SecurityErrorCodes.TOTP_CONFIRMATION_REQUIRED]
     * and a challenge token. Process must be completed via [LOGIN_BY_TOTP] or [LOGIN_BY_TOTP_RECOVERY_CODE].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.LOGIN_BY_EXTERNAL_AUTH_PROVIDER].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful authentication.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success; leave unset for failed attempts.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.EXTERNAL_ID] — external subject identifier.
     * 3. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address if provided by the external provider.
     */
    const val LOGIN_BY_EXTERNAL_AUTH_PROVIDER = BaseOpenLoginRoutes.LOGIN_BY_EXTERNAL_AUTH_PROVIDER

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
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.LOGIN_BY_TOTP].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] upon success.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val LOGIN_BY_TOTP = BaseOpenLoginRoutes.LOGIN_BY_TOTP

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
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.LOGIN_BY_TOTP_RECOVERY_CODE].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] upon success.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val LOGIN_BY_TOTP_RECOVERY_CODE = BaseOpenLoginRoutes.LOGIN_BY_TOTP_RECOVERY_CODE

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
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     */
    const val SEND_LOGIN_CONFIRMATION_TO_PHONE = BaseOpenLoginRoutes.SEND_LOGIN_CONFIRMATION_TO_PHONE
}