package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.register

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.register.RegisterByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.register.BaseOpenRegisterRoutes

/**
 * Route paths for registration in the open API.
 */
object OpenRegisterRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [RegisterByEmailRequest].
     *
     * Response body: [AuthDataPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for every registration attempt.
     * * **Action:** [UserAuditActionType.REGISTER_BY_EMAIL].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the new [UserId] only upon successful registration.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success; leave as `null` for failed attempts.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     * 2. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address from the request.
     */
    const val REGISTER_BY_EMAIL = BaseOpenRegisterRoutes.REGISTER_BY_EMAIL

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [SendConfirmationToEmailRequest].
     *
     * Response body: [OtpConfirmationPayload].
     */
    const val SEND_REGISTER_CONFIRMATION_TO_EMAIL = BaseOpenRegisterRoutes.SEND_REGISTER_CONFIRMATION_TO_EMAIL
}