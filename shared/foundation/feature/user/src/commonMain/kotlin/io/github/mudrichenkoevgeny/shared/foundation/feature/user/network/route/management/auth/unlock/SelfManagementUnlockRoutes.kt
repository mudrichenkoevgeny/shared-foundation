package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.unlock

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.otpconfirmation.OtpConfirmationPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.metadata.UserAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementAuthNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.auth.unlock.UnlockByEmailConfirmationRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.confirmation.SendConfirmationToEmailRequest
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.BaseUserNetworkRoutes

/**
 * Route paths for authenticated self-service account unlocking in the management API.
 */
object SelfManagementUnlockRoutes {
    /** Base path for management self-unlock operations. */
    private const val BASE_SELF_MANAGEMENT_UNLOCK_ROUTE =
        "${CommonNetworkRoutes.BASE_MANAGEMENT_NETWORK_ROUTE}${BaseUserNetworkRoutes.BASE_USER_AUTH_NETWORK_ROUTE}/unlock"

    /** Base path for email unlock operations. */
    private const val UNLOCK_BY_EMAIL_ROUTE = "$BASE_SELF_MANAGEMENT_UNLOCK_ROUTE/email"

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
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementAuthNavigation.UnlockTargetInput]
     *   - [ManagementAuthNavigation.UnlockOtpInput]
     */
    const val SEND_UNLOCK_EMAIL_CONFIRMATION = "$UNLOCK_BY_EMAIL_ROUTE/send-confirmation"

    /**
     * **HTTP method:** `POST`
     *
     * Unlocks a temporarily locked management account using an email confirmation code.
     *
     * Request body: [UnlockByEmailConfirmationRequest].
     * Response body: None (204 No Content).
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.SELF_UNLOCK_ACCOUNT].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] upon success.
     * - **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the [UserId] upon success.
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *   2. [UserAuditMetadataKey.EMAIL_ADDRESS] — email address from the request.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementAuthNavigation.UnlockOtpInput]
     */
    const val UNLOCK_BY_EMAIL = UNLOCK_BY_EMAIL_ROUTE
}