package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.user

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session.OpenSessionRoutes

/**
 * Route paths for the authenticated user's account in the open API.
 */
object OpenUserRoutes {
    /** Base path for open self-service user account operations. */
    private const val BASE_USER_ROUTE = "/user"

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves the private profile details and account status of the currently authenticated user.
     *
     * Response body: [UserPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** Any [UserAccountStatus] (**OR** semantics).
     * - **Required Permissions:** None.
     */
    const val GET_USER = BASE_USER_ROUTE

    /**
     * **HTTP method:** `DELETE`
     *
     * Schedules the current user account for permanent deletion after a grace period.
     *
     * Response body: [UserPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY],
     * [UserAccountStatus.BANNED], [UserAccountStatus.SECURITY_HOLD] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled for the account). Returns
     * [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.SELF_SCHEDULE_USER_DELETION].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the current [UserId].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val SCHEDULE_DELETION = "$BASE_USER_ROUTE/schedule-deletion"

    /**
     * **HTTP method:** `POST`
     *
     * Cancels a scheduled account deletion and restores the user to an active state.
     *
     * Response body: [UserPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.USER] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.PENDING_DELETION] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled for the account). Returns
     * [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.SELF_RESTORE_USER].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the current [UserId].
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val RESTORE_USER = "$BASE_USER_ROUTE/restore"
}
