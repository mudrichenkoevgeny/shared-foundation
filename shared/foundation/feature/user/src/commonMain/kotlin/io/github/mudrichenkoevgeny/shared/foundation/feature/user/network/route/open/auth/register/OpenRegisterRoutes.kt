package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.register

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.register.RegisterByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.confirmation.SendConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.register.BaseRegisterRoutes

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
     * **Audit logging:** Persist an [AuditEvent] for successful registration and for security-relevant denials. Use
     * action [UserAuditActionType.REGISTER_BY_EMAIL] and resource [UserAuditResourceType.USER_EMAIL]. After success, set
     * `resourceId` to the new account id when available from [AuthDataPayload].
     */
    const val REGISTER_BY_EMAIL = BaseRegisterRoutes.REGISTER_BY_EMAIL

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [SendConfirmationToEmailRequest].
     *
     * Response body: [SendConfirmationPayload].
     */
    const val SEND_REGISTER_CONFIRMATION_TO_EMAIL = BaseRegisterRoutes.SEND_REGISTER_CONFIRMATION_TO_EMAIL
}
