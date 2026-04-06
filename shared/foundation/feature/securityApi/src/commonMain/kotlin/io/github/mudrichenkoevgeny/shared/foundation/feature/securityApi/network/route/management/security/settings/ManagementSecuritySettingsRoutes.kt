package io.github.mudrichenkoevgeny.shared.foundation.feature.securityApi.network.route.management.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.SecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.securityApi.domain.audit.action.SecurityAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.securityApi.domain.audit.resource.SecurityAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.securityApi.domain.permission.SecurityPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.securityApi.network.route.base.security.settings.BaseSecuritySettingsRoutes

/**
 * Route paths for security settings in the management API.
 */
object ManagementSecuritySettingsRoutes {
    /**
     * **HTTP method:** `PUT`
     *
     * **Authorization:** caller must have [SecurityPermissionCode.SECURITY_SETTINGS_UPDATE].
     *
     * Request body: [SecuritySettingsPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful updates and for security-relevant denials. Use action
     * [SecurityAuditActionType.MANAGEMENT_UPDATE_SECURITY_SETTINGS] and resource [SecurityAuditResourceType.SECURITY_SETTINGS].
     * Prefer omitting raw policy secrets from metadata; record outcome and actor only.
     */
    const val UPDATE_SECURITY_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseSecuritySettingsRoutes.BASE_SECURITY_SETTINGS_ROUTE}"
}