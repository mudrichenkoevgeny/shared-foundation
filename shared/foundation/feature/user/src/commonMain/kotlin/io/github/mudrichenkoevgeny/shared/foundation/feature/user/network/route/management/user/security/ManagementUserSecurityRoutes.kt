package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.user.security

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEvent
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.UserPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.contract.UserApiPaths
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.base.user.security.BaseManagementUserSecurityRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.open.session.OpenSessionRoutes

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
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [UserPermissionCode.USER_UPDATE_SECURITY_FOR_USER] when the
     * target has [UserRole.USER]; [UserPermissionCode.USER_UPDATE_SECURITY_FOR_STAFF] when the
     * target has [UserRole.STAFF] (**AND** semantics).
     * - **Authority Level:** Actor's level must be strictly greater than the target's level.
     * Actor cannot target own account.
     *
     * **Security:** Sensitive operation. MFA Step-up required (if enabled). Returns
     * [SecurityErrorCodes.TOTP_CONFIRMATION_REQUIRED] if additional verification is needed.
     * Session must be verified via [OpenSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEvent] for successful execution and all failed attempts.
     * * **Action:** [UserAuditActionType.MANAGEMENT_DISABLE_USER_TOTP].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the administrator performing the operation.
     * * **Resource:** [UserAuditResourceType.USER]. Set `resourceId` to the target [UserId] from the path.
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val DISABLE_TOTP = BaseManagementUserSecurityRoutes.DISABLE_TOTP
}