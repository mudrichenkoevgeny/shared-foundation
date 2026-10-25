package io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.auth.settings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.security.error.naming.SecurityErrorCodes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.action.UserAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.audit.resource.UserAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.accountstatus.UserAccountStatus
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.role.UserRole
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.model.user.UserId
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.domain.permission.AuthSettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.navigation.route.management.ManagementAuthSettingsNavigation
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.ManagementAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.model.auth.settings.OpenAuthSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.BaseUserNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.feature.user.network.route.management.self.user.session.SelfManagementSessionRoutes

/**
 * Route paths for authentication settings in the management API.
 *
 * `GET` and `PUT` share the same path; the management prefix distinguishes them from the open
 * read-only auth-settings route (no management prefix, open slice).
 */
object ManagementAuthSettingsRoutes {
    /** Base path for management authentication settings operations. */
    private const val BASE_MANAGEMENT_AUTH_SETTINGS_ROUTE =
        "${CommonNetworkRoutes.BASE_MANAGEMENT_NETWORK_ROUTE}${CommonNetworkRoutes.BASE_SETTINGS_NETWORK_ROUTE}${BaseUserNetworkRoutes.BASE_USER_AUTH_NETWORK_ROUTE}"

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves global authentication settings for the management portal.
     *
     * Request body: None.
     * Response body: [ManagementAuthSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE], [UserAccountStatus.READ_ONLY]
     *   (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementAuthSettingsNavigation.AuthSettings]
     */
    const val GET_MANAGEMENT_AUTH_SETTINGS = BASE_MANAGEMENT_AUTH_SETTINGS_ROUTE

    /**
     * **HTTP method:** `PUT`
     *
     * Updates global authentication settings.
     *
     * Request body: [ManagementAuthSettingsPayload].
     * Response body: [ManagementAuthSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [AuthSettingsPermissionCode.AUTH_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful updates and all failed attempts.
     * - **Action:** [UserAuditActionType.MANAGEMENT_UPDATE_AUTH_SETTINGS].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] of the administrator performing the update.
     * - **Resource:** [UserAuditResourceType.AUTH_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementAuthSettingsNavigation.AuthSettings]
     */
    const val UPDATE_MANAGEMENT_AUTH_SETTINGS = BASE_MANAGEMENT_AUTH_SETTINGS_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Resets global authentication settings to default values.
     *
     * Request body: None.
     * Response body: [ManagementAuthSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** [UserRole.STAFF], [UserRole.ADMIN] (**OR** semantics).
     * - **Allowed Account Statuses:** [UserAccountStatus.ACTIVE] (**OR** semantics).
     * - **Required Permissions:** [AuthSettingsPermissionCode.AUTH_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns [SecurityErrorCodes.TOTP_NOT_ENABLED] if TOTP is not configured. When enabled,
     * MFA Step-up is required via [SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED].
     * Session must be verified via [SelfManagementSessionRoutes.REAUTHENTICATE_SESSION] if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful resets and all failed attempts.
     * - **Action:** [UserAuditActionType.MANAGEMENT_RESET_AUTH_SETTINGS].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the [UserId] of the administrator performing the reset.
     * - **Resource:** [UserAuditResourceType.AUTH_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementAuthSettingsNavigation.AuthSettings]
     */
    const val RESET_MANAGEMENT_AUTH_SETTINGS = "$BASE_MANAGEMENT_AUTH_SETTINGS_ROUTE/reset"

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves the open (public) authentication settings for unauthenticated management users.
     *
     * Request body: None.
     * Response body: [OpenAuthSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **UI Presentation:** None.
     */
    const val GET_OPEN_AUTH_SETTINGS = "$BASE_MANAGEMENT_AUTH_SETTINGS_ROUTE/open"
}