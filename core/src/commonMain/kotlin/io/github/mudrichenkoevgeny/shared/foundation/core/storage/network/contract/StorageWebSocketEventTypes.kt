package io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.contract

import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.file.StorageFilePrivatePayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.ManagementStorageSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.model.storagesettings.OpenStorageSettingsPayload

/**
 * WebSocket event types related to storage functionality.
 */
public object StorageWebSocketEventTypes {
    /**
     * Triggered when open storage settings are updated.
     * Payload: [OpenStorageSettingsPayload]
     */
    public const val OPEN_STORAGE_SETTINGS_UPDATED: String = "OPEN_STORAGE_SETTINGS_UPDATED"

    /**
     * Triggered when management storage settings are updated.
     * Payload: [ManagementStorageSettingsPayload]
     */
    public const val MANAGEMENT_STORAGE_SETTINGS_UPDATED: String = "MANAGEMENT_STORAGE_SETTINGS_UPDATED"

    /**
     * Triggered when a storage file status changes (e.g. from PENDING_UPLOAD to READY).
     * Payload: [StorageFilePrivatePayload]
     */
    public const val STORAGE_FILE_UPDATED: String = "STORAGE_FILE_UPDATED"
}
