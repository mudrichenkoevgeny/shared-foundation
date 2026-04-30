package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.verifytotp.VerifyTotpPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.login.BaseSelfManagementLoginRoutes

/**
 * Route paths for login in the management API.
 */
object SelfManagementLoginRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Authenticates a management account using email and password.
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
     * **Note:** The server strictly validates that the credentials belong to an account
     * with management-level roles ([UserRole.STAFF] or [UserRole.ADMIN]).
     *
     * **Security:** Sensitive operation. If MFA is enabled for the account, this method returns
     * a [SecurityErrorCodes.TOTP_CONFIRMATION_REQUIRED] error and a challenge token.
     * The process must be completed via [LOGIN_BY_TOTP] or [LOGIN_BY_TOTP_RECOVERY_CODE].
     *
     * **Audit logging:** Persist an [AuditEvent] for every authentication attempt.
     * * **Action:** [UserAuditActionType.LOGIN_BY_EMAIL].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful authentication.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success;
     * leave as `null` for failed attempts.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address from the request.
     */
    const val LOGIN_BY_EMAIL = BaseSelfManagementLoginRoutes.LOGIN_BY_EMAIL

    /**
     * **HTTP method:** `POST`
     *
     * Finalizes the management login process by providing a TOTP code.
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
     * **Note:** The server strictly validates that the challenge token belongs to an account
     * with management-level roles ([UserRole.STAFF] or [UserRole.ADMIN]).
     *
     * **Audit logging:** Persist an [AuditEvent] for the second-factor verification.
     * * **Action:** [UserAuditActionType.LOGIN_BY_TOTP].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] upon success.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val LOGIN_BY_TOTP = BaseSelfManagementLoginRoutes.LOGIN_BY_TOTP

    /**
     * **HTTP method:** `POST`
     *
     * Finalizes the management login process by providing a recovery code.
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
     * **Note:** The server strictly validates that the challenge token belongs to an account
     * with management-level roles ([UserRole.STAFF] or [UserRole.ADMIN]).
     *
     * **Audit logging:** Persist an [AuditEvent] for the backup-factor verification.
     * * **Action:** [UserAuditActionType.LOGIN_BY_TOTP_RECOVERY_CODE].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] upon success.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val LOGIN_BY_TOTP_RECOVERY_CODE = BaseSelfManagementLoginRoutes.LOGIN_BY_TOTP_RECOVERY_CODE
}