package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.ResetPasswordRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.SendResetPasswordConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Route paths for password recovery and initial password setup in the open API.
 */
object OpenResetPasswordRoutes {
    /** Base path for open password reset operations. */
    private const val BASE_RESET_PASSWORD_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/reset-password"

    /**
     * **HTTP method:** `POST`
     *
     * Resets a forgotten password or sets up an initial password using a previously sent confirmation code or invitation token.
     *
     * Request body: [ResetPasswordRequest].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.RESET_PASSWORD].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful reset.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success; leave unset for failed attempts.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address from the request.
     */
    const val RESET_EMAIL_PASSWORD = BASE_RESET_PASSWORD_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code (OTP) to be sent to the user's email to initiate password recovery or initial setup.
     *
     * Request body: [SendResetPasswordConfirmationRequest].
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
    const val SEND_RESET_EMAIL_PASSWORD_CONFIRMATION = "$BASE_RESET_PASSWORD_ROUTE/send-confirmation"
}