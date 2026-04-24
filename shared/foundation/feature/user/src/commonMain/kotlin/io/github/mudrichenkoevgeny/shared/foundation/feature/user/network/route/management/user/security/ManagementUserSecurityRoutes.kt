package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.security

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.security.BaseManagementUserSecurityRoutes

/**
 * Route paths for administrative security management of users.
 */
object ManagementUserSecurityRoutes {

    /**
     * **HTTP method:** `DELETE`
     *
     * Administratively disables TOTP (2FA) for a specific user.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * **Authorization** ([UserPermissionCode]): [UserPermissionCode.USER_UPDATE_SECURITY_FOR_USER] when the target has
     * [UserRole.USER]; [UserPermissionCode.USER_UPDATE_SECURITY_FOR_STAFF] when the target has [UserRole.STAFF] or
     * [UserRole.ADMIN].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful reset and for security-relevant denials.
     * * **Action:** [UserAuditActionType.MANAGEMENT_RESET_USER_TOTP].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator [UserId] performing the operation.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the target [UserId] from the path.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val RESET_TOTP = BaseManagementUserSecurityRoutes.RESET_TOTP

    /**
     * **HTTP method:** `POST`
     *
     * Administratively invalidates existing recovery codes and generates a new set for a specific user.
     *
     * Path parameter: [UserApiPaths.USER_ID].
     *
     * **Authorization** ([UserPermissionCode]): [UserPermissionCode.USER_UPDATE_SECURITY_FOR_USER] when the target has
     * [UserRole.USER]; [UserPermissionCode.USER_UPDATE_SECURITY_FOR_STAFF] when the target has [UserRole.STAFF] or
     * [UserRole.ADMIN].
     *
     * **Audit logging:** Persist an [AuditEvent] for successful regeneration and for security-relevant denials.
     * * **Action:** [UserAuditActionType.MANAGEMENT_REGENERATE_USER_RECOVERY_CODES].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator [UserId] performing the operation.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the target [UserId] from the path.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey])
     */
    const val REGENERATE_RECOVERY_CODES = BaseManagementUserSecurityRoutes.REGENERATE_RECOVERY_CODES
}