package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.open

import io.github.mudrichenkoevgeny.shared.foundation.core.common.network.route.CommonNetworkRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.BaseStorageNetworkRoutes

/**
 * Route paths for storage settings in the open API.
 */
object OpenStorageSettingsRoutes {
    /**
     * **HTTP method:** `GET`
     *
     * Retrieves open storage settings.
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
    const val GET_OPEN_STORAGE_SETTINGS =
        "${CommonNetworkRoutes.BASE_SETTINGS_NETWORK_ROUTE}${BaseStorageNetworkRoutes.BASE_STORAGE_NETWORK_ROUTE}"
}
