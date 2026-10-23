package io.github.mudrichenkoevgeny.shared.foundation.core.settings.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.route.management.globalsettings.ManagementGlobalSettingsRoutes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val RESOURCE_GLOBAL_SETTINGS = "global_settings"

/**
 * Settings-module audit resource kinds for global configuration settings.
 *
 * @property value String representation of the [SettingsAuditResourceType].
 */
@Serializable
enum class SettingsAuditResourceType(override val value: String) : AuditResourceType {
    /** Global settings resource ([ManagementGlobalSettingsRoutes]). */
    @SerialName(RESOURCE_GLOBAL_SETTINGS)
    GLOBAL_SETTINGS(RESOURCE_GLOBAL_SETTINGS);

    companion object {
        /**
         * Returns [SettingsAuditResourceType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): SettingsAuditResourceType? =
            entries.firstOrNull {
                it.value.equals(value, ignoreCase = true) ||
                        it.name.equals(value, ignoreCase = true)
            }

        /**
         * Returns [SettingsAuditResourceType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SettingsAuditResourceType].
         */
        fun fromValueOrThrow(value: String): SettingsAuditResourceType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for SettingsAuditResourceType: '$value'")
    }
}
