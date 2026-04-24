package io.github.mudrichenkoevgeny.shared.foundation.feature.settings.api.network.route.management.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.GlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.settings.api.domain.audit.action.SettingsAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.settings.api.domain.audit.resource.SettingsAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.settings.api.domain.permission.SettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.settings.api.network.route.base.globalsettings.BaseGlobalSettingsRoutes

/**
 * Route paths for global settings in the management API.
 */
object ManagementGlobalSettingsRoutes {
    /**
     * **HTTP method:** `PUT`
     *
     * **Authorization:** caller must have [SettingsPermissionCode.GLOBAL_SETTINGS_UPDATE].
     *
     * Request body: [GlobalSettingsPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful updates and for security-relevant denials.
     * * **Action:** [SettingsAuditActionType.MANAGEMENT_UPDATE_GLOBAL_SETTINGS].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator user id performing the update.
     * * **Resource:** [SettingsAuditResourceType.GLOBAL_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val UPDATE_GLOBAL_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseGlobalSettingsRoutes.BASE_GLOBAL_SETTINGS_ROUTE}"
}