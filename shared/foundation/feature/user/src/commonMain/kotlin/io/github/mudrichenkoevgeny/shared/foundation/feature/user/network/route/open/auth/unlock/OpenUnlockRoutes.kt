package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.auth.unlock

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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByEmailConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByPhoneConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.BaseAuthRoutes

/**
 * Route paths for self-service account unlocking in the open API.
 */
object OpenUnlockRoutes {
    /** Base path for open unlock operations. */
    private const val BASE_OPEN_UNLOCK_ROUTE = "${BaseAuthRoutes.BASE_AUTH_ROUTE}/unlock"

    /** Base path for email unlock operations. */
    private const val UNLOCK_BY_EMAIL_ROUTE = "$BASE_OPEN_UNLOCK_ROUTE/email"

    /** Base path for phone unlock operations. */
    private const val UNLOCK_BY_PHONE_ROUTE = "$BASE_OPEN_UNLOCK_ROUTE/phone"

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
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     */
    const val SEND_UNLOCK_EMAIL_CONFIRMATION = "$UNLOCK_BY_EMAIL_ROUTE/send-confirmation"

    /**
     * **HTTP method:** `POST`
     *
     * Unlocks a temporarily locked account using an email confirmation code.
     *
     * Request body: [UnlockByEmailConfirmationRequest].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.SELF_UNLOCK_ACCOUNT].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful unlock.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address from the request.
     */
    const val UNLOCK_BY_EMAIL = UNLOCK_BY_EMAIL_ROUTE

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
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     */
    const val SEND_UNLOCK_PHONE_CONFIRMATION = "$UNLOCK_BY_PHONE_ROUTE/send-confirmation"

    /**
     * **HTTP method:** `POST`
     *
     * Unlocks a temporarily locked account using a phone confirmation code.
     *
     * Request body: [UnlockByPhoneConfirmationRequest].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.SELF_UNLOCK_ACCOUNT].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful unlock.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.PHONE_NUMBER] — phone number from the request.
     */
    const val UNLOCK_BY_PHONE = UNLOCK_BY_PHONE_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Unlocks a temporarily locked account using an external authentication provider token.
     *
     * Request body: [UnlockByExternalAuthProviderRequest].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.SELF_UNLOCK_ACCOUNT].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful unlock.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     * 2. [UserAuditMetadataKey.USER_AUTH_PROVIDER] — the identity provider used.
     */
    const val UNLOCK_BY_EXTERNAL_PROVIDER = "$BASE_OPEN_UNLOCK_ROUTE/external-provider"
}
