package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.actor.AuditActorType
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.event.AuditEventPrivate
import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.metadata.CommonAuditMetadataKey
import io.github.mudrichenkoevgeny.shared.foundation.core.common.domain.model.client.ClientInfo
import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.action.StorageAuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.resource.StorageAuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.permission.StoragePermissionCode
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.navigation.route.management.ManagementStorageSettingsNavigation
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.ManagementStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.BaseStorageNetworkRoutes

/**
 * Route paths for storage settings in the management API.
 */
object ManagementStorageSettingsRoutes {
    /** Base path for management storage settings endpoints. */
    private const val BASE_MANAGEMENT_STORAGE_SETTINGS_ROUTE =
        "${CommonNetworkRoutes.BASE_MANAGEMENT_NETWORK_ROUTE}${CommonNetworkRoutes.BASE_SETTINGS_NETWORK_ROUTE}${BaseStorageNetworkRoutes.BASE_STORAGE_NETWORK_ROUTE}"

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves the platform-wide storage settings.
     *
     * Request body: None.
     * Response body: [ManagementStorageSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE`, `UserAccountStatus.READ_ONLY` (**OR** semantics).
     * - **Required Permissions:** None.
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementStorageSettingsNavigation.StorageSettings]
     */
    const val GET_MANAGEMENT_STORAGE_SETTINGS = BASE_MANAGEMENT_STORAGE_SETTINGS_ROUTE

    /**
     * **HTTP method:** `PUT`
     *
     * Updates global storage settings and category upload limits.
     *
     * Request body: [ManagementStorageSettingsPayload].
     * Response body: [ManagementStorageSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** [StoragePermissionCode.STORAGE_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful updates and all failed attempts.
     * - **Action:** [StorageAuditActionType.MANAGEMENT_UPDATE_STORAGE_SETTINGS].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the `UserId` of the administrator performing the update.
     * - **Resource:** [StorageAuditResourceType.STORAGE_SETTINGS].
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementStorageSettingsNavigation.StorageSettings]
     */
    const val UPDATE_MANAGEMENT_STORAGE_SETTINGS = BASE_MANAGEMENT_STORAGE_SETTINGS_ROUTE

    /**
     * **HTTP method:** `POST`
     *
     * Resets global storage settings and category limits to default platform values.
     *
     * Request body: None.
     * Response body: [ManagementStorageSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Denied.
     * - **Allowed Roles:** `UserRole.STAFF`, `UserRole.ADMIN` (**OR** semantics).
     * - **Allowed Account Statuses:** `UserAccountStatus.ACTIVE` (**OR** semantics).
     * - **Required Permissions:** [StoragePermissionCode.STORAGE_SETTINGS_UPDATE] (**AND** semantics).
     *
     * **Audit logging:** Persist an [AuditEventPrivate] for successful resets and all failed attempts.
     * - **Action:** [StorageAuditActionType.MANAGEMENT_RESET_STORAGE_SETTINGS].
     * - **Actor:** [AuditActorType.USER]. Set `actorId` to the `UserId` of the administrator performing the reset.
     * - **Resource:** [StorageAuditResourceType.STORAGE_SETTINGS].
     * - **Metadata:** Include:
     *   1. [ClientInfo] (see [CommonAuditMetadataKey]).
     *
     * **UI Presentation:**
     * - Screens:
     *   - [ManagementStorageSettingsNavigation.StorageSettings]
     */
    const val RESET_MANAGEMENT_STORAGE_SETTINGS = "$BASE_MANAGEMENT_STORAGE_SETTINGS_ROUTE/reset"

    /**
     * **HTTP method:** `GET`
     *
     * Retrieves the open (public) storage settings for unauthenticated management users.
     *
     * Request body: None.
     * Response body: [OpenStorageSettingsPayload].
     *
     * **Authorization:**
     * - **Public Access:** Allowed.
     * - **Allowed Roles:** Any.
     * - **Allowed Account Statuses:** Any.
     * - **Required Permissions:** None.
     *
     * **UI Presentation:** None.
     */
    const val GET_OPEN_STORAGE_SETTINGS = "$BASE_MANAGEMENT_STORAGE_SETTINGS_ROUTE/open"
}
