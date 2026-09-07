package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.AuthSettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.settings.BaseManagementAuthSettingsRoutes

/**
 * Route paths for authentication settings in the management API.
 *
 * `GET` and `PUT` share the same path; the management prefix distinguishes them from the open
 * read-only auth-settings route (no management prefix, open slice).
 */
object ManagementAuthSettingsRoutes {

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves global authentication settings for the management portal.
     *
     * Response body: [ManagementAuthSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     */
    const val GET_MANAGEMENT_AUTH_SETTINGS = BaseManagementAuthSettingsRoutes.MANAGEMENT_AUTH_SETTINGS_PATH

    /**
     * **HTTP method:** `PUT`
     *
     * Updates global authentication settings.
     *
     * Request body: [ManagementAuthSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [AuthSettingsPermissionCode.AUTH_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful updates and all failed attempts.
     * * **Action:** [UserAuditActionType.MANAGEMENT_UPDATE_AUTH_SETTINGS].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] of the administrator performing the update.
     * * **Resource:** [UserAuditResourceType.AUTH_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val UPDATE_MANAGEMENT_AUTH_SETTINGS = BaseManagementAuthSettingsRoutes.MANAGEMENT_AUTH_SETTINGS_PATH
}