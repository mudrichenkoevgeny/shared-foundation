package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.ManagementStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload

/**
 * WebSocket event types related to storage functionality.
 */
object StorageWebSocketEventTypes {
    /**
     * Triggered when open storage settings are updated.
     * Payload: [OpenStorageSettingsPayload]
     */
    const val OPEN_STORAGE_SETTINGS_UPDATED = "OPEN_STORAGE_SETTINGS_UPDATED"

    /**
     * Triggered when management storage settings are updated.
     * Payload: [ManagementStorageSettingsPayload]
     */
    const val MANAGEMENT_STORAGE_SETTINGS_UPDATED = "MANAGEMENT_STORAGE_SETTINGS_UPDATED"

    /**
     * Triggered when a storage file status changes (e.g. from PENDING_UPLOAD to READY).
     * Payload: [StorageFilePrivatePayload]
     */
    const val STORAGE_FILE_UPDATED = "STORAGE_FILE_UPDATED"
}
