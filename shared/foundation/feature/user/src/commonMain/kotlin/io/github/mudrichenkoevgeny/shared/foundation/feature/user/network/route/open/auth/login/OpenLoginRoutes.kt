package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.login

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.login.LoginByPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.data.AuthDataPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.confirmation.SendConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.login.BaseLoginRoutes

/**
 * Route paths for login in the open API.
 */
object OpenLoginRoutes {
    /**
     * **HTTP method:** `POST`
     *
     * Request body: [LoginByEmailRequest].
     *
     * Response body: [AuthDataPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful authentication and for failed attempts when your policy
     * records login abuse. Use action [UserAuditActionType.LOGIN_BY_EMAIL] and resource [UserAuditResourceType.USER_EMAIL].
     * Set `resourceId` to the authenticated user id when known; omit or leave unset before the user is resolved.
     */
    const val LOGIN_BY_EMAIL = BaseLoginRoutes.LOGIN_BY_EMAIL

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [LoginByPhoneRequest].
     *
     * Response body: [AuthDataPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful authentication and for failed attempts when your policy
     * records login abuse. Use action [UserAuditActionType.LOGIN_BY_PHONE] and resource [UserAuditResourceType.USER_PHONE].
     * Set `resourceId` to the authenticated user id when known; omit or leave unset before the user is resolved.
     */
    const val LOGIN_BY_PHONE = BaseLoginRoutes.LOGIN_BY_PHONE

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [LoginByExternalAuthProviderRequest].
     *
     * Response body: [AuthDataPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful authentication and for failed attempts when your policy
     * records login abuse. Use action [UserAuditActionType.LOGIN_BY_EXTERNAL_AUTH_PROVIDER] and resource
     * [UserAuditResourceType.USER_IDENTIFIER]. Set `resourceId` to the authenticated user id when known.
     */
    const val LOGIN_BY_EXTERNAL_AUTH_PROVIDER = BaseLoginRoutes.LOGIN_BY_EXTERNAL_AUTH_PROVIDER

    /**
     * **HTTP method:** `POST`
     *
     * Request body: [SendConfirmationToPhoneRequest].
     *
     * Response body: [SendConfirmationPayload].
     */
    const val SEND_LOGIN_CONFIRMATION_TO_PHONE = BaseLoginRoutes.SEND_LOGIN_CONFIRMATION_TO_PHONE
}
