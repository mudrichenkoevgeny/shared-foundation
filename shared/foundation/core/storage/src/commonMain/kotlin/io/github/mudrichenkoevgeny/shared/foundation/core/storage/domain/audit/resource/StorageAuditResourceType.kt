package io.github.mudrichenkoevgeny.shared.foundation.core.storage.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.ManagementStorageRoutes
import io.github.mudrichenkoevgeny.shared.foundation.core.storage.network.route.management.ManagementStorageSettingsRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val RESOURCE_STORAGE_SETTINGS = "storage_settings"
private const val RESOURCE_STORAGE_FILE = "storage_file"

/**
 * Storage-module audit resource kinds for administrative storage operations.
 *
 * @property value String representation of the [StorageAuditResourceType].
 */
@Serializable
enum class StorageAuditResourceType(override val value: String) : AuditResourceType {
    /** Global storage settings resource ([ManagementStorageSettingsRoutes]). */
    @SerialName(RESOURCE_STORAGE_SETTINGS)
    STORAGE_SETTINGS(RESOURCE_STORAGE_SETTINGS),

    /** Storage file metadata or S3 binary record ([ManagementStorageRoutes]). */
    @SerialName(RESOURCE_STORAGE_FILE)
    STORAGE_FILE(RESOURCE_STORAGE_FILE);

    companion object {
        /**
         * Returns [StorageAuditResourceType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): StorageAuditResourceType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [StorageAuditResourceType] for the given wire or enum-style string.
         *
         * @throws IllegalArgumentException if [value] does not match any [StorageAuditResourceType].
         */
        fun fromValueOrThrow(value: String): StorageAuditResourceType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for StorageAuditResourceType: '$value'")
    }
}
