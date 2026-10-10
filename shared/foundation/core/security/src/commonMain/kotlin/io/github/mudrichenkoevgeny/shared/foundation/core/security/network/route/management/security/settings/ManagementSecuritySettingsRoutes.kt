package io.github.mudrichenkoevgeny.shared.foundation.core.security.network.route.management.security.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.management.ManagementRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.action.SecurityAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.audit.resource.SecurityAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.security.domain.permission.SecurityPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.network.model.securitysettings.ManagementSecuritySettingsPayload

/**
 * Route paths for security settings in the management API.
 */
object ManagementSecuritySettingsRoutes {
    /** Base path for management security settings endpoints. */
    private const val BASE_MANAGEMENT_SECURITY_SETTINGS_ROUTE = "${ManagementRoutes.BASE_MANAGEMENT_ROUTE}/security/settings"

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves security settings.
     *
     * Response body: [ManagementSecuritySettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE`, `UserAccountStatus.READ_ONLY`
     * (**OR** semantics).
     * - **Required Permissions:** None.
     */
    const val GET_MANAGEMENT_SECURITY_SETTINGS = BASE_MANAGEMENT_SECURITY_SETTINGS_ROUTE

    /**
     * **HTTP method:** `PUT`
     *
     * Updates global security settings and policies.
     *
     * Request body: [ManagementSecuritySettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** [SecurityPermissionCode.SECURITY_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via `SelfManagementSessionRoutes.REAUTHENTICATE_SESSION` if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful updates and all failed attempts.
     * * **Action:** [SecurityAuditActionType.MANAGEMENT_UPDATE_SECURITY_SETTINGS].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the `UserId` of the administrator performing the update.
     * * **Resource:** [SecurityAuditResourceType.SECURITY_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val UPDATE_MANAGEMENT_SECURITY_SETTINGS = BASE_MANAGEMENT_SECURITY_SETTINGS_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Resets global security settings and policies to default values.
     *
     * Response body: [ManagementSecuritySettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** [SecurityPermissionCode.SECURITY_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via `SelfManagementSessionRoutes.REAUTHENTICATE_SESSION` if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful resets and all failed attempts.
     * * **Action:** [SecurityAuditActionType.MANAGEMENT_RESET_SECURITY_SETTINGS].
     * * **Actor:** [AuditActorType.USER]. Set `actorId` to the `UserId` of the administrator performing the reset.
     * * **Resource:** [SecurityAuditResourceType.SECURITY_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * * **Metadata:** Include:
     * 1. [ClientInfo] (see [CommonAuditMetadataKey]).
     */
    const val RESET_MANAGEMENT_SECURITY_SETTINGS = "$BASE_MANAGEMENT_SECURITY_SETTINGS_ROUTE/reset"
}
