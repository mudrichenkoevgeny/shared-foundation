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
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.client.ClientAuthNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByEmailConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByExternalAuthProviderRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByPhoneConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToPhoneRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.BaseUserNetworkRoutes

/**
 * Route paths for self-service account unlocking in the open API.
 */
object OpenUnlockRoutes {
    /** Base path for open unlock operations. */
    private const val BASE_OPEN_UNLOCK_ROUTE = "${BaseUserNetworkRoutes.BASE_USER_AUTH_NETWORK_ROUTE}/unlock"

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
     * Response body: [OtpConfirmationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     *   [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientAuthNavigation.UnlockTargetInput]
     *   - [ClientAuthNavigation.UnlockOtpInput]
     */
    const val SEND_UNLOCK_EMAIL_CONFIRMATION = "$UNLOCK_BY_EMAIL_ROUTE/send-confirmation"

    /**
     * **HTTP method:** `POST`
     *
     * Unlocks a temporarily locked account using an email confirmation code.
     *
     * Request body: [UnlockByEmailConfirmationRequest].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     *   [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.SELF_UNLOCK_ACCOUNT].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful unlock.
     * - **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address from the request.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientAuthNavigation.UnlockOtpInput]
     */
    const val UNLOCK_BY_EMAIL = UNLOCK_BY_EMAIL_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Requests a confirmation code (OTP) to be sent to the user's phone to initiate the unlock process.
     *
     * Request body: [SendConfirmationToPhoneRequest].
     * Response body: [OtpConfirmationPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     *   [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientAuthNavigation.UnlockTargetInput]
     *   - [ClientAuthNavigation.UnlockOtpInput]
     */
    const val SEND_UNLOCK_PHONE_CONFIRMATION = "$UNLOCK_BY_PHONE_ROUTE/send-confirmation"

    /**
     * **HTTP method:** `POST`
     *
     * Unlocks a temporarily locked account using a phone confirmation code.
     *
     * Request body: [UnlockByPhoneConfirmationRequest].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     *   [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.SELF_UNLOCK_ACCOUNT].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful unlock.
     * - **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.PHONE_NUMBER] — phone number from the request.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientAuthNavigation.UnlockOtpInput]
     */
    const val UNLOCK_BY_PHONE = UNLOCK_BY_PHONE_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Unlocks a temporarily locked account using an external authentication provider token.
     *
     * Request body: [UnlockByExternalAuthProviderRequest].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     *   [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.SELF_UNLOCK_ACCOUNT].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] only upon successful unlock.
     * - **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.USER_AUTH_PROVIDER] — the identity provider used.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ClientAuthNavigation.UnlockMethodSelection]
     */
    const val UNLOCK_BY_EXTERNAL_PROVIDER = "$BASE_OPEN_UNLOCK_ROUTE/external-provider"
}