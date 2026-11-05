package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementSelfUserNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.user.UserPrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.request.user.SelfUpdateUserRequest

/**
 * Route paths for the current authenticated principal's account in the management API (self-service).
 */
public object SelfManagementUserRoutes {
    /** Base path for self-service user operations in the management API. */
    internal const val BASE_SELF_MANAGEMENT_USER_ROUTE = "${CommonNetworkRoutes.BASE_SELF_MANAGEMENT_NETWORK_ROUTE}/user"

    /**
     * **HTTP method:** `GET`
     *
     * Returns the private management-level details of the **current** authenticated staff member or administrator.
     *
     * Request body: None.
     * Response body: [UserPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfUserNavigation.Main]
     */
    public const val GET_USER: String = BASE_SELF_MANAGEMENT_USER_ROUTE

    /**
     * **HTTP method:** `PATCH`
     *
     * Partially updates account details and profile fields of the current authenticated staff member.
     *
     * Request body: [SelfUpdateUserRequest].
     * Response body: [UserPrivatePayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful execution and all failed attempts.
     * - **Action:** [UserAuditActionType.SELF_UPDATE_USER].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the current [UserId].
     * - **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the current [UserId].
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSelfUserNavigation.UserEdit]
     */
    public const val UPDATE_USER: String = BASE_SELF_MANAGEMENT_USER_ROUTE
}
