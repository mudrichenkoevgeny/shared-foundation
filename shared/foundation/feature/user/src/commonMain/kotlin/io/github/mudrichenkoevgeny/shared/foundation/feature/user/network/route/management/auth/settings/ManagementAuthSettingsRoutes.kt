package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.permission.PermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.auth.settings.BaseManagementAuthSettingsRoutes

/**
 * Route paths for authentication settings in the management API.
 *
 * `GET` and `PUT` share the same path; the management prefix distinguishes them from the open read-only auth-settings
 * route (no management prefix, public slice).
 */
object ManagementAuthSettingsRoutes {

    /**
     * **HTTP method:** `GET`
     *
     * **Authorization:** No [PermissionCode] is declared in this library for this endpoint; who may call it is
     * implementation-defined (for example management-authenticated principals or future dedicated grants in consumers).
     *
     * Response body: [ManagementAuthSettingsPayload].
     */
    const val GET_AUTH_SETTINGS_MANAGEMENT = BaseManagementAuthSettingsRoutes.MANAGEMENT_AUTH_SETTINGS_PATH

    /**
     * **HTTP method:** `PUT`
     *
     * **Authorization:** No [PermissionCode] is declared in this library for this endpoint; who may call it is
     * implementation-defined (for example management-authenticated principals or future dedicated grants in consumers).
     *
     * Request body: [ManagementAuthSettingsPayload].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful updates and security-relevant denials.
     * * **Action:** [UserAuditActionType.MANAGEMENT_UPDATE_AUTH_SETTINGS].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] of the administrator performing the update.
     * * **Resource:** [UserAuditResourceType.AUTH_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val UPDATE_AUTH_SETTINGS = BaseManagementAuthSettingsRoutes.MANAGEMENT_AUTH_SETTINGS_PATH
}