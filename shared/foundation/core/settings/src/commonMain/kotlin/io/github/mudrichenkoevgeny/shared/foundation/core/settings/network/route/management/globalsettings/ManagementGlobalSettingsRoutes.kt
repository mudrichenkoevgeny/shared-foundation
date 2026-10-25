package io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.management.globalsettings

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.audit.action.SettingsAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.audit.resource.SettingsAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.permission.SettingsPermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.navigation.route.management.ManagementSettingsNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.OpenGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.BaseSettingsNetworkRoutes

/**
 * Route paths for global settings in the management API.
 */
object ManagementGlobalSettingsRoutes {

    /** Base path for management global settings endpoints. */
    private const val BASE_MANAGEMENT_GLOBAL_SETTINGS_ROUTE =
        "${CommonNetworkRoutes.BASE_MANAGEMENT_NETWORK_ROUTE}${CommonNetworkRoutes.BASE_SETTINGS_NETWORK_ROUTE}${BaseSettingsNetworkRoutes.BASE_GLOBAL_SETTINGS_NETWORK_ROUTE}"

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves global platform settings.
     *
     * Request body: None.
     * Response body: [ManagementGlobalSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE`, `UserAccountStatus.READ_ONLY` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSettingsNavigation.GlobalSettings]
     */
    const val GET_MANAGEMENT_GLOBAL_SETTINGS = BASE_MANAGEMENT_GLOBAL_SETTINGS_ROUTE

    /**
     * **HTTP method:** `PUT`
     *
     * Updates platform-wide global settings.
     *
     * Request body: [ManagementGlobalSettingsPayload].
     * Response body: [ManagementGlobalSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** [SettingsPermissionCode.GLOBAL_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns `SecurityErrorCodes.TOTP_NOT_ENABLED` if TOTP is not configured. When enabled,
     * MFA Step-up is required via `SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED`.
     * Session must be verified via `SelfManagementSessionRoutes.REAUTHENTICATE_SESSION` if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful updates and all failed attempts.
     * - **Action:** [SettingsAuditActionType.MANAGEMENT_UPDATE_GLOBAL_SETTINGS].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the `UserId` of the administrator performing the update.
     * - **Resource:** [SettingsAuditResourceType.GLOBAL_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSettingsNavigation.GlobalSettings]
     */
    const val UPDATE_MANAGEMENT_GLOBAL_SETTINGS = BASE_MANAGEMENT_GLOBAL_SETTINGS_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Resets platform-wide global settings to default values.
     *
     * Request body: None.
     * Response body: [ManagementGlobalSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** [SettingsPermissionCode.GLOBAL_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Security:** Sensitive administrative operation. Requires mandatory MFA setup on the manager's
     * account; returns `SecurityErrorCodes.TOTP_NOT_ENABLED` if TOTP is not configured. When enabled,
     * MFA Step-up is required via `SecurityErrorCodes.MFA_CONFIRMATION_REQUIRED`.
     * Session must be verified via `SelfManagementSessionRoutes.REAUTHENTICATE_SESSION` if stale.
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful resets and all failed attempts.
     * - **Action:** [SettingsAuditActionType.MANAGEMENT_RESET_GLOBAL_SETTINGS].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the `UserId` of the administrator performing the reset.
     * - **Resource:** [SettingsAuditResourceType.GLOBAL_SETTINGS]. Leave `resourceId` unset (singleton resource).
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementSettingsNavigation.GlobalSettings]
     */
    const val RESET_MANAGEMENT_GLOBAL_SETTINGS = "$BASE_MANAGEMENT_GLOBAL_SETTINGS_ROUTE/reset"

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves the open (public) platform settings for unauthenticated management users.
     *
     * Request body: None.
     * Response body: [OpenGlobalSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **UI Presentation:** None.
     */
    const val GET_OPEN_GLOBAL_SETTINGS = "$BASE_MANAGEMENT_GLOBAL_SETTINGS_ROUTE/open"
}
