package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.resetpassword

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.ResetPasswordRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.password.SendResetPasswordConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.confirmation.SendConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.resetpassword.BaseResetPasswordRoutes

/**
 * Route paths for password recovery in the open API.
 */
object OpenResetPasswordRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [ResetPasswordRequest].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful password reset and for security-relevant denials. Use
     * action [UserAuditActionType.RESET_PASSWORD] and resource [UserAuditResourceType.USER_EMAIL]. Set `resourceId` to the
     * affected user id when known after validation; do not place secrets or raw reset tokens in metadata.
     */
    const val RESET_EMAIL_PASSWORD = BaseResetPasswordRoutes.RESET_PASSWORD

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [SendResetPasswordConfirmationRequest].
     *
     * Response body: [SendConfirmationPayload].
     */
    const val SEND_RESET_EMAIL_PASSWORD_CONFIRMATION = BaseResetPasswordRoutes.SEND_RESET_PASSWORD_CONFIRMATION
}
