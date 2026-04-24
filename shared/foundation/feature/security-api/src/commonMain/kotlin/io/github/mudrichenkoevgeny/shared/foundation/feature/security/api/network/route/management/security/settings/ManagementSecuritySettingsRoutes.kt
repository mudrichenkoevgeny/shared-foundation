package io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.network.route.management.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.SecuritySettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.domain.audit.action.SecurityAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.domain.audit.resource.SecurityAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.domain.permission.SecurityPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.security.api.network.route.base.security.settings.BaseSecuritySettingsRoutes

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
     * **Audit logging:** Persist an [AuditEvent] for successful updates and for security-relevant denials.
     * * **Action:** [SecurityAuditActionType.MANAGEMENT_UPDATE_SECURITY_SETTINGS].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator user id performing the update.
     * * **Resource:** [SecurityAuditResourceType.SECURITY_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val UPDATE_SECURITY_SETTINGS = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}${BaseSecuritySettingsRoutes.BASE_SECURITY_SETTINGS_ROUTE}"
}