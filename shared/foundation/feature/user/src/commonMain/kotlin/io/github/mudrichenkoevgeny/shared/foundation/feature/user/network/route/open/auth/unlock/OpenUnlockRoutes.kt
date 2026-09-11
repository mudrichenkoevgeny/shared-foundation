package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.unlock

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByEmailConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByPhoneConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.unlock.BaseOpenUnlockRoutes

/**
 * Route paths for self-service account unlocking in the open API.
 */
object OpenUnlockRoutes {

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code (OTP) to be sent to the user's email to initiate the unlock process.
     *
     * Request body: [SendConfirmationToEmailRequest].
     *
     * Response body: [OtpConfirmationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     */
    const val SEND_UNLOCK_EMAIL_CONFIRMATION = BaseOpenUnlockRoutes.SEND_UNLOCK_EMAIL_CONFIRMATION

    /**
     * **HTTP method:** `POST`
     *
     * Unlocks a temporarily locked account using an email confirmation code.
     *
     * Request body: [UnlockByEmailConfirmationRequest].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.SELF_UNLOCK_ACCOUNT].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful unlock.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address from the request.
     */
    const val UNLOCK_BY_EMAIL = BaseOpenUnlockRoutes.UNLOCK_BY_EMAIL

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code (OTP) to be sent to the user's phone to initiate the unlock process.
     *
     * Request body: [SendConfirmationToPhoneRequest].
     *
     * Response body: [OtpConfirmationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     */
    const val SEND_UNLOCK_PHONE_CONFIRMATION = BaseOpenUnlockRoutes.SEND_UNLOCK_PHONE_CONFIRMATION

    /**
     * **HTTP method:** `POST`
     *
     * Unlocks a temporarily locked account using a phone confirmation code.
     *
     * Request body: [UnlockByPhoneConfirmationRequest].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.SELF_UNLOCK_ACCOUNT].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful unlock.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.PHONE_NUMBER] — phone number from the request.
     */
    const val UNLOCK_BY_PHONE = BaseOpenUnlockRoutes.UNLOCK_BY_PHONE

    /**
     * **HTTP method:** `POST`
     *
     * Unlocks a temporarily locked account using an external authentication provider token.
     *
     * Request body: [UnlockByExternalAuthProviderRequest].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.SELF_UNLOCK_ACCOUNT].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful unlock.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.USER_AUTH_PROVIDER] — the identity provider used.
     */
    const val UNLOCK_BY_EXTERNAL_PROVIDER = BaseOpenUnlockRoutes.UNLOCK_BY_EXTERNAL_PROVIDER
}
