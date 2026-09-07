package io.github.mudrichenkoevgeny.shared.foundation.feature.settingsapi.network.route.management.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsapi.domain.audit.action.SettingsAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsapi.domain.audit.resource.SettingsAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsapi.domain.permission.SettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsapi.network.route.base.globalsettings.BaseGlobalSettingsRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId

/**
 * Route paths for global settings in the management API.
 */
object ManagementGlobalSettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves global platform settings.
     *
     * Response body: [ManagementGlobalSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     */
    const val GET_MANAGEMENT_GLOBAL_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseGlobalSettingsRoutes.BASE_GLOBAL_SETTINGS_ROUTE}"

    /**
     * **HTTP method:** `PUT`
     *
     * Updates platform-wide global settings.
     *
     * Request body: [ManagementGlobalSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [SettingsPermissionCode.GLOBAL_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Audit logging:** Persist an [AuditEvent] for successful updates and all failed attempts.
     * * **Action:** [SettingsAuditActionType.MANAGEMENT_UPDATE_GLOBAL_SETTINGS].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] of the administrator performing the update.
     * * **Resource:** [SettingsAuditResourceType.GLOBAL_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val UPDATE_MANAGEMENT_GLOBAL_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseGlobalSettingsRoutes.BASE_GLOBAL_SETTINGS_ROUTE}"
}
