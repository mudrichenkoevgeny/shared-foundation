package io.github.mudrichenkoevgeny.shared.foundation.feature.settingsapi.domain.audit.resource

import io.github.mudrichenkoevgeny.shared.foundation.core.audit.domain.model.resource.AuditResourceType
import io.github.mudrichenkoevgeny.shared.foundation.core.settings.network.model.globalsettings.ManagementGlobalSettingsPayload
import io.github.mudrichenkoevgeny.shared.foundation.feature.settingsapi.network.route.management.globalsettings.ManagementGlobalSettingsRoutes

/**
 * Settings-module audit resource kinds (global settings, not security settings).
 */
enum class SettingsAuditResourceType : AuditResourceType {
    /** [ManagementGlobalSettingsRoutes.UPDATE_MANAGEMENT_GLOBAL_SETTINGS] request/response ([ManagementGlobalSettingsPayload]). */
    GLOBAL_SETTINGS;

    /**
     * String representation of the [SettingsAuditResourceType].
     */
    override val serialName: String
        get() = when (this) {
            GLOBAL_SETTINGS -> RESOURCE_GLOBAL_SETTINGS
        }

    override fun parseOrNull(value: String): AuditResourceType? = fromValueOrNull(value)

    override fun parseOrThrow(value: String): AuditResourceType = fromValueOrThrow(value)

    companion object {
        private const val RESOURCE_GLOBAL_SETTINGS = "global_settings"

        /**
         * Returns [SettingsAuditResourceType] based on the provided string value, or null if the value is invalid.
         */
        fun fromValueOrNull(value: String): SettingsAuditResourceType? =
            entries.firstOrNull {
                it.serialName.equals(value, ignoreCase = true) ||
                    it.name.equals(value, ignoreCase = true)
            } ?: runCatching {
                valueOf(value.uppercase())
            }.getOrNull()

        /**
         * Returns [SettingsAuditResourceType] for the given wire or enum-style string (case-insensitive enum name).
         *
         * @throws IllegalArgumentException if [value] does not match any [SettingsAuditResourceType].
         */
        fun fromValueOrThrow(value: String): SettingsAuditResourceType =
            fromValueOrNull(value) ?: throw IllegalArgumentException("Unknown value for SettingsAuditResourceType: '$value'")
    }
}
