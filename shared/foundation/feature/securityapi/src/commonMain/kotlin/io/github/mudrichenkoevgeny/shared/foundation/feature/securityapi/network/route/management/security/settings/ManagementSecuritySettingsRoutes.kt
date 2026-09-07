package io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.network.route.management.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.action.SecurityAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.audit.resource.SecurityAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.domain.permission.SecurityPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.securityapi.network.route.base.security.settings.BaseSecuritySettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId

/**
 * Route paths for security settings in the management API.
 */
object ManagementSecuritySettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves security settings.
     *
     * Response body: [ManagementSecuritySettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     */
    const val GET_MANAGEMENT_SECURITY_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseSecuritySettingsRoutes.BASE_SECURITY_SETTINGS_ROUTE}"

    /**
     * **HTTP method:** `PUT`
     *
     * Updates global security settings and policies.
     *
     * Request body: [ManagementSecuritySettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [SecurityPermissionCode.SECURITY_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful updates and all failed attempts.
     * * **Action:** [SecurityAuditActionType.MANAGEMENT_UPDATE_SECURITY_SETTINGS].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] of the administrator performing the update.
     * * **Resource:** [SecurityAuditResourceType.SECURITY_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val UPDATE_MANAGEMENT_SECURITY_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseSecuritySettingsRoutes.BASE_SECURITY_SETTINGS_ROUTE}"
}
