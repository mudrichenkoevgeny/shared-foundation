package io.github.mudrichenkoevgeny.shared.foundation.feature.settingsApi.network.route.management.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.GlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsApi.domain.audit.action.SettingsAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsApi.domain.audit.resource.SettingsAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsApi.domain.permission.SettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsApi.network.route.base.globalsettings.BaseGlobalSettingsRoutes

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
     * **Audit logging:** Persist an [AuditEvent] for successful updates and for security-relevant denials. Use action
     * [SettingsAuditActionType.MANAGEMENT_UPDATE_GLOBAL_SETTINGS] and resource [SettingsAuditResourceType.GLOBAL_SETTINGS].
     * Prefer omitting full settings payloads from metadata; record outcome and actor only.
     */
    const val UPDATE_GLOBAL_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseGlobalSettingsRoutes.BASE_GLOBAL_SETTINGS_ROUTE}"
}