package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.ResetPasswordRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.SendResetPasswordConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.resetpassword.BaseOpenResetPasswordRoutes

/**
 * Route paths for password recovery in the open API.
 */
object OpenResetPasswordRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [ResetPasswordRequest].
     *
     * **Audit logging:** Persist an [AuditEvent] for every password reset attempt.
     * * **Action:** [UserAuditActionType.RESET_PASSWORD].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful reset.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success; leave as `null` for failed attempts.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     * 2. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address from the request.
     */
    const val RESET_EMAIL_PASSWORD = BaseOpenResetPasswordRoutes.RESET_PASSWORD

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [SendResetPasswordConfirmationRequest].
     *
     * Response body: [OtpConfirmationPayload].
     */
    const val SEND_RESET_EMAIL_PASSWORD_CONFIRMATION = BaseOpenResetPasswordRoutes.SEND_RESET_PASSWORD_CONFIRMATION
}