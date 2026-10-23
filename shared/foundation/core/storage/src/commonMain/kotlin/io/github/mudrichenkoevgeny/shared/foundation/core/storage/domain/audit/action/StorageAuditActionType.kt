package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.action

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.action.AuditActionType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.ManagementStorageRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.ManagementStorageSettingsRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val ACTION_MANAGEMENT_UPDATE_STORAGE_SETTINGS = "management_update_storage_settings"
private const val ACTION_MANAGEMENT_RESET_STORAGE_SETTINGS = "management_reset_storage_settings"
private const val ACTION_MANAGEMENT_DELETE_FILE = "management_delete_file"

/**
 * Storage-module audit action kinds for administrative operations and file deletions.
 *
 * @property value String representation of the [StorageAuditActionType].
 */
@Serializable
enum class StorageAuditActionType(override val value: String) : AuditActionType {
    /** Administrative update of platform-wide storage configuration limits via [ManagementStorageSettingsRoutes.UPDATE_MANAGEMENT_STORAGE_SETTINGS]. */
    @SerialName(ACTION_MANAGEMENT_UPDATE_STORAGE_SETTINGS)
    MANAGEMENT_UPDATE_STORAGE_SETTINGS(ACTION_MANAGEMENT_UPDATE_STORAGE_SETTINGS),

    /** Administrative reset of global storage settings to platform default limits via [ManagementStorageSettingsRoutes.RESET_MANAGEMENT_STORAGE_SETTINGS]. */
    @SerialName(ACTION_MANAGEMENT_RESET_STORAGE_SETTINGS)
    MANAGEMENT_RESET_STORAGE_SETTINGS(ACTION_MANAGEMENT_RESET_STORAGE_SETTINGS),

    /** Administrative deletion of single or multiple files from S3 storage via [ManagementStorageRoutes.DELETE_FILE] or [ManagementStorageRoutes.DELETE_FILES_BATCH]. */
    @SerialName(ACTION_MANAGEMENT_DELETE_FILE)
    MANAGEMENT_DELETE_FILE(ACTION_MANAGEMENT_DELETE_FILE);

    companion object {
        /**
         * Returns [StorageAuditActionType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): StorageAuditActionType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [StorageAuditActionType] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [StorageAuditActionType].
         */
        fun fromValueOrThrow(value: String): StorageAuditActionType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for StorageAuditActionType: '$value'")
    }
}
